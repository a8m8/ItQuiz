package ua.com.itquiz.security;

import org.springframework.security.core.userdetails.User;

import ua.com.itquiz.entities.Account;

/**
 *
 * @author Artur Meshcheriakov
 */
public class CurrentAccount extends User {

    private static final long serialVersionUID = 4142367500691984809L;

    private final int idAccount;
    private int role;

    public CurrentAccount(Account account) {
	super(account.getEmail(), account.getPassword(), account.getConfirmed(), true, true,
	    account.getActive(), AuthentificationService.convert(account.getAccountRoles()));
	this.idAccount = account.getIdAccount();
    }

    public int getRole() {
	return role;
    }

    public void setRole(int role) {
	this.role = role;
    }

    public int getIdAccount() {
	return idAccount;
    }

}
