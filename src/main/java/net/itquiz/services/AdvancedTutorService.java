package net.itquiz.services;

import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface AdvancedTutorService extends CommonTutorService {

    void removeTest(long idTest) throws InvalidUserInputException;

    long countAllTests();

    List<Test> listTests(int offset, int count);
}
