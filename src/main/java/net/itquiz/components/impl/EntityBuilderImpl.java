package net.itquiz.components.impl;

import net.itquiz.components.EntityBuilder;
import net.itquiz.entities.Account;
import net.itquiz.entities.AccountRegistration;
import net.itquiz.entities.AccountRole;
import net.itquiz.entities.Answer;
import net.itquiz.entities.Question;
import net.itquiz.entities.Role;
import net.itquiz.entities.Test;
import net.itquiz.entities.TestResult;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Artur Meshcheriakov
 */
@Component("entityBuilder")
public class EntityBuilderImpl implements EntityBuilder {

    @Override
    public Account buildAccount() {
        return new Account();
    }

    @Override
    public AccountRegistration buildAccountRegistration(Account account) {

        AccountRegistration accountRegistration = new AccountRegistration();
        accountRegistration.setIdAccountRegistration(account.getIdAccount());
        accountRegistration.setAccount(account);
        accountRegistration.setHash(UUID.randomUUID().toString());

        return accountRegistration;

    }

    @Override
    public AccountRole buildAccountRole(Account account, Role role) {
        AccountRole accountRole = new AccountRole();
        accountRole.setAccount(account);
        accountRole.setRole(role);
        return accountRole;
    }

    @Override
    public Test buildTest(Account account) {
        Test test = new Test();
        test.setAccount(account);
        return test;
    }

    @Override
    public Question buildQuestion(Test test) {
        Question question = new Question();
        question.setTest(test);
        return question;
    }

    @Override
    public Answer buildAnswer(Question question) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        return answer;
    }

    @Override
    public TestResult buildTestResult(Account account, Test test, int allQuestions) {
        TestResult testResult = new TestResult();
        testResult.setAccount(account);
        testResult.setTest(test);
        testResult.setTestTitle(test.getTitle());
        testResult.setCorrectCount(0);
        testResult.setAllQuestionsCount(allQuestions);
        testResult.setCreated(new Timestamp(System.currentTimeMillis()));
        return testResult;
    }
}
