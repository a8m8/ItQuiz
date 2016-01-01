package net.itquiz.dao;

import net.itquiz.entities.TestResult;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TestResultDao extends IEntityDao<TestResult> {

    List<TestResult> getUserTestResults(int idAccount, int offset, int count);

    long getUserTestResultsCount(int idAccount);

    TestResult getExistingTestResult(String testTitle, int idAccount);
}
