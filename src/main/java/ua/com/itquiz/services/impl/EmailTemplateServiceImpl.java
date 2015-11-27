package ua.com.itquiz.services.impl;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.services.EmailTemplateService;

/**
 *
 * @author Artur Meshcheriakov
 */
@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public String getEmailText(Account account, String templatePath) {

	Template template = velocityEngine.getTemplate(templatePath);

	VelocityContext velocityContext = new VelocityContext();
	velocityContext.put("user", account);

	StringWriter stringWriter = new StringWriter();

	template.merge(velocityContext, stringWriter);

	return stringWriter.toString();
    }

}
