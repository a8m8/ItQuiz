package net.itquiz.services.impl;

import com.restfb.types.User;
import net.itquiz.components.EntityBuilder;
import net.itquiz.constants.ApplicationConstants;
import net.itquiz.dao.AccountDao;
import net.itquiz.dao.AccountRegistrationDao;
import net.itquiz.dao.AccountRoleDao;
import net.itquiz.dao.RoleDao;
import net.itquiz.entities.Account;
import net.itquiz.entities.AccountRegistration;
import net.itquiz.entities.AccountRole;
import net.itquiz.entities.Role;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.SignUpForm;
import net.itquiz.services.EmailService;
import net.itquiz.services.EntranceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Artur Meshcheriakov
 */
@Service("entranceService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EntranceServiceImpl implements EntranceService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountRoleDao accountRoleDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AccountRegistrationDao accountRegistrationDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EntityBuilder entityBuilder;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EntranceServiceImpl() {
        super();
    }

    @Transactional
    @Override
    public Account login(User user) throws InvalidUserInputException {
        Account account = accountDao.findByEmail(user.getEmail());
        if (account != null) {
            return account;
        } else {
            SignUpForm form = new SignUpForm();
            if (user.getEmail() == null) {
                throw new InvalidUserInputException(messageSource.getMessage(
                        "facebook.cannot.create", new Object[]{}, LocaleContextHolder.getLocale()));
            }
            form.setEmail(user.getEmail());
            form.setLogin(UUID.randomUUID().toString());
            if (user.getName() == null) {
                form.setFio("User");
            } else {
                form.setFio(user.getName());
            }
            UUID password = UUID.randomUUID();
            form.setPassword(password.toString());
            form.setPasswordConfirmed(password.toString());
            form.setConfirmed(Boolean.TRUE);

            return singUp(form, false, true);
        }
    }

    @Transactional
    @Override
    public List<Role> listRoles() {
        return roleDao.list();
    }

    @Transactional
    @Override
    public Account signUp(SignUpForm signUpForm) throws InvalidUserInputException {
        return singUp(signUpForm, true, false);
    }

    private Account singUp(SignUpForm signUpForm, boolean sendVerificationEmail,
                           boolean sendNotificationToEmail) throws InvalidUserInputException {
        if (isEmailExist(signUpForm.getEmail())) {
            throw new InvalidUserInputException(messageSource.getMessage("email.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (isLoginExist(signUpForm.getLogin())) {
            throw new InvalidUserInputException(messageSource.getMessage("login.busy",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        Account account = entityBuilder.buildAccount();
        String encodedPassword = passwordEncoder.encode(signUpForm.getPassword());
        signUpForm.setPassword(encodedPassword);
        signUpForm.copyFieldsTo(account);
        accountDao.save(account);

        AccountRegistration accountRegistration = entityBuilder.buildAccountRegistration(account);
        accountRegistrationDao.save(accountRegistration);

        Role role = roleDao.getProxy(ApplicationConstants.STUDENT_ROLE);
        AccountRole accountRole = entityBuilder.buildAccountRole(account, role);
        accountRoleDao.save(accountRole);

        List<AccountRole> accountRoles = new ArrayList<>();
        accountRoles.add(accountRole);
        account.setAccountRoles(accountRoles);

        if (sendVerificationEmail) {
            account.setAccountRegistration(accountRegistration);
            emailService.sendVerificationEmail(account);
        }

        if (sendNotificationToEmail) {
            emailService.sendNotificationEmail(account);
        }

        return account;
    }

    @Transactional
    @Override
    public void sendPasswordRecoveryEmail(String email) throws InvalidUserInputException {
        Account account = accountDao.findByEmail(email);
        if (account == null) {
            throw new InvalidUserInputException(messageSource.getMessage("email.not.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (!account.getActive()) {
            throw new InvalidUserInputException(messageSource.getMessage("account.not.active",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (!account.getConfirmed()) {
            throw new InvalidUserInputException(messageSource.getMessage("account.not.confirmed",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        AccountRegistration accountRegistration = account.getAccountRegistration();
        accountRegistration.setPassHash(UUID.randomUUID().toString());
        accountRegistration.setPassHashCreated(new Timestamp(System.currentTimeMillis()));
        accountRegistrationDao.update(accountRegistration);
        account.setAccountRegistration(accountRegistration);
        emailService.sendPasswordRecoveryEmail(account);
    }

    @Transactional
    @Override
    public void verifyAccount(int id, String hash) throws InvalidUserInputException {
        Account account = accountDao.findById(id);
        if (account == null) {
            throw new InvalidUserInputException(messageSource.getMessage("confirmation.failed",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (account.getConfirmed()) {
            throw new InvalidUserInputException(messageSource.getMessage("confirmation.confirmed",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (!StringUtils.equals(account.getAccountRegistration().getHash(), hash)) {
            throw new InvalidUserInputException(messageSource.getMessage("confirmation.failed",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }

        account.setConfirmed(Boolean.TRUE);
        accountDao.update(account);
    }

    @Transactional
    @Override
    public boolean isEmailExist(String email) {
        return (accountDao.findByEmail(email) != null);
    }

    @Transactional
    @Override
    public boolean isLoginExist(String login) {
        return (accountDao.findByLogin(login) != null);
    }

    @Transactional
    @Override
    public Account getAccount(String email) {
        return accountDao.findByEmail(email);
    }

    @Transactional
    @Override
    public void checkAccess(short role, int currentIdAccount) throws InvalidUserInputException {
        Account account = accountDao.findById(currentIdAccount);
        Set<Short> roles = new HashSet<>();
        for (AccountRole accountRole : account.getAccountRoles()) {
            roles.add(accountRole.getRole().getIdRole());
        }
        if (!roles.contains(role)) {
            throw new InvalidUserInputException(messageSource.getMessage("user.role.denied",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

    @Transactional
    @Override
    public void checkPasswordRecoveryAccess(int id, String passHash) throws InvalidUserInputException {
        Account account = accountDao.findById(id);
        if (account == null) {
            throw new InvalidUserInputException(messageSource.getMessage("cannot.recover.password",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        AccountRegistration accountRegistration = account.getAccountRegistration();
        if (accountRegistration.getPassHash() == null) {
            throw new InvalidUserInputException(messageSource.getMessage("cannot.recover.password",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (!StringUtils.equals(accountRegistration.getPassHash(), passHash)) {
            throw new InvalidUserInputException(messageSource.getMessage("cannot.recover.password",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }
}
