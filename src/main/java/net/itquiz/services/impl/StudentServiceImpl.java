package net.itquiz.services.impl;

import net.itquiz.components.EntityBuilder;
import net.itquiz.dao.AccountDao;
import net.itquiz.dao.QuestionDao;
import net.itquiz.dao.TestDao;
import net.itquiz.dao.TestResultDao;
import net.itquiz.entities.Account;
import net.itquiz.entities.Answer;
import net.itquiz.entities.Question;
import net.itquiz.entities.Test;
import net.itquiz.entities.TestResult;
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
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentServiceImpl implements StudentService {

    @Autowired
    private TestDao testDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TestResultDao testResultDao;

    @Autowired
    private EntityBuilder entityBuilder;

    @Transactional
    @Override
    public List<Test> listAvailableTests(int offset, int count) {
        List<Test> tests = testDao.listAvailable(offset, count);
        return tests;
    }

    @Transactional
    @Override
    public long countAvailableTests() {
        return testDao.countAvailable();
    }

    @Transactional
    @Override
    public TestResult prepareTestResult(int idAccount, long idTest) {
        Account account = accountDao.findById(idAccount);
        Test test = testDao.getProxy(idTest);
        int allQuestions = (int) questionDao.countActiveQuestions(idTest);
        TestResult testResult = entityBuilder.buildTestResult(account, test, allQuestions);
        return testResult;
    }

    @Transactional
    @Override
    public Question getQuestion(long idTest, int question) {
        return questionDao.listActiveRelatedTo(idTest, question, 1).get(0);
    }

    @Transactional
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

    @Transactional
    @Override
    public void saveTestResult(TestResult testResult) {
        TestResult existingTestResult = testResultDao.findByTestTitleRelatedTo(testResult.getAccount().getIdAccount()
                , testResult.getTestTitle());
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

    @Transactional
    @Override
    public long countAccountTestResults(int idAccount) {
        return testResultDao.countAllRelatedTo(idAccount);
    }

    @Transactional
    @Override
    public List<TestResult> listTestResults(int idAccount, Integer offset, Integer count) {
        return testResultDao.listRelatedTo(idAccount, offset, count);
    }

    @Transactional
    @Override
    public int getTimePerQuestion(long idTest) {
        Test test = testDao.findById(idTest);
        return test.getTimePerQuestion();
    }
}
