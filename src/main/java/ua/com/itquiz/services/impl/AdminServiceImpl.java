package ua.com.itquiz.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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
import ua.com.itquiz.forms.AdminUserForm;
import ua.com.itquiz.services.AdminService;

/**
 *
 * @author Artur Meshcheriakov
 */
@Service("adminService")
@Transactional(readOnly = true)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AdminServiceImpl extends CommonServiceImpl implements AdminService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountRoleDao accountRoleDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AccountRegistrationDao accountRegistrationDao;

    @Autowired
    EntityBuilder entityBuilder;

    @Override
    public int accountCount() {
	return accountDao.findAll().size();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { InvalidUserInputException.class, RuntimeException.class })
    public void addUser(AdminUserForm adminUserForm) throws InvalidUserInputException {
	Account exsistingAccount = accountDao.findByEmail(adminUserForm.getEmail());
	if (exsistingAccount != null) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("email.exist", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	exsistingAccount = accountDao.findByLogin(adminUserForm.getLogin());
	if (exsistingAccount != null) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("login.busy", new Object[] {}, LocaleContextHolder.getLocale()));
	}

	Account account = entityBuilder.buildAccount();
	adminUserForm.copyFieldsTo(account);
	accountDao.save(account);

	AccountRegistration accountRegistration = entityBuilder.buildAccountRegistration(account);
	accountRegistrationDao.save(accountRegistration);
	Role role;
	AccountRole accountRole;
	if (adminUserForm.getAdministrator()) {
	    role = roleDao.getAdministratorRole();
	    accountRole = entityBuilder.buildAccountRole(account, role);
	    accountRoleDao.save(accountRole);
	}
	if (adminUserForm.getAdvancedTutor()) {
	    role = roleDao.getAdvancedTutorRole();
	    accountRole = entityBuilder.buildAccountRole(account, role);
	    accountRoleDao.save(accountRole);
	}
	if (adminUserForm.getTutor()) {
	    role = roleDao.getTutorRole();
	    accountRole = entityBuilder.buildAccountRole(account, role);
	    accountRoleDao.save(accountRole);
	}
	if (adminUserForm.getStudent()) {
	    role = roleDao.getStudentRole();
	    accountRole = entityBuilder.buildAccountRole(account, role);
	    accountRoleDao.save(accountRole);
	}

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { InvalidUserInputException.class, RuntimeException.class })
    public void editUser(AdminUserForm adminUserForm) {
	// TODO Auto-generated method stub
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { InvalidUserInputException.class, RuntimeException.class })
    public void removeAccount(int accountId) throws InvalidUserInputException {
	Account account = accountDao.findById(accountId);
	if (account == null) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("login.badcredentials", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	accountDao.delete(account);
    }

    @Override
    public List<Account> getAllAccounts() {
	List<Account> result = accountDao.findAll();
	for (Account account : result) {
	    account.getAccountRoles();
	}
	return result;
    }

    @Override
    public List<Account> getAccounts(int offset, int count) {
	return accountDao.list(offset, count);
    }

}
