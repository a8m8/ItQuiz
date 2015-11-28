package ua.com.itquiz.services.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.types.User;

import ua.com.itquiz.components.EntityBuilder;
import ua.com.itquiz.dao.AccountDao;
import ua.com.itquiz.dao.AccountRegistrationDao;
import ua.com.itquiz.dao.AccountRoleDao;
import ua.com.itquiz.dao.RoleDao;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.AccountRegistration;
import ua.com.itquiz.entities.AccountRole;
import ua.com.itquiz.entities.Role;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.SingUpForm;
import ua.com.itquiz.services.EmailService;
import ua.com.itquiz.services.EntranceService;

/**
 *
 * @author Artur Meshcheriakov
 */
@Service("entranceService")
@Transactional(readOnly = true)
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

    public EntranceServiceImpl() {
	super();
    }

    @Override
    public Account login(String email, String password, int role) throws InvalidUserInputException {
	Account account = accountDao.findByEmail(email);
	if (account == null) {
	    throw new InvalidUserInputException(messageSource.getMessage("login.badcredentials",
		new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (!StringUtils.equals(password, account.getPassword())) {
	    throw new InvalidUserInputException(messageSource.getMessage("login.badcredentials",
		new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (!account.getActive()) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("account.notactive",
		new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (!account.getConfirmed()) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("account.notconfirmed", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	boolean found = false;
	for (AccountRole accountRole : account.getAccountRoles()) {
	    if (accountRole.getRole().getIdRole().intValue() == role) {
		found = true;
		break;
	    }
	}
	if (!found) {
	    throw new InvalidUserInputException(messageSource.getMessage("account.doesnothaverole",
		new Object[] {}, LocaleContextHolder.getLocale()));
	}
	return account;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { InvalidUserInputException.class, RuntimeException.class })
    public Account login(User user) throws InvalidUserInputException {
	Account account = accountDao.findByEmail(user.getEmail());
	if (account != null) {
	    return account;
	} else {
	    SingUpForm form = new SingUpForm();
	    form.setEmail(user.getEmail());
	    form.setLogin(String.valueOf(user.getEmail().hashCode()));
	    form.setFio(user.getName());
	    UUID password = UUID.randomUUID();
	    form.setPassword(password.toString());
	    form.setPassword2(password.toString());

	    return singUp(form, false, true);
	}
    }

    @Override
    public List<Role> getAllRoles() {
	return roleDao.findAll();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { InvalidUserInputException.class, RuntimeException.class })
    public Account singUp(SingUpForm form) throws InvalidUserInputException {
	return singUp(form, true, false);
    }

    private Account singUp(SingUpForm form, boolean sendVerificationEmail, boolean sendPasswordToEmail)
	    throws InvalidUserInputException {
	Account exsistingAccount = accountDao.findByEmail(form.getEmail());
	if (exsistingAccount != null) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("login.busy", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	Account account = entityBuilder.buildAccount();
	form.copyFieldsTo(account);
	accountDao.save(account);

	AccountRegistration accountRegistration = entityBuilder.buildAccountRegistration(account);
	accountRegistrationDao.save(accountRegistration);

	Role role = roleDao.getStudentRole();
	AccountRole accountRole = entityBuilder.buildAccountRole(account, role);
	accountRoleDao.save(accountRole);

	if (sendVerificationEmail) {
	    account.setAccountRegistration(accountRegistration);
	    emailService.sendVerificationEmail(account);
	}

	if (sendPasswordToEmail) {
	    emailService.sendPasswordToEmail(account);
	}

	return account;
    }

    @Override
    public void sendPasswordForRecovery(String email) throws InvalidUserInputException {
	Account account = accountDao.findByEmail(email);
	if (account == null) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("login.badcredentials",
		new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (!account.getActive()) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("account.notactive", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (!account.getConfirmed()) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("account.notconfirmed", new Object[] {}, LocaleContextHolder.getLocale()));
	}

	emailService.sendPasswordToEmail(account);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { InvalidUserInputException.class, RuntimeException.class })
    public void verifyAccount(int id, String hash) throws InvalidUserInputException {
	Account account = accountDao.findById(id);
	if (account == null) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("confirmation.failed", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (account.getConfirmed()) {
	    throw new InvalidUserInputException(messageSource.getMessage("confirmation.confirmed", new Object[] {},
		    LocaleContextHolder.getLocale()));
	}
	if (!StringUtils.equals(account.getAccountRegistration().getHash(), hash)) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("confirmation.failed", new Object[] {}, LocaleContextHolder.getLocale()));
	}

	account.setConfirmed(Boolean.TRUE);
	accountDao.update(account);
    }

}
