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
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TutorServiceImpl extends AbstractTutorService implements TutorService {

    @Transactional
    @Override
    public long countAccountTests(int idAccount) {
        return testDao.countCreatedBy(idAccount);
    }

    @Transactional
    @Override
    public List<Test> listAccountTests(int idAccount, int offset, int count) {
        return testDao.listCreatedBy(idAccount, offset, count);
    }

    @Transactional
    @Override
    public void removeTest(long idTest, int idAccount) throws InvalidUserInputException {
        checkTestCreator(idTest, idAccount);
        super.removeTest(testDao.findById(idTest));
    }

    @Transactional
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
