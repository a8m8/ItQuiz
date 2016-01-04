package net.itquiz.dao.impl;

import net.itquiz.dao.AccountRegistrationDao;
import net.itquiz.entities.AccountRegistration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public long countEntityWithPasswordHash() {
        return (long) getSession().createCriteria(getEntityClass()).add(Restrictions.isNotNull("passHash"))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List<AccountRegistration> getAllEntityWithPasswordHash() {
        return getSession().createCriteria(getEntityClass()).add(Restrictions.isNotNull("passHash")).
                addOrder(Order.desc("passHashCreated")).list();
    }
}
