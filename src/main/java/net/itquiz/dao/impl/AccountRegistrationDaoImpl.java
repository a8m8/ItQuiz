package net.itquiz.dao.impl;

import net.itquiz.dao.AccountRegistrationDao;
import net.itquiz.entities.AccountRegistration;
import org.springframework.stereotype.Repository;

/**
 * @author Artur Meshcheriakov
 */
@Repository("accountRegistrationDao")
public class AccountRegistrationDaoImpl extends AbstractEntityDao<AccountRegistration>
        implements AccountRegistrationDao {

    @Override
    protected Class<AccountRegistration> getEntityClass() {
        return AccountRegistration.class;
    }

}
