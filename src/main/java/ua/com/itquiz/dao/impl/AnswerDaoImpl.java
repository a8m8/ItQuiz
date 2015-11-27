package ua.com.itquiz.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ua.com.itquiz.dao.AnswerDao;
import ua.com.itquiz.entities.Answer;
import ua.com.itquiz.entities.Question;

/**
 * 
 * @author Artur Meshcheriakov
 */
@Repository("answerDao")
public class AnswerDaoImpl extends AbstractEntityDao<Answer> implements AnswerDao {

    @Override
    protected Class<Answer> getEntityClass() {
	return Answer.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Answer> getAllAnswerOfQuestion(Question question) {
	return getSession().createCriteria(getEntityClass())
	    .add(Restrictions.eq("question", question)).list();
    }

}
