package ua.com.itquiz.services;

import ua.com.itquiz.entities.Account;

/**
 * @author Artur Meshcheriakov
 */
public interface EmailTemplateService {

    String getEmailText(Account account, String templatePath);

}
