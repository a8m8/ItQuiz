package ua.com.itquiz.services;

import ua.com.itquiz.entities.Test;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TutorService {

    long getAccountTestsCount(int IdAccount);

    List<Test> getAccountTests(int currentIdAccount, int offset, int count);

}
