package net.itquiz.services;

import net.itquiz.entities.Account;

/**
 * @author Artur Meshcheriakov
 */
public interface EmailTemplateService {

    String getEmailText(Account account, String templatePath);

}
