package net.itquiz.services;

import net.itquiz.entities.Account;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.admin.AdminAddUserForm;
import net.itquiz.forms.admin.AdminUserForm;

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
