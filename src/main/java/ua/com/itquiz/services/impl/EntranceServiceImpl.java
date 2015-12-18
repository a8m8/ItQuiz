package ua.com.itquiz.services.impl;

import com.restfb.types.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itquiz.components.EntityBuilder;
import ua.com.itquiz.constants.ApplicationConstants;
import ua.com.itquiz.dao.AccountDao;
import ua.com.itquiz.dao.AccountRegistrationDao;
import ua.com.itquiz.dao.AccountRoleDao;
import ua.com.itquiz.dao.RoleDao;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.AccountRegistration;
import ua.com.itquiz.entities.AccountRole;
import ua.com.itquiz.entities.Role;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.admin.SignUpForm;
import ua.com.itquiz.security.DefaultPasswordEncoder;
import ua.com.itquiz.services.EmailService;
import ua.com.itquiz.services.EntranceService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Artur Meshcheriakov
 */
@Service("entranceService")
@Transactional
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
    private DefaultPasswordEncoder defaultPasswordEncoder;

    public EntranceServiceImpl() {
        super();
    }

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
            form.setLogin(String.valueOf(user.getEmail().hashCode()));
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

    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public Account signUp(SignUpForm signUpForm) throws InvalidUserInputException {
        return singUp(signUpForm, true, false);
    }

    private Account singUp(SignUpForm signUpForm, boolean sendVerificationEmail,
                           boolean sendPasswordToEmail) throws InvalidUserInputException {
        if (isEmailExist(signUpForm.getEmail())) {
            throw new InvalidUserInputException(messageSource.getMessage("email.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (isLoginExist(signUpForm.getLogin())) {
            throw new InvalidUserInputException(messageSource.getMessage("login.busy",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        Account account = entityBuilder.buildAccount();
        String encodedPassword = defaultPasswordEncoder.encode(signUpForm.getPassword());
        signUpForm.setPassword(encodedPassword);
        signUpForm.copyFieldsTo(account);
        accountDao.save(account);

        AccountRegistration accountRegistration = entityBuilder.buildAccountRegistration(account);
        accountRegistrationDao.save(accountRegistration);

        Role role = roleDao.findById(ApplicationConstants.STUDENT_ROLE);
        AccountRole accountRole = entityBuilder.buildAccountRole(account, role);
        accountRoleDao.save(accountRole);

        List<AccountRole> accountRoles = new ArrayList<>();
        accountRoles.add(accountRole);
        account.setAccountRoles(accountRoles);

        if (sendVerificationEmail) {
            account.setAccountRegistration(accountRegistration);
            emailService.sendVerificationEmail(account);
        }

        if (sendPasswordToEmail) {
            emailService.sendPasswordToEmail(account);
        }

        return accountDao.findById(account.getId());
    }

    @Override
    public void sendPasswordForRecovery(String email) throws InvalidUserInputException {
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

        emailService.sendPasswordToEmail(account);
    }

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

    @Override
    public boolean isEmailExist(String email) {
        return (accountDao.findByEmail(email) != null);
    }

    @Override
    public boolean isLoginExist(String login) {
        return (accountDao.findByLogin(login) != null);
    }

    @Override
    public Account getAccount(String email) {
        return accountDao.findByEmail(email);
    }

}
