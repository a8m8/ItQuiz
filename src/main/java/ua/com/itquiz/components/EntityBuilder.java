package ua.com.itquiz.components;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.AccountRegistration;
import ua.com.itquiz.entities.AccountRole;
import ua.com.itquiz.entities.Role;

/**
 *
 * @author Artur Meshcheriakov
 */
public interface EntityBuilder {

    Account buildAccount();

    AccountRole buildAccountRole(Account account, Role role);

    AccountRegistration buildAccountRegistration(Account account);

}
