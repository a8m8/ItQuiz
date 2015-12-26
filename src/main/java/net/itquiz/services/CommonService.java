package net.itquiz.services;

import net.itquiz.entities.Account;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.AccountInfoForm;
import net.itquiz.forms.PasswordForm;

/**
 * @author Artur Meshcheriakov
 */
public interface CommonService {

    Account getAccountById(int idAccount);

    void editPersonalData(int idAccount, AccountInfoForm editDataForm) throws InvalidUserInputException;

    void changePassword(int idAccount, PasswordForm passwordForm, boolean oldPasswordChecking) throws
            InvalidUserInputException;

    AccountInfoForm generateAccountForm(int idAccount);
}
