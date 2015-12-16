package ua.com.itquiz.services;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.admin.AdminAddUserForm;
import ua.com.itquiz.forms.admin.AdminUserForm;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface AdminService {

    long accountsCount();

    AdminUserForm generateFormBasedOnAccount(int idAccount);

    List<Account> getAccounts(int offset, int count);

    void addUser(AdminAddUserForm form) throws InvalidUserInputException;

    void editUser(int idAccount, AdminUserForm form) throws InvalidUserInputException;

    void removeAccount(int accountId) throws InvalidUserInputException;

}
