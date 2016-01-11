package net.itquiz.services;

import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.AccountInfoForm;
import net.itquiz.forms.PasswordForm;

/**
 * @author Artur Meshcheriakov
 */
public interface CommonService {

    void editPersonalData(int idAccount, AccountInfoForm editDataForm) throws InvalidUserInputException;

    void changePassword(int idAccount, PasswordForm passwordForm, boolean oldPasswordChecking) throws
            InvalidUserInputException;

    AccountInfoForm generateAccountInfoForm(int idAccount);
}
