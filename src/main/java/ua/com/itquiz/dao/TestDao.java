package ua.com.itquiz.dao;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.Test;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TestDao extends IEntityDao<Test> {

    List<Test> findAllTestCreatedBy(Account account);

}
