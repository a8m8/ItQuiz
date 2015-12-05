package ua.com.itquiz.dao;

import ua.com.itquiz.entities.Question;
import ua.com.itquiz.entities.Test;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface QuestionDao extends IEntityDao<Question> {

    List<Question> getAllQuestionOfTest(Test test);

    List<Question> getAllQuestionOfTest(Test test, int offset, int count);

}
