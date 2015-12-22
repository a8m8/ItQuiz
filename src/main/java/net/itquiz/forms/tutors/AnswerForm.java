package net.itquiz.forms.tutors;

import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.IForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Artur Meshcheriakov
 */
public class AnswerForm implements IForm {

    private String content;
    private Boolean correct = Boolean.FALSE;
    private Boolean active = Boolean.TRUE;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
        if (StringUtils.isBlank(content)) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("answer.required", new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }
}
