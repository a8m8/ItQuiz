package net.itquiz.smtp;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


/**
 * @author Artur Meshcheriakov
 */
public class SMTPAuthenticator extends Authenticator {

    private String username;
    private String password;

    public SMTPAuthenticator(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
