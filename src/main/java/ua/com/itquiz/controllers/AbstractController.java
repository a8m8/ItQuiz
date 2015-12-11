package ua.com.itquiz.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Artur Meshcheriakov
 */
public abstract class AbstractController {

    @Autowired
    MessageSource messageSource;

    protected final Logger LOGGER = Logger.getLogger(getClass());

    protected void setMessage(HttpSession session, String codeKey) {
        session.setAttribute("message", messageSource.getMessage(codeKey, new Object[]{}, LocaleContextHolder
                .getLocale()));
    }

    protected Map<String, Integer> getPaginationMap(int count, long entityScope, int currentPage) {
        Map<String, Integer> paginationMap = new HashMap<>();
        paginationMap.put("count", count);
        paginationMap.put("currentIndex", currentPage);
        int temp = (int) entityScope / count;
        int maximum = (entityScope % count == 0) ? temp : temp + 1;
        paginationMap.put("maximum", maximum);
        int offset = (currentPage - 1) * count;
        paginationMap.put("offset", offset);
        int beginIndex = Math.max(1, currentPage - 5);
        paginationMap.put("beginIndex", beginIndex);
        int endIndex = Math.min(beginIndex + 10, maximum);
        paginationMap.put("endIndex", endIndex);
        return paginationMap;
    }

}
