package net.itquiz.dao;

import net.itquiz.entities.Question;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface QuestionDao extends IEntityDao<Question> {

    long getTestQuestionsCount(long idTest);

    List<Question> getQuestionListOfTest(long idTest, int offset, int count);

    long getActiveQuestionCount(long idTest);

    List<Question> getActiveQuestionListOfTest(long idTest, int question, int count);
}
