package net.itquiz.services.impl;

import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.tutors.TestForm;
import net.itquiz.services.AdvancedTutorService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Service("advancedTutorService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AdvancedTutorServiceImpl extends AbstractTutorService implements AdvancedTutorService {

    @Override
    public void removeTest(long idTest) throws InvalidUserInputException {
        Test test = testDao.findById(idTest);
        super.removeTest(test);
    }

    @Override
    public long getAllTestsCount() {
        return testDao.countAllTests();
    }

    @Override
    public List<Test> getAllTests(int offset, int count) {
        return testDao.list(offset, count);
    }

    @Override
    public TestForm generateFormBasedOnTest(long idTest) throws InvalidUserInputException {
        return super.generateFormBasedOnTest(idTest);
    }

}
