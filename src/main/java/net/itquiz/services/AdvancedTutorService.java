package net.itquiz.services;

import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface AdvancedTutorService extends CommonTutorService {

    void removeTest(long idTest) throws InvalidUserInputException;

    long getAllTestsCount();

    List<Test> getAllTests(int offset, int count);
}
