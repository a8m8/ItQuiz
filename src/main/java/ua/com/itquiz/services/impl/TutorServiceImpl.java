package ua.com.itquiz.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itquiz.components.EntityBuilder;
import ua.com.itquiz.dao.AccountDao;
import ua.com.itquiz.dao.TestDao;
import ua.com.itquiz.entities.Test;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.tutors.NewTestForm;
import ua.com.itquiz.services.TutorService;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Service("tutorService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TutorServiceImpl implements TutorService {

    @Autowired
    private TestDao testDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntityBuilder entityBuilder;

    @Override
    public long getAccountTestsCount(int idAccount) {
        return testDao.countTestsCreatedBy(idAccount);
    }

    @Override
    public List<Test> getAccountTests(int idAccount, int offset, int count) {
        return testDao.listTestsCreatedBy(idAccount, offset, count);
    }

    @Override
    public void addNewTest(int authorID, NewTestForm newTestForm) {
        Test test = entityBuilder.buildTest(accountDao.findById(authorID));
        newTestForm.copyFieldsTo(test);
        testDao.save(test);
    }

    @Override
    public void removeTest(long idTest, int idAccount) throws InvalidUserInputException {
        Test test = testDao.findById(idTest);
        if (test == null) {
            throw new InvalidUserInputException(messageSource.getMessage("test.not.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (test.getAccount().getIdAccount() != idAccount) {
            throw new InvalidUserInputException(messageSource.getMessage("test.access.denied",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        testDao.delete(test);
    }

}
