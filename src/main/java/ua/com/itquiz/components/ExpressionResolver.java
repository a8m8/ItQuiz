package ua.com.itquiz.components;

import java.util.Map;

/**
 * @author Artur Meshcheriakov
 */
public interface ExpressionResolver {

    String resolveExpression(String expression, Map<String, Object> values);

}
