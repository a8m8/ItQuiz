package ua.com.itquiz.dao.impl;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itquiz.dao.IEntityDao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public abstract class AbstractEntityDao<T> implements IEntityDao<T> {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

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

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void flush() {
        getSession().flush();
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
        return getSession().createCriteria(getEntityClass()).addOrder(Order.asc("created")).setFirstResult(offset)
                .setMaxResults(count).list();
    }
}
