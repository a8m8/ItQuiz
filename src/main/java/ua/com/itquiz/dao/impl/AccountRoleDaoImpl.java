package ua.com.itquiz.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.itquiz.dao.AccountRoleDao;
import ua.com.itquiz.entities.AccountRole;

/**
 * @author Artur Meshcheriakov
 */
@Repository("accountRoleDao")
public class AccountRoleDaoImpl extends AbstractEntityDao<AccountRole> implements AccountRoleDao {

    @Override
    protected Class<AccountRole> getEntityClass() {
        return AccountRole.class;
    }

}
