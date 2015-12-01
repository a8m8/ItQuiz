package ua.com.itquiz.forms;

import ua.com.itquiz.entities.Account;

/**
 *
 * @author Artur Meshcheriakov
 */

public class SingUpForm extends AccountInfoForm implements Copyable<Account> {

    private static final long serialVersionUID = 2155252411443776689L;

    private Boolean confirmed = Boolean.FALSE;

    public Boolean getConfirmed() {
	return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
	this.confirmed = confirmed;
    }

    @Override
    public void copyFieldsTo(Account account) {
	account.setEmail(email);
	account.setLogin(login);
	account.setPassword(password);
	account.setFio(fio);
	account.setConfirmed(confirmed);
    }

}
