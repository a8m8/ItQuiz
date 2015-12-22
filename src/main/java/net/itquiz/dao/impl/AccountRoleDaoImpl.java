package net.itquiz.dao.impl;

import net.itquiz.dao.AccountRoleDao;
import net.itquiz.entities.AccountRole;
import org.springframework.stereotype.Repository;

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
