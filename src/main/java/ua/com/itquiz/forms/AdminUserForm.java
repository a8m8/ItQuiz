package ua.com.itquiz.forms;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;

/**
 *
 * @author Artur Meshcheriakov
 */
public class AdminUserForm extends SingUpForm {

    private Boolean active = Boolean.FALSE;

    private Boolean student = Boolean.FALSE;

    private Boolean administrator = Boolean.FALSE;

    private Boolean tutor = Boolean.FALSE;

    private Boolean advancedTutor = Boolean.FALSE;

    public Boolean getActive() {
	return active;
    }

    public void setActive(Boolean active) {
	this.active = active;
    }

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
	    throw new InvalidUserInputException(
		    messageSource.getMessage("roles.withoutroles", new Object[] {}, LocaleContextHolder.getLocale()));
	}
    }

    @Override
    public void copyFieldsTo(Account account) {
	super.copyFieldsTo(account);
	account.setActive(active);
    }
}
