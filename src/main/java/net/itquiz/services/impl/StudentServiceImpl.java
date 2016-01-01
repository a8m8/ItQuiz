package net.itquiz.services.impl;

import net.itquiz.components.EntityBuilder;
import net.itquiz.dao.*;
import net.itquiz.entities.*;
import net.itquiz.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Service("studentService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentServiceImpl implements StudentService {

    @Autowired
    private TestDao testDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TestResultDao testResultDao;

    @Autowired
    private EntityBuilder entityBuilder;

    @Override
    public List<Test> getAvailableTests(int offset, int count) {
        List<Test> tests = testDao.getAvailableTest(offset, count);
        return tests;
    }

    @Override
    public long availableTestsCount() {
        return testDao.countAvailableTest();
    }

    @Override
    public TestResult prepareTestResult(int idAccount, long idTest) {
        Account account = accountDao.findById(idAccount);
        Test test = testDao.findById(idTest);
        int allQuestions = (int) questionDao.getActiveQuestionCount(idTest);
        TestResult testResult = entityBuilder.buildTestResult(account, test, allQuestions);
        return testResult;
    }

    @Override
    public Question getQuestion(long idTest, int question) {
        return questionDao.getActiveQuestionListOfTest(idTest, question, 1).get(0);
    }

    @Override
    public TestResult checkAnswers(List<String> answerIds, Question question, TestResult testResult) {
        if (answerIds == null) {
            return testResult;
        }
        List<Answer> answers = question.getAnswers();
        for (String answerId : answerIds) {
            for (Answer answer : answers) {
                if (answer.getIdAnswer().equals(Long.parseLong(answerId)) && !answer.getCorrect()) {
                    return testResult;
                }
            }
        }
        int correctCount = 0;
        for (Answer answer : answers) {
            if (answer.getCorrect()) {
                correctCount++;
            }
        }
        if (correctCount == answerIds.size()) {
            testResult.setCorrectCount(testResult.getCorrectCount() + 1);
        }
        return testResult;
    }

    @Override
    public void saveTestResult(TestResult testResult) {
        TestResult existingTestResult = testResultDao.getExistingTestResult(testResult.getTestTitle(),
                testResult.getAccount().getIdAccount());
        if (existingTestResult != null) {
            existingTestResult.setCorrectCount(testResult.getCorrectCount());
            existingTestResult.setAllQuestionsCount(testResult.getAllQuestionsCount());
            existingTestResult.setCreated(new Timestamp(System.currentTimeMillis()));
            existingTestResult.setTestTitle(testResult.getTestTitle());
            testResultDao.update(existingTestResult);
        } else {
            testResultDao.save(testResult);
        }

    }

    @Override
    public long userTestResultsCount(int idAccount) {
        return testResultDao.getUserTestResultsCount(idAccount);
    }

    @Override
    public List<TestResult> getTestResults(int idAccount, Integer offset, Integer count) {
        return testResultDao.getUserTestResults(idAccount, offset, count);
    }

    @Override
    public int getTimePerQuestion(long idTest) {
        Test test = testDao.findById(idTest);
        return test.getTimePerQuestion();
    }
}
