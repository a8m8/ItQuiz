package net.itquiz.dao.impl;

import net.itquiz.dao.RoleDao;
import net.itquiz.entities.Role;
import org.springframework.stereotype.Repository;

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
