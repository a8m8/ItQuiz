package ua.com.itquiz.services;

import java.util.List;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.Role;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.SingUpForm;

/**
 *
 * @author Artur Meshcheriakov
 */
public interface EntranceService {

    Account login(String login, String password, int role) throws InvalidUserInputException;

    List<Role> getAllRoles();

    Account singUp(SingUpForm form) throws InvalidUserInputException;

    void sendPasswordForRecovery(String login) throws InvalidUserInputException;

    void verifyAccount(int id, String hash) throws InvalidUserInputException;

}
