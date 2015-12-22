package net.itquiz.security;

import net.itquiz.entities.Account;
import org.springframework.security.core.userdetails.User;

/**
 * @author Artur Meshcheriakov
 */
public class CurrentAccount extends User {

    private static final long serialVersionUID = 4142367500691984809L;

    private final int idAccount;
    private short role;

    public CurrentAccount(Account account) {
        super(account.getEmail(), account.getPassword(), account.getConfirmed(), true, true,
                account.getActive(), AuthentificationService.convert(account.getAccountRoles()));
        this.idAccount = account.getIdAccount();
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public int getIdAccount() {
        return idAccount;
    }

}
