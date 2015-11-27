package ua.com.itquiz.dao;

import java.util.List;

import ua.com.itquiz.entities.Question;
import ua.com.itquiz.entities.Test;

/**
 * 
 * @author Artur Meshcheriakov
 */
public interface QuestionDao extends IEntityDao<Question> {

    List<Question> getAllQuestionOfTest(Test test);

    List<Question> getAllQuestionOfTest(Test test, int offset, int count);

}
