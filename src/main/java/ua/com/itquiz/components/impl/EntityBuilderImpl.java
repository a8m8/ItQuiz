package ua.com.itquiz.components.impl;

import org.springframework.stereotype.Component;
import ua.com.itquiz.components.EntityBuilder;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.AccountRegistration;
import ua.com.itquiz.entities.AccountRole;
import ua.com.itquiz.entities.Role;

/**
 * @author Artur Meshcheriakov
 */
@Component("entityBuilder")
public class EntityBuilderImpl implements EntityBuilder {

    @Override
    public Account buildAccount() {
        return new Account();
    }

    @Override
    public AccountRegistration buildAccountRegistration(Account account) {

        AccountRegistration accountRegistration = new AccountRegistration();
        accountRegistration.setAccount(account);
        accountRegistration.setHash(String.valueOf(account.getLogin().hashCode()));

        return accountRegistration;

    }

    @Override
    public AccountRole buildAccountRole(Account account, Role role) {
        AccountRole accountRole = new AccountRole();
        accountRole.setAccount(account);
        accountRole.setRole(role);
        return accountRole;
    }

}
