package net.itquiz.dao.impl;

import net.itquiz.dao.AnswerDao;
import net.itquiz.entities.Answer;
import org.springframework.stereotype.Repository;

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
