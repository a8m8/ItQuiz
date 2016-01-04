package net.itquiz.dao;

import net.itquiz.entities.AccountRegistration;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface AccountRegistrationDao extends IEntityDao<AccountRegistration> {

    long countEntityWithPasswordHash();

    List<AccountRegistration> getAllEntityWithPasswordHash();
}
