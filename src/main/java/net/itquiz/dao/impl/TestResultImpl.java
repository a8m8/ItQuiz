package net.itquiz.dao.impl;

import net.itquiz.dao.TestResultDao;
import net.itquiz.entities.Account;
import net.itquiz.entities.TestResult;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
