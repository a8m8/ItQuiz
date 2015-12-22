package net.itquiz.forms;

import net.itquiz.exceptions.InvalidUserInputException;
import org.springframework.context.MessageSource;

import java.io.Serializable;

/**
 * @author Artur Meshcheriakov
 */
public interface IForm extends Serializable {

    void validate(MessageSource messageSource) throws InvalidUserInputException;

}
