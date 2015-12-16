package ua.com.itquiz.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itquiz.dao.TestDao;
import ua.com.itquiz.entities.Test;
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
    protected TestDao testDao;

    @Override
    public long getAccountTestsCount(int idAccount) {
        return testDao.countTestsCreatedBy(idAccount);
    }

    @Override
    public List<Test> getAccountTests(int idAccount, int offset, int count) {
        return testDao.listTestsCreatedBy(idAccount, offset, count);
    }

}
