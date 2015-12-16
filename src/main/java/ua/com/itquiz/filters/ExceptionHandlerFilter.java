package ua.com.itquiz.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.itquiz.exceptions.InvalidUserInputException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Artur Meshcheriakov
 */
public class ExceptionHandlerFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception ex) {
            Throwable causeException = ex.getCause() == null ? ex : ex.getCause();
            if (!(causeException instanceof InvalidUserInputException)) {
                LOGGER.error(ex.getClass().toString(), ex);
            } else {
                LOGGER.warn("Unsupported exception: " + ex.getClass(), ex);
            }
            ((HttpServletResponse) response).sendRedirect("/error");
        }
    }

    @Override
    public void destroy() {

    }
}
