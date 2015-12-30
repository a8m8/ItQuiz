package net.itquiz.services;

import net.itquiz.entities.Question;
import net.itquiz.entities.Test;
import net.itquiz.entities.TestResult;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface StudentService {

    List<Test> getAvailableTests(int offset, int count);

    long availableTestsCount();

    TestResult prepareTestResult(int idAccount, long idTest);

    Question getQuestion(long idTest, int question);

    TestResult checkAnswers(List<String> answerIds, Question question, TestResult testResult);

    void saveTestResult(TestResult testResult);

    long userTestResultsCount(int idAccount);

    List<TestResult> getTestResults(int idAccount, Integer offset, Integer count);

    int getTimePerQuestion(long idTest);
}
