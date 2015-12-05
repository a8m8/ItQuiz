package ua.com.itquiz.dao;

import ua.com.itquiz.entities.Answer;
import ua.com.itquiz.entities.Question;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface AnswerDao extends IEntityDao<Answer> {

    List<Answer> getAllAnswerOfQuestion(Question question);

}
