package ua.com.itquiz.dao;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.TestResult;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TestResultDao extends IEntityDao<TestResult> {

    List<TestResult> getUserTestResult(Account account);

}
