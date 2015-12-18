package ua.com.itquiz.components;

import ua.com.itquiz.entities.*;

/**
 * @author Artur Meshcheriakov
 */
public interface EntityBuilder {

    Account buildAccount();

    AccountRole buildAccountRole(Account account, Role role);

    AccountRegistration buildAccountRegistration(Account account);

    Test buildTest(Account account);

}
