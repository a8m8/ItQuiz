package ua.com.itquiz.services;

import ua.com.itquiz.entities.Account;

/**
 *
 * @author Artur Meshcheriakov
 */
public interface EmailService {

    void sendVerificationEmail(Account account);

    void sendPasswordForRecovery(Account account);

}
