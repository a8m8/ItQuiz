package net.itquiz.dao.impl;

import net.itquiz.dao.AccountDao;
import net.itquiz.entities.Account;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Artur Meshcheriakov
 */
@Repository("accountDao")
public class AccountDaoImpl extends AbstractEntityDao<Account> implements AccountDao {

    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Account findByEmail(final String email) {
        return (Account) getSession().createCriteria(getEntityClass()).add(Restrictions.eq("email", email).ignoreCase())
                .uniqueResult();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Account findByLogin(final String login) {
        return (Account) getSession().createCriteria(getEntityClass()).add(Restrictions.eq("login", login))
                .uniqueResult();
    }

    @Override
    public long accountsCount() {
        return (long) getSession().createCriteria(getEntityClass()).setProjection(Projections.rowCount())
                .uniqueResult();
    }
}
