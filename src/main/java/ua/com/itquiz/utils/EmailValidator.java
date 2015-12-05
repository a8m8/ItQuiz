package ua.com.itquiz.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author Artur Meshcheriakov
 */
@Component("emailValidator")
public class EmailValidator {

    private Pattern emailPattern = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public boolean isValid(String email) {
        return emailPattern.matcher(email).matches();
    }

}
