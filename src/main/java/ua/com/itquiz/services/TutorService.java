package ua.com.itquiz.services;

import ua.com.itquiz.entities.Test;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.tutors.NewTestForm;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TutorService {

    long getAccountTestsCount(int IdAccount);

    List<Test> getAccountTests(int currentIdAccount, int offset, int count);

    void removeTest(long idTest, int idAccount) throws InvalidUserInputException;

    void addNewTest(int authorID, NewTestForm newTestForm);

}
