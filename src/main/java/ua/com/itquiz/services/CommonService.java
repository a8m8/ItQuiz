package ua.com.itquiz.services;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AccountInfoForm;

/**
 * @author Artur Meshcheriakov
 */
public interface CommonService {

    Account getAccountById(int id);

    boolean editPersonalData(Account account, AccountInfoForm editDataForm)
            throws InvalidUserInputException;

}
