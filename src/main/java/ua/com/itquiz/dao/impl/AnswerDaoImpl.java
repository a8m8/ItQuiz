package ua.com.itquiz.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.itquiz.dao.AnswerDao;
import ua.com.itquiz.entities.Answer;

/**
 * @author Artur Meshcheriakov
 */
@Repository("answerDao")
public class AnswerDaoImpl extends AbstractEntityDao<Answer> implements AnswerDao {

    @Override
    protected Class<Answer> getEntityClass() {
        return Answer.class;
    }

}
