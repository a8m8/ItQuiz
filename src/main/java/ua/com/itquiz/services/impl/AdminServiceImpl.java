package ua.com.itquiz.services.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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
import ua.com.itquiz.forms.AdminUserForm;
import ua.com.itquiz.services.AdminService;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

/**
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
    @Transactional(readOnly = false,
            rollbackFor = {InvalidUserInputException.class, RuntimeException.class})
    public void addUser(AdminUserForm adminUserForm) throws InvalidUserInputException {
        isEmailExist(adminUserForm.getEmail());
        isLoginExist(adminUserForm.getLogin());

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
    @Transactional(readOnly = false,
            rollbackFor = {InvalidUserInputException.class, RuntimeException.class})
    public void editUser(int idAccount, AdminUserForm adminUserForm)
            throws InvalidUserInputException {
        Account account = accountDao.findById(idAccount);
        boolean isNewAccountInfo = false;
        boolean isNewRole = false;
        if (!StringUtils.equals(account.getLogin(), adminUserForm.getLogin())) {
            isLoginExist(adminUserForm.getLogin());
            account.setLogin(adminUserForm.getLogin());
            isNewAccountInfo = true;
        }
        if (!StringUtils.equals(account.getFio(), adminUserForm.getFio())) {
            account.setFio(adminUserForm.getFio());
            isNewAccountInfo = true;
        }
        if (!adminUserForm.getActive().equals(account.getActive())) {
            account.setActive(adminUserForm.getActive());
            isNewAccountInfo = true;
        }
        if (!adminUserForm.getConfirmed().equals(account.getConfirmed())) {
            account.setConfirmed(adminUserForm.getConfirmed());
            isNewAccountInfo = true;
        }

        HashSet<Integer> roles = new HashSet<>();
        for (AccountRole role : account.getAccountRoles()) {
            roles.add(role.getRole().getIdRole().intValue());
        }

        if (adminUserForm.getAdministrator() && !roles.contains(ApplicationConstants.ADMIN_ROLE)) {
            AccountRole accountRole = entityBuilder.buildAccountRole(account, roleDao.getAdministratorRole());
            accountRoleDao.save(accountRole);
            isNewRole = true;
        }
        if (!adminUserForm.getAdministrator() && roles.contains(ApplicationConstants.ADMIN_ROLE)) {
            isNewRole = true;
            accountRoleDao.delete(getAdministratorRoleOf(account));
        }

        if (adminUserForm.getAdvancedTutor() && !roles.contains(ApplicationConstants.ADVANCED_TUTOR_ROLE)) {
            AccountRole accountRole = entityBuilder.buildAccountRole(account, roleDao.getAdvancedTutorRole());
            accountRoleDao.save(accountRole);
            isNewRole = true;
        }
        if (!adminUserForm.getAdvancedTutor() && roles.contains(ApplicationConstants.ADVANCED_TUTOR_ROLE)) {
            isNewRole = true;
            accountRoleDao.delete(getAdvancedTutorRoleOf(account));
        }

        if (adminUserForm.getTutor() && !roles.contains(ApplicationConstants.TUTOR_ROLE)) {
            AccountRole accountRole = entityBuilder.buildAccountRole(account, roleDao.getTutorRole());
            accountRoleDao.save(accountRole);
            isNewRole = true;
        }
        if (!adminUserForm.getTutor() && roles.contains(ApplicationConstants.TUTOR_ROLE)) {
            isNewRole = true;
            accountRoleDao.delete(getTutorRoleOf(account));
        }

        if (adminUserForm.getStudent() && !roles.contains(ApplicationConstants.STUDENT_ROLE)) {
            AccountRole accountRole = entityBuilder.buildAccountRole(account, roleDao.getStudentRole());
            accountRoleDao.save(accountRole);
            isNewRole = true;
        }
        if (!adminUserForm.getStudent() && roles.contains(ApplicationConstants.STUDENT_ROLE)) {
            isNewRole = true;
            accountRoleDao.delete(getStudentRoleOf(account));
        }

        if (isNewAccountInfo || isNewRole) {
            account.setUpdated(new Timestamp(System.currentTimeMillis()));
            accountDao.save(account);
        } else {
            throw new InvalidUserInputException(messageSource.getMessage("nothing.save",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }

    }

    private AccountRole getAdministratorRoleOf(Account account) {
        AccountRole result = null;
        for (AccountRole accountRole : account.getAccountRoles()) {
            if (accountRole.getRole().getIdRole().intValue() == ApplicationConstants.ADMIN_ROLE) {
                result = accountRole;
            }
        }
        return result;
    }

    private AccountRole getAdvancedTutorRoleOf(Account account) {
        AccountRole result = null;
        for (AccountRole accountRole : account.getAccountRoles()) {
            if (accountRole.getRole().getIdRole().intValue() == ApplicationConstants.ADVANCED_TUTOR_ROLE) {
                result = accountRole;
            }
        }
        return result;
    }

    private AccountRole getTutorRoleOf(Account account) {
        AccountRole result = null;
        for (AccountRole accountRole : account.getAccountRoles()) {
            if (accountRole.getRole().getIdRole().intValue() == ApplicationConstants.TUTOR_ROLE) {
                result = accountRole;
            }
        }
        return result;
    }

    private AccountRole getStudentRoleOf(Account account) {
        AccountRole result = null;
        for (AccountRole accountRole : account.getAccountRoles()) {
            if (accountRole.getRole().getIdRole().intValue() == ApplicationConstants.STUDENT_ROLE) {
                result = accountRole;
            }
        }
        return result;
    }

    private void isEmailExist(String email) throws InvalidUserInputException {
        Account exsistingAccount = accountDao.findByEmail(email);
        if (exsistingAccount != null) {
            throw new InvalidUserInputException(messageSource.getMessage("email.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

    private void isLoginExist(String login) throws InvalidUserInputException {
        Account exsistingAccount = accountDao.findByLogin(login);
        if (exsistingAccount != null) {
            throw new InvalidUserInputException(messageSource.getMessage("login.busy",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @Transactional(readOnly = false,
            rollbackFor = {InvalidUserInputException.class, RuntimeException.class})
    public void removeAccount(int accountId) throws InvalidUserInputException {
        Account account = accountDao.findById(accountId);
        if (account == null) {
            throw new InvalidUserInputException(messageSource.getMessage("login.badcredentials",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        accountDao.delete(account);
    }

    @Override
    public List<Account> getAccounts(int offset, int count) {
        return accountDao.list(offset, count);
    }

}
