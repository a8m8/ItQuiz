package net.itquiz.dao;

import net.itquiz.entities.Account;

/**
 * @author Artur Meshcheriakov
 */
public interface AccountDao extends IEntityDao<Account> {

    Account findByEmail(String email);

    Account findByLogin(String login);

    long accountsCount();

}
