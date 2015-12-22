package net.itquiz.services;

import net.itquiz.entities.Answer;
import net.itquiz.entities.Question;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.tutors.AnswerForm;
import net.itquiz.forms.tutors.QuestionForm;
import net.itquiz.forms.tutors.TestForm;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface CommonTutorService {

    void addNewTest(int authorID, TestForm newTestForm);

    TestForm generateFormBasedOnTest(long idTest) throws InvalidUserInputException;

    long getTestQuestionsCount(long idTest);

    List<Question> getTestQuestionsList(long idTest, int offset, int count);

    void addNewQuestion(long idTest, QuestionForm questionForm);

    void deleteQuestion(long idQuestion, long idTest) throws InvalidUserInputException;

    void editTest(long idTest, TestForm testForm) throws InvalidUserInputException;

    Question getQuestion(long idQuestion, long idTest) throws InvalidUserInputException;

    QuestionForm generateFormBasedOnQuestion(Question question);

    void editQuestion(long idQuestion, QuestionForm questionForm) throws InvalidUserInputException;

    void addNewAnswer(long idQuestion, AnswerForm answerForm);

    void deleteAnswer(long idAnswer, long idQuestion) throws InvalidUserInputException;

    Answer getAnswer(long idAnswer, long idQuestion) throws InvalidUserInputException;

    AnswerForm generateFormBasedOnAnswer(Answer answer);

    void editAnswer(long idAnswer, AnswerForm answerForm) throws InvalidUserInputException;
}
