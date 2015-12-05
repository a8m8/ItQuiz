package ua.com.itquiz.services;

import java.util.List;

import com.restfb.types.User;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.Role;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.SignUpForm;

/**
 *
 * @author Artur Meshcheriakov
 */
public interface EntranceService {

    Account login(User user) throws InvalidUserInputException;

    List<Role> getAllRoles();

    Account signUp(SignUpForm form) throws InvalidUserInputException;

    void sendPasswordForRecovery(String email) throws InvalidUserInputException;

    void verifyAccount(int id, String hash) throws InvalidUserInputException;

    boolean isEmailExist(String email);

    boolean isLoginExist(String login);

    Account getAccount(String email);

}
