package ua.com.itquiz.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ua.com.itquiz.dao.TestDao;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.Test;

/**
 * 
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
    public List<Test> findAllTestCreatedBy(Account account) {
	return getSession().createCriteria(getEntityClass())
	    .add(Restrictions.eq("account", account)).list();
    }

}
