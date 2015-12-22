package net.itquiz.services.impl;

import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.services.TutorService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Service("tutorService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TutorServiceImpl extends AbstractTutorService implements TutorService {

    @Override
    public long getAccountTestsCount(int idAccount) {
        return testDao.countTestsCreatedBy(idAccount);
    }

    @Override
    public List<Test> getAccountTests(int idAccount, int offset, int count) {
        return testDao.listTestsCreatedBy(idAccount, offset, count);
    }

    @Override
    public void removeTest(long idTest, int idAccount) throws InvalidUserInputException {
        checkTestCreator(idTest, idAccount);
        super.removeTest(testDao.findById(idTest));
    }

    @Override
    public void checkTestCreator(long idTest, int idAccount) throws InvalidUserInputException {
        Test test = testDao.findById(idTest);
        if (test == null) {
            throw new InvalidUserInputException(messageSource.getMessage("test.not.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (test.getAccount().getIdAccount() != idAccount) {
            throw new InvalidUserInputException(messageSource.getMessage("test.access.denied",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

}
