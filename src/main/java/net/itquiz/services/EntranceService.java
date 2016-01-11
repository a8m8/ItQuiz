package net.itquiz.services;

import com.restfb.types.User;
import net.itquiz.entities.Account;
import net.itquiz.entities.Role;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.SignUpForm;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface EntranceService {

    Account login(User user) throws InvalidUserInputException;

    List<Role> listRoles();

    Account signUp(SignUpForm form) throws InvalidUserInputException;

    void sendPasswordRecoveryEmail(String email) throws InvalidUserInputException;

    void verifyAccount(int id, String hash) throws InvalidUserInputException;

    boolean isEmailExist(String email);

    boolean isLoginExist(String login);

    Account getAccount(String email);

    void checkAccess(short role, int currentIdAccount) throws InvalidUserInputException;

    void checkPasswordRecoveryAccess(int id, String passHash) throws InvalidUserInputException;
}
