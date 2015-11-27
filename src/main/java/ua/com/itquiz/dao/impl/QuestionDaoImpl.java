package ua.com.itquiz.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ua.com.itquiz.dao.QuestionDao;
import ua.com.itquiz.entities.Question;
import ua.com.itquiz.entities.Test;

/**
 * 
 * @author Artur Meshcheriakov
 */
@Repository("questionDao")
public class QuestionDaoImpl extends AbstractEntityDao<Question> implements QuestionDao {

    @Override
    protected Class<Question> getEntityClass() {
	return Question.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Question> getAllQuestionOfTest(Test test) {
	return getSession().createCriteria(getEntityClass()).add(Restrictions.eq("test", test))
	    .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Question> getAllQuestionOfTest(Test test, int offset, int count) {
	return getSession().createCriteria(getEntityClass()).add(Restrictions.eq("test", test))
	    .setFirstResult(offset).setMaxResults(count).list();
    }

}
