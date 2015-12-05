package ua.com.itquiz.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.services.EmailService;
import ua.com.itquiz.services.EmailTemplateService;

import javax.mail.internet.MimeMessage;

/**
 * @author Artur Meshcheriakov
 */
@Service("emailService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EmailServiceImpl implements EmailService {

    private String verificationText = "registration-confirmation.vm";
    private String recoveryText = "password-recovery.vm";

    @Autowired
    private JavaMailSender defaultMailSender;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Override
    public void sendVerificationEmail(Account account) {
        String content = emailTemplateService.getEmailText(account, verificationText);
        sendMail(account, content, "Account verification");
    }

    @Override
    public void sendPasswordToEmail(Account account) {
        String content = emailTemplateService.getEmailText(account, recoveryText);
        sendMail(account, content, "Your password");
    }

    private void sendMail(final Account account, final String content, final String subject) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(account.getEmail());
                message.setFrom("artua@mail.ru");
                message.setSubject(subject);
                message.setText(content, true);
            }
        };
        defaultMailSender.send(preparator);
    }
}
