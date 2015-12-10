package ua.com.itquiz.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Artur Meshcheriakov
 */
public class MessageHandlerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //TODO Message filter
    }

    @Override
    public void destroy() {

    }
}
