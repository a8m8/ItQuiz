package ua.com.itquiz.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.itquiz.dao.RoleDao;
import ua.com.itquiz.entities.Role;

/**
 * @author Artur Meshcheriakov
 */
@Repository("roleDao")
public class RoleDaoImpl extends AbstractEntityDao<Role> implements RoleDao {

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

}
