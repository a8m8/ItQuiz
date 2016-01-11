package net.itquiz.forms.utils;

import net.itquiz.exceptions.InvalidUserInputException;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Artur Meshcheriakov
 */
public class PasswordChecker {

    public static void checkPasswords(String password, String passwordConfirmed, MessageSource messageSource) throws InvalidUserInputException {
        if (StringUtils.isBlank(password)) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("password.required", new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (password.length() > 60) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("volume.exceed", new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (!StringUtils.equals(password, passwordConfirmed)) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("passwords.not.match", new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }
}
