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
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AdvancedTutorServiceImpl extends AbstractTutorService implements AdvancedTutorService {

    @Transactional
    @Override
    public void removeTest(long idTest) throws InvalidUserInputException {
        Test test = testDao.findById(idTest);
        super.removeTest(test);
    }

    @Transactional
    @Override
    public long countAllTests() {
        return testDao.countAll();
    }

    @Transactional
    @Override
    public List<Test> listTests(int offset, int count) {
        return testDao.list(offset, count);
    }

    @Transactional
    @Override
    public TestForm generateFormBasedOnTest(long idTest) throws InvalidUserInputException {
        return super.generateFormBasedOnTest(idTest);
    }

}
