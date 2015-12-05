package ua.com.itquiz.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.com.itquiz.dao.TestResultDao;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.TestResult;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Repository("testResultImpl")
public class TestResultImpl extends AbstractEntityDao<TestResult> implements TestResultDao {

    @Override
    protected Class<TestResult> getEntityClass() {
        return TestResult.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TestResult> getUserTestResult(Account account) {
        return getSession().createCriteria(getEntityClass())
                .add(Restrictions.eq("account", account)).list();
    }

}
