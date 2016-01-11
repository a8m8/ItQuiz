package net.itquiz.services;

import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TutorService extends CommonTutorService {

    void removeTest(long idTest, int idAccount) throws InvalidUserInputException;

    long countAccountTests(int idAccount);

    List<Test> listAccountTests(int currentIdAccount, int offset, int count);

    void checkTestCreator(long idTest, int currentIdAccount) throws InvalidUserInputException;
}
