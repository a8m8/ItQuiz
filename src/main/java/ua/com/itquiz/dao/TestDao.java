package ua.com.itquiz.dao;

import java.util.List;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.Test;

/**
 * 
 * @author Artur Meshcheriakov
 */
public interface TestDao extends IEntityDao<Test> {

    List<Test> findAllTestCreatedBy(Account account);

}
