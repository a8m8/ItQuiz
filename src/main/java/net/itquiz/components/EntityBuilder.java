package net.itquiz.components;

import net.itquiz.entities.*;

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
}
