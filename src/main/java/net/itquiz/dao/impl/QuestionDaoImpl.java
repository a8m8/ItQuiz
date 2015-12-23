package net.itquiz.dao.impl;

import net.itquiz.dao.QuestionDao;
import net.itquiz.entities.Question;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Repository("questionDao")
public class QuestionDaoImpl extends AbstractEntityDao<Question> implements QuestionDao {

    @Override
    protected Class<Question> getEntityClass() {
        return Question.class;
    }

    @Override
    public long getTestQuestionsCount(long idTest) {
        return (long) getSession().createCriteria(getEntityClass()).add(Restrictions.eq("test.idTest",
                idTest)).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List<Question> getQuestionListOfTest(long idTest, int offset, int count) {
        return getSession().createCriteria(getEntityClass()).add(Restrictions.eq("test.idTest", idTest)).
                addOrder(Order.asc("created")).setFirstResult(offset).setMaxResults(count).list();
    }

    @Override
    public long getActiveQuestionCount(long idTest) {
        return (long) getSession().createCriteria(getEntityClass()).add(Restrictions.eq("test.idTest",
                idTest)).add(Restrictions.eq("active", true)).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List<Question> getActiveQuestionListOfTest(long idTest, int offset, int count) {
        return getSession().createCriteria(getEntityClass()).add(Restrictions.eq("test.idTest", idTest)).
                add(Restrictions.eq("active", true)).addOrder(Order.asc("created")).setFirstResult(offset)
                .setMaxResults(count).list();
    }
}
