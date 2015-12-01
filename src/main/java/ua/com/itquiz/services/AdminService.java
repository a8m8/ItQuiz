package ua.com.itquiz.services;

import java.util.List;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AdminUserForm;

/**
 *
 * @author Artur Meshcheriakov
 */
public interface AdminService {

    int accountCount();

    List<Account> getAllAccounts();

    void addUser(AdminUserForm form) throws InvalidUserInputException;

    void editUser(AdminUserForm form) throws InvalidUserInputException;

    void removeAccount(int accountId) throws InvalidUserInputException;

}
