package net.itquiz.services.impl;

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
import net.itquiz.forms.admin.AdminAddUserForm;
import net.itquiz.forms.admin.AdminUserForm;
import net.itquiz.services.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Service("adminService")
@Transactional
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
    private EntityBuilder entityBuilder;

    @Override
    public long accountsCount() {
        return accountDao.accountsCount();
    }

    @Override
    public void addUser(AdminAddUserForm adminAddUserForm) throws InvalidUserInputException {
        isEmailExist(adminAddUserForm.getEmail());
        isLoginExist(adminAddUserForm.getLogin());

        Account account = entityBuilder.buildAccount();
        adminAddUserForm.copyFieldsTo(account);
        accountDao.save(account);

        AccountRegistration accountRegistration = entityBuilder.buildAccountRegistration(account);
        accountRegistrationDao.save(accountRegistration);
        Role role;
        AccountRole accountRole;
        if (adminAddUserForm.getAdministrator()) {
            role = roleDao.findById(ApplicationConstants.ADMIN_ROLE);
            accountRole = entityBuilder.buildAccountRole(account, role);
            accountRoleDao.save(accountRole);
        }
        if (adminAddUserForm.getAdvancedTutor()) {
            role = roleDao.findById(ApplicationConstants.ADVANCED_TUTOR_ROLE);
            accountRole = entityBuilder.buildAccountRole(account, role);
            accountRoleDao.save(accountRole);
        }
        if (adminAddUserForm.getTutor()) {
            role = roleDao.findById(ApplicationConstants.TUTOR_ROLE);
            accountRole = entityBuilder.buildAccountRole(account, role);
            accountRoleDao.save(accountRole);
        }
        if (adminAddUserForm.getStudent()) {
            role = roleDao.findById(ApplicationConstants.STUDENT_ROLE);
            accountRole = entityBuilder.buildAccountRole(account, role);
            accountRoleDao.save(accountRole);
        }

    }

    @Override
    public AdminUserForm generateFormBasedOnAccount(int idAccount) {
        Account account = accountDao.findById(idAccount);
        AdminUserForm adminUserForm = new AdminUserForm();
        adminUserForm.setEmail(account.getEmail());
        adminUserForm.setLogin(account.getLogin());
        adminUserForm.setFio(account.getFio());
        adminUserForm.setActive(account.getActive());
        adminUserForm.setConfirmed(account.getConfirmed());

        HashSet<Short> accountRolesSet = new HashSet<>();
        for (AccountRole accountRole : account.getAccountRoles()) {
            accountRolesSet.add(accountRole.getRole().getIdRole());
        }
        if (accountRolesSet.contains(ApplicationConstants.ADMIN_ROLE)) {
            adminUserForm.setAdministrator(Boolean.TRUE);
        }
        if (accountRolesSet.contains(ApplicationConstants.ADVANCED_TUTOR_ROLE)) {
            adminUserForm.setAdvancedTutor(Boolean.TRUE);
        }
        if (accountRolesSet.contains(ApplicationConstants.TUTOR_ROLE)) {
            adminUserForm.setTutor(Boolean.TRUE);
        }
        if (accountRolesSet.contains(ApplicationConstants.STUDENT_ROLE)) {
            adminUserForm.setStudent(Boolean.TRUE);
        }
        return adminUserForm;
    }

    @Override
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

        HashSet<Short> roles = new HashSet<>();
        for (AccountRole role : account.getAccountRoles()) {
            roles.add(role.getRole().getIdRole());
        }

        if (adminUserForm.getAdministrator() && !roles.contains(ApplicationConstants.ADMIN_ROLE)) {
            AccountRole accountRole = entityBuilder.buildAccountRole(account,
                    roleDao.findById(ApplicationConstants.ADMIN_ROLE));
            accountRoleDao.save(accountRole);
            isNewRole = true;
        }
        if (!adminUserForm.getAdministrator() && roles.contains(ApplicationConstants.ADMIN_ROLE)) {
            isNewRole = true;
            accountRoleDao.delete(getAdministratorRoleOf(account));
        }

        if (adminUserForm.getAdvancedTutor() && !roles.contains(ApplicationConstants.ADVANCED_TUTOR_ROLE)) {
            AccountRole accountRole = entityBuilder.buildAccountRole(account,
                    roleDao.findById(ApplicationConstants.ADVANCED_TUTOR_ROLE));
            accountRoleDao.save(accountRole);
            isNewRole = true;
        }
        if (!adminUserForm.getAdvancedTutor() && roles.contains(ApplicationConstants.ADVANCED_TUTOR_ROLE)) {
            isNewRole = true;
            accountRoleDao.delete(getAdvancedTutorRoleOf(account));
        }

        if (adminUserForm.getTutor() && !roles.contains(ApplicationConstants.TUTOR_ROLE)) {
            AccountRole accountRole = entityBuilder.buildAccountRole(account,
                    roleDao.findById(ApplicationConstants.TUTOR_ROLE));
            accountRoleDao.save(accountRole);
            isNewRole = true;
        }
        if (!adminUserForm.getTutor() && roles.contains(ApplicationConstants.TUTOR_ROLE)) {
            isNewRole = true;
            accountRoleDao.delete(getTutorRoleOf(account));
        }

        if (adminUserForm.getStudent() && !roles.contains(ApplicationConstants.STUDENT_ROLE)) {
            AccountRole accountRole = entityBuilder.buildAccountRole(account,
                    roleDao.findById(ApplicationConstants.STUDENT_ROLE));
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
            if (accountRole.getRole().getIdRole() == ApplicationConstants.ADMIN_ROLE) {
                result = accountRole;
            }
        }
        return result;
    }

    private AccountRole getAdvancedTutorRoleOf(Account account) {
        AccountRole result = null;
        for (AccountRole accountRole : account.getAccountRoles()) {
            if (accountRole.getRole().getIdRole() == ApplicationConstants.ADVANCED_TUTOR_ROLE) {
                result = accountRole;
            }
        }
        return result;
    }

    private AccountRole getTutorRoleOf(Account account) {
        AccountRole result = null;
        for (AccountRole accountRole : account.getAccountRoles()) {
            if (accountRole.getRole().getIdRole() == ApplicationConstants.TUTOR_ROLE) {
                result = accountRole;
            }
        }
        return result;
    }

    private AccountRole getStudentRoleOf(Account account) {
        AccountRole result = null;
        for (AccountRole accountRole : account.getAccountRoles()) {
            if (accountRole.getRole().getIdRole() == ApplicationConstants.STUDENT_ROLE) {
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
    public void removeAccount(int accountId) throws InvalidUserInputException {
        Account account = accountDao.findById(accountId);
        if (account == null) {
            throw new InvalidUserInputException(messageSource.getMessage("account.not.found",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        accountDao.delete(account);
    }

    @Override
    public List<Account> getAccounts(int offset, int count) {
        return accountDao.list(offset, count);
    }

}
