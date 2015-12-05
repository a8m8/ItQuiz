package ua.com.itquiz.dao;

import ua.com.itquiz.entities.Role;

/**
 * @author Artur Meshcheriakov
 */
public interface RoleDao extends IEntityDao<Role> {

    Role getAdministratorRole();

    Role getAdvancedTutorRole();

    Role getTutorRole();

    Role getStudentRole();

}
