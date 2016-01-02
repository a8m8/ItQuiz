package net.itquiz.forms.admin;

import net.itquiz.entities.Account;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.Copyable;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Artur Meshcheriakov
 */
public class AdminAddUserForm extends AdminUserForm implements Copyable<Account> {

    protected String password;
    protected String passwordConfirmed;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
        super.validate(messageSource);
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

    @Override
    public void copyFieldsTo(Account account) {
        account.setEmail(email);
        account.setPassword(password);
        account.setLogin(login);
        account.setFio(fio);
        account.setConfirmed(confirmed);
        account.setActive(active);
    }

}
