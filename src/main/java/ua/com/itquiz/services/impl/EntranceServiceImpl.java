package ua.com.itquiz.services.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Role> getAllRoles() {
	return roleDao.findAll();
    }

    @Override
    @Transactional(readOnly = false,
		   rollbackFor = { InvalidUserInputException.class, RuntimeException.class })
    public Account singUp(SingUpForm form) throws InvalidUserInputException {
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

	account.setAccountRegistration(accountRegistration);

	emailService.sendVerificationEmail(account);

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

	emailService.sendPasswordForRecovery(account);
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
