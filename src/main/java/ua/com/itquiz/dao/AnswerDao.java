package ua.com.itquiz.dao;

import java.util.List;

import ua.com.itquiz.entities.Answer;
import ua.com.itquiz.entities.Question;

/**
 * 
 * @author Artur Meshcheriakov
 */
public interface AnswerDao extends IEntityDao<Answer> {

    List<Answer> getAllAnswerOfQuestion(Question question);

}
