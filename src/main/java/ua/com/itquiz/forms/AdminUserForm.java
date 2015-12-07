package ua.com.itquiz.forms;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ua.com.itquiz.exceptions.InvalidUserInputException;

/**
 * @author Artur Meshcheriakov
 */
public class AdminUserForm extends SignUpForm {

    private static final long serialVersionUID = -3393569577509014603L;

    protected Boolean student = Boolean.FALSE;

    protected Boolean administrator = Boolean.FALSE;

    protected Boolean tutor = Boolean.FALSE;

    protected Boolean advancedTutor = Boolean.FALSE;

    public Boolean getStudent() {
        return student;
    }

    public void setStudent(Boolean student) {
        this.student = student;
    }

    public Boolean getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public Boolean getTutor() {
        return tutor;
    }

    public void setTutor(Boolean tutor) {
        this.tutor = tutor;
    }

    public Boolean getAdvancedTutor() {
        return advancedTutor;
    }

    public void setAdvancedTutor(Boolean advancedTutor) {
        this.advancedTutor = advancedTutor;
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
        super.validate(messageSource);
        if (!administrator && !advancedTutor && !tutor && !student) {
            throw new InvalidUserInputException(messageSource.getMessage("roles.withoutroles",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

}
