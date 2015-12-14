package ua.com.itquiz.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.itquiz.dao.QuestionDao;
import ua.com.itquiz.entities.Question;

/**
 * @author Artur Meshcheriakov
 */
@Repository("questionDao")
public class QuestionDaoImpl extends AbstractEntityDao<Question> implements QuestionDao {

    @Override
    protected Class<Question> getEntityClass() {
        return Question.class;
    }

}
