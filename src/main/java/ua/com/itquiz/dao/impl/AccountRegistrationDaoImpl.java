package ua.com.itquiz.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.itquiz.dao.AccountRegistrationDao;
import ua.com.itquiz.entities.AccountRegistration;

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
