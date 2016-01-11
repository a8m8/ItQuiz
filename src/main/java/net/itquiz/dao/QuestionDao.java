package net.itquiz.dao;

import net.itquiz.entities.Question;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface QuestionDao extends IEntityDao<Question> {

    long countAllIn(long idTest);

    List<Question> listRelatedTo(long idTest, int offset, int count);

    long countActiveQuestions(long idTest);

    List<Question> listActiveRelatedTo(long idTest, int offset, int count);
}
