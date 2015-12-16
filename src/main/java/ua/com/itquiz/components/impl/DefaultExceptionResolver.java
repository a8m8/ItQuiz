package ua.com.itquiz.components.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import ua.com.itquiz.exceptions.InvalidUserInputException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Artur Meshcheriakov
 */
@Component
public class DefaultExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionResolver.class);

    public DefaultExceptionResolver() {
        setOrder(0);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {

        try {
            Throwable causeException = ex.getCause() == null ? ex : ex.getCause();
            if (handler != null && !(causeException instanceof InvalidUserInputException)) {
                LoggerFactory.getLogger(handler.getClass()).error("", ex);
            }
            LOGGER.warn("Unsupported exception: " + ex.getClass(), ex);
            return new ModelAndView("redirect:/error?url=" + request.getRequestURI());
        } catch (Exception ex1) {
            LOGGER.error("Can't send error", ex1);
            return new ModelAndView();
        }
    }

    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        // do nothing
    }

    @Override
    protected boolean shouldApplyTo(HttpServletRequest arg0, Object arg1) {
        return true;
    }

}
