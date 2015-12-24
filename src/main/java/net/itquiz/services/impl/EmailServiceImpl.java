package net.itquiz.services.impl;

import net.itquiz.entities.Account;
import net.itquiz.services.EmailService;
import net.itquiz.services.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author Artur Meshcheriakov
 */
@Service("emailService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EmailServiceImpl implements EmailService {

    @Value("${verification.email.filepath}")
    private String verificationEmailText;

    @Value("${recovery.email.filepath}")
    private String recoveryTextFileName;

    @Value("${email.address}")
    private String emailFrom;

    @Autowired
    private JavaMailSender defaultMailSender;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    MessageSource messageSource;

    @Override
    public void sendVerificationEmail(Account account) {
        String content = emailTemplateService.getEmailText(account, verificationEmailText);
        sendMail(account, content, messageSource.getMessage("verification.email.title",
                new Object[]{}, LocaleContextHolder.getLocale()));
    }

    @Override
    public void sendPasswordToEmail(Account account) {
        String content = emailTemplateService.getEmailText(account, recoveryTextFileName);
        sendMail(account, content, messageSource.getMessage("recovery.email.title",
                new Object[]{}, LocaleContextHolder.getLocale()));
    }

    private void sendMail(final Account account, final String content, final String subject) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(account.getEmail());
                message.setFrom(emailFrom);
                message.setSubject(subject);
                message.setText(content, true);
            }
        };
        defaultMailSender.send(preparator);
    }
}
