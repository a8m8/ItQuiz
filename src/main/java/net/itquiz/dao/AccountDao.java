package net.itquiz.dao;

import net.itquiz.entities.Account;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface AccountDao extends IEntityDao<Account> {

    Account findByEmail(String email);

    Account findByLogin(String login);

    long accountsCount();

    long countUnconfirmedAccounts();

    List<Account> getAllUnconfirmed();
}
