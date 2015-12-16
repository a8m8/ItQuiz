package ua.com.itquiz.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpSession;

/**
 * @author Artur Meshcheriakov
 */
public abstract class AbstractController {

    @Autowired
    MessageSource messageSource;

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected void setMessage(HttpSession session, String codeKey) {
        session.setAttribute("message", messageSource.getMessage(codeKey, new Object[]{}, LocaleContextHolder
                .getLocale()));
    }

}
