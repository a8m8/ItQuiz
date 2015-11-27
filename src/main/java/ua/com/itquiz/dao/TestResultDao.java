package ua.com.itquiz.dao;

import java.util.List;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.TestResult;

/**
 * 
 * @author Artur Meshcheriakov
 */
public interface TestResultDao extends IEntityDao<TestResult> {

    List<TestResult> getUserTestResult(Account account);

}
