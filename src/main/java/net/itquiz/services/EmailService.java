package net.itquiz.services;

import net.itquiz.entities.Account;

/**
 * @author Artur Meshcheriakov
 */
public interface EmailService {

    void sendVerificationEmail(Account account);

    void sendPasswordToEmail(Account account);

}
