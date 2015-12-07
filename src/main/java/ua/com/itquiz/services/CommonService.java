package ua.com.itquiz.services;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AccountInfoForm;
import ua.com.itquiz.forms.PasswordForm;

/**
 * @author Artur Meshcheriakov
 */
public interface CommonService {

    Account getAccountById(int idAccount);

    void editPersonalData(int idAccount, AccountInfoForm editDataForm) throws InvalidUserInputException;

    void changePassword(int idAccount, PasswordForm passwordForm);
}
