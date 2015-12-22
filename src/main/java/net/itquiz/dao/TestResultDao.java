package net.itquiz.dao;

import net.itquiz.entities.Account;
import net.itquiz.entities.TestResult;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TestResultDao extends IEntityDao<TestResult> {

    List<TestResult> getUserTestResult(Account account);

}
