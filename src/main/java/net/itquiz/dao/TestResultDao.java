package net.itquiz.dao;

import net.itquiz.entities.TestResult;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TestResultDao extends IEntityDao<TestResult> {

    List<TestResult> listRelatedTo(int idAccount, int offset, int count);

    long countAllRelatedTo(int idAccount);

    TestResult findByTestTitleRelatedTo(int idAccount, String testTitle);
}
