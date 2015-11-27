package ua.com.itquiz.forms;

import java.io.Serializable;

import org.springframework.context.MessageSource;

import ua.com.itquiz.exceptions.InvalidUserInputException;

/**
 * 
 * @author Artur Meshcheriakov
 */
public interface IForm extends Serializable {

    void validate(MessageSource messageSource) throws InvalidUserInputException;

}
