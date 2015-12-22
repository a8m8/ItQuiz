package net.itquiz.forms.tutors;

import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.Copyable;
import net.itquiz.forms.IForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Artur Meshcheriakov
 */
public class TestForm implements IForm, Copyable<Test> {

    private String title;
    private String description;
    private String timePerQuestion;
    private Boolean active = Boolean.FALSE;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimePerQuestion() {
        return timePerQuestion;
    }

    public void setTimePerQuestion(String timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public void copyFieldsTo(Test test) {
        test.setTitle(title);
        test.setDescription(description);
        test.setTimePerQuestion(Integer.parseInt(timePerQuestion));
        test.setActive(active);
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
        if (StringUtils.isBlank(title)) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("test.title.required", new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (title.length() > 255) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("volume.exceed", new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (StringUtils.isBlank(timePerQuestion)) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("test.time.required", new Object[]{}, LocaleContextHolder.getLocale()));
        }
        try {
            Integer.parseInt(timePerQuestion);
        } catch (NumberFormatException e) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("test.time.wrong.format", new Object[]{}, LocaleContextHolder.getLocale
                            ()));
        }
    }
}
