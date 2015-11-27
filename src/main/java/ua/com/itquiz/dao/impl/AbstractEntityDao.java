package ua.com.itquiz.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itquiz.dao.IEntityDao;

/**
 * 
 * @author Artur Meshcheriakov
 */
public abstract class AbstractEntityDao<T> implements IEntityDao<T> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getSession() {
	return sessionFactory.getCurrentSession();
    }

    protected abstract Class<T> getEntityClass();

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void save(final T t) {
	getSession().save(t);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void update(final T t) {
	getSession().update(t);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void delete(final T t) {
	getSession().delete(t);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void remove(final T t) {
	delete(t);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public T findById(final Serializable id) {
	return getSession().get(getEntityClass(), id);
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public List<T> findAll() {
	return getSession().createCriteria(getEntityClass()).list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public List<T> list(final int offset, final int count) {
	return getSession().createCriteria(getEntityClass()).setFirstResult(offset)
	    .setMaxResults(count).list();
    }
}
