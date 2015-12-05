package ua.com.itquiz.dao;

import ua.com.itquiz.entities.Account;

/**
 * @author Artur Meshcheriakov
 */
public interface AccountDao extends IEntityDao<Account> {

    Account findByEmail(String email);

    Account findByLogin(String login);

}
