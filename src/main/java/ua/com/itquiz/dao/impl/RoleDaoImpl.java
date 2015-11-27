package ua.com.itquiz.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itquiz.dao.RoleDao;
import ua.com.itquiz.entities.Role;

/**
 * 
 * @author Artur Meshcheriakov
 */
@Repository("roleDao")
public class RoleDaoImpl extends AbstractEntityDao<Role> implements RoleDao {

    @Override
    protected Class<Role> getEntityClass() {
	return Role.class;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Role getAdministratorRole() {
	return (Role) getSession().createCriteria(getEntityClass())
	    .add(Restrictions.eq("title", "administrator")).uniqueResult();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Role getAdvancedTutorRole() {
	return (Role) getSession().createCriteria(getEntityClass())
	    .add(Restrictions.eq("title", "advanced tutor")).uniqueResult();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Role getTutorRole() {
	return (Role) getSession().createCriteria(getEntityClass())
	    .add(Restrictions.eq("title", "tutor")).uniqueResult();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Role getStudentRole() {
	return (Role) getSession().createCriteria(getEntityClass())
	    .add(Restrictions.eq("title", "student")).uniqueResult();
    }

}
