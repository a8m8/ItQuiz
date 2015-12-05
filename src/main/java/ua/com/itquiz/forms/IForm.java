package ua.com.itquiz.forms;

import org.springframework.context.MessageSource;
import ua.com.itquiz.exceptions.InvalidUserInputException;

import java.io.Serializable;

/**
 * @author Artur Meshcheriakov
 */
public interface IForm extends Serializable {

    void validate(MessageSource messageSource) throws InvalidUserInputException;

}
