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

    long countAllAccounts();

    List<Account> listAccounts(int offset, int count);

    AdminUserForm generateFormBasedOnAccount(int idAccount);

    void addAccount(AdminAddUserForm form) throws InvalidUserInputException;

    void editAccount(int idAccount, AdminUserForm form) throws InvalidUserInputException;

    void removeAccount(int idAccount) throws InvalidUserInputException;

}
