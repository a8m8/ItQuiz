package net.itquiz.dao.impl;

import net.itquiz.dao.TestDao;
import net.itquiz.entities.Test;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Repository("testDao")
public class TestDaoImpl extends AbstractEntityDao<Test> implements TestDao {

    @Override
    protected Class<Test> getEntityClass() {
        return Test.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Test> listCreatedBy(int idAccount, int offset, int count) {
        return getSession().createCriteria(getEntityClass()).add(Restrictions.eq("account.idAccount", idAccount)).
                addOrder(Order.asc("created")).setFirstResult(offset).setMaxResults(count).list();
    }

    @Override
    public long countCreatedBy(int idAccount) {
        return (long) getSession().createCriteria(getEntityClass()).add(Restrictions.eq("account.idAccount",
                idAccount)).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public long countAll() {
        return (long) getSession().createCriteria(getEntityClass()).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List<Test> listAvailable(int offset, int count) {
        return getSession().createCriteria(getEntityClass()).add(Restrictions.eq("active", true)).
                addOrder(Order.asc("created")).setFirstResult(offset).setMaxResults(count).list();
    }

    @Override
    public long countAvailable() {
        return (long) getSession().createCriteria(getEntityClass()).add(Restrictions.eq("active", true)).setProjection
                (Projections.rowCount()).uniqueResult();
    }

    @Override
    public Test findByTitle(String title) {
        return (Test) getSession().createCriteria(getEntityClass()).add(Restrictions.eq("title", title)
                .ignoreCase()).uniqueResult();
    }
}
