package net.itquiz.services.impl;

import net.itquiz.entities.Account;
import net.itquiz.services.EmailTemplateService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

/**
 * @author Artur Meshcheriakov
 */
@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Value("${application.host}")
    private String applicationHome;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public String getEmailText(Account account, String templatePath) {

        Template template = velocityEngine.getTemplate(templatePath);

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("user", account);
        velocityContext.put("home", applicationHome);

        StringWriter stringWriter = new StringWriter();

        template.merge(velocityContext, stringWriter);

        return stringWriter.toString();
    }

}
