package ua.com.itquiz.components.impl;

import org.springframework.stereotype.Component;
import ua.com.itquiz.components.EntityBuilder;
import ua.com.itquiz.entities.*;

import java.util.UUID;

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
        accountRegistration.setIdAccountRegistration(account.getIdAccount());
        accountRegistration.setAccount(account);
        accountRegistration.setHash(UUID.randomUUID().toString());

        return accountRegistration;

    }

    @Override
    public AccountRole buildAccountRole(Account account, Role role) {
        AccountRole accountRole = new AccountRole();
        accountRole.setAccount(account);
        accountRole.setRole(role);
        return accountRole;
    }

    @Override
    public Test buildTest(Account account) {
        Test test = new Test();
        test.setAccount(account);
        return test;
    }
}
