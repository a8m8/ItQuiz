package net.itquiz.components;

import net.itquiz.entities.Account;
import net.itquiz.entities.AccountRegistration;
import net.itquiz.entities.AccountRole;
import net.itquiz.entities.Answer;
import net.itquiz.entities.Question;
import net.itquiz.entities.Role;
import net.itquiz.entities.Test;
import net.itquiz.entities.TestResult;

/**
 * @author Artur Meshcheriakov
 */
public interface EntityBuilder {

    Account buildAccount();

    AccountRole buildAccountRole(Account account, Role role);

    AccountRegistration buildAccountRegistration(Account account);

    Test buildTest(Account account);

    Question buildQuestion(Test test);

    Answer buildAnswer(Question question);

    TestResult buildTestResult(Account account, Test test, int allQuestions);
}
