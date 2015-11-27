package ua.com.itquiz.services;

import java.util.List;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.forms.IForm;

/**
 *
 * @author Artur Meshcheriakov
 */
public interface AdminService {

    int accountCount();

    List<Account> getAllAccounts();

    void addUser(IForm form);

    void editAccount(IForm form);

    void removeAccount(int accountId);

    void activateAccount(int accountId);

    void deactivateAccount(int accountId);

}
