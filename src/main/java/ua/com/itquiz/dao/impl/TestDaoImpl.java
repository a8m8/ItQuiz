package ua.com.itquiz.dao.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.com.itquiz.dao.TestDao;
import ua.com.itquiz.entities.Test;

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
    public List<Test> listTestsCreatedBy(int idAccount, int offset, int count) {
        return getSession().createCriteria(getEntityClass()).add(Restrictions.eq("account.idAccount", idAccount)).
                addOrder(Order.asc("created")).setFirstResult(offset).setMaxResults(count).list();
    }

    @Override
    public long countTestsCreatedBy(int idAccount) {
        return (long) getSession().createCriteria(getEntityClass()).add(Restrictions.eq("account.idAccount",
                idAccount)).setProjection(Projections.rowCount()).uniqueResult();
    }

}
