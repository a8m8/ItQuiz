package net.itquiz.services.impl;

import net.itquiz.components.EntityBuilder;
import net.itquiz.dao.AccountDao;
import net.itquiz.dao.AnswerDao;
import net.itquiz.dao.QuestionDao;
import net.itquiz.dao.TestDao;
import net.itquiz.entities.Answer;
import net.itquiz.entities.Question;
import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.tutors.AnswerForm;
import net.itquiz.forms.tutors.QuestionForm;
import net.itquiz.forms.tutors.TestForm;
import net.itquiz.services.CommonTutorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Transactional
public abstract class AbstractTutorService implements CommonTutorService {

    @Autowired
    protected TestDao testDao;

    @Autowired
    protected QuestionDao questionDao;

    @Autowired
    protected AnswerDao answerDao;

    @Autowired
    protected AccountDao accountDao;

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected EntityBuilder entityBuilder;

    public void removeTest(Test test) throws InvalidUserInputException {
        if (test == null) {
            throw new InvalidUserInputException(messageSource.getMessage("test.not.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        testDao.delete(test);
    }

    @Override
    public void addNewTest(int authorID, TestForm newTestForm) {
        Test test = entityBuilder.buildTest(accountDao.findById(authorID));
        newTestForm.copyFieldsTo(test);
        testDao.save(test);
    }

    @Override
    public TestForm generateFormBasedOnTest(long idTest) throws InvalidUserInputException {
        Test test = testDao.findById(idTest);
        if (test == null) {
            throw new InvalidUserInputException(messageSource.getMessage("test.not.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        TestForm form = new TestForm();
        form.setTitle(test.getTitle());
        form.setDescription(test.getDescription());
        form.setTimePerQuestion(test.getTimePerQuestion().toString());
        form.setActive(test.getActive());
        return form;
    }

    @Override
    public long getTestQuestionsCount(long idTest) {
        return questionDao.getTestQuestionsCount(idTest);
    }

    @Override
    public List<Question> getTestQuestionsList(long idTest, int offset, int count) {
        return questionDao.getQuestionListOfTest(idTest, offset, count);
    }

    @Override
    public void addNewQuestion(long idTest, QuestionForm questionForm) {
        Test test = testDao.findById(idTest);
        Question question = entityBuilder.buildQuestion(test);
        question.setContent(questionForm.getContent());
        question.setActive(questionForm.getActive());
        test.setUpdated(new Timestamp(System.currentTimeMillis()));
        testDao.save(test);
        questionDao.save(question);
    }

    @Override
    public void deleteQuestion(long idQuestion, long idTest) throws InvalidUserInputException {
        Question question = getQuestion(idQuestion, idTest);
        questionDao.delete(question);
        Test test = testDao.findById(idTest);
        test.setUpdated(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void editTest(long idTest, TestForm testForm) throws InvalidUserInputException {
        Test test = testDao.findById(idTest);
        boolean isNewValue = false;
        if (!StringUtils.equals(test.getTitle(), testForm.getTitle())) {
            isNewValue = true;
            test.setTitle(testForm.getTitle());
        }
        if (!StringUtils.equals(test.getDescription(), testForm.getDescription())) {
            isNewValue = true;
            test.setDescription(testForm.getDescription());
        }
        if (!test.getTimePerQuestion().equals(Integer.parseInt(testForm.getTimePerQuestion()))) {
            isNewValue = true;
            test.setTimePerQuestion(Integer.parseInt(testForm.getTimePerQuestion()));
        }
        if (!test.getActive().equals(testForm.getActive())) {
            isNewValue = true;
            test.setActive(testForm.getActive());
        }

        if (isNewValue) {
            test.setUpdated(new Timestamp(System.currentTimeMillis()));
            testDao.save(test);
        } else {
            throw new InvalidUserInputException(messageSource.getMessage("nothing.save",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Question getQuestion(long idQuestion, long idTest) throws InvalidUserInputException {
        Question question = questionDao.findById(idQuestion);
        if (question == null) {
            throw new InvalidUserInputException(messageSource.getMessage("question.not.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (question.getTest().getIdTest() != idTest) {
            throw new InvalidUserInputException(messageSource.getMessage("question.access.denied",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        return question;
    }

    @Override
    public QuestionForm generateFormBasedOnQuestion(Question question) {
        QuestionForm questionForm = new QuestionForm();
        questionForm.setContent(question.getContent());
        questionForm.setActive(question.getActive());
        return questionForm;
    }

    @Override
    public void editQuestion(long idQuestion, QuestionForm questionForm) throws InvalidUserInputException {
        Question question = questionDao.findById(idQuestion);
        boolean isNewValue = false;
        if (!StringUtils.equals(question.getContent(), questionForm.getContent())) {
            isNewValue = true;
            question.setContent(questionForm.getContent());
        }
        if (!question.getActive().equals(questionForm.getActive())) {
            isNewValue = true;
            question.setActive(questionForm.getActive());
        }

        if (isNewValue) {
            question.setUpdated(new Timestamp(System.currentTimeMillis()));
            Test test = testDao.findById(question.getTest().getIdTest());
            test.setUpdated(new Timestamp(System.currentTimeMillis()));
            questionDao.save(question);
            testDao.save(test);
        } else {
            throw new InvalidUserInputException(messageSource.getMessage("nothing.save",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void addNewAnswer(long idQuestion, AnswerForm answerForm) {
        Question question = questionDao.findById(idQuestion);
        Answer answer = entityBuilder.buildAnswer(question);
        answer.setContent(answerForm.getContent());
        answer.setCorrect(answerForm.getCorrect());
        answer.setActive(answerForm.getActive());

        answerDao.save(answer);
        question.setUpdated(new Timestamp(System.currentTimeMillis()));
        questionDao.save(question);

        Test test = testDao.findById(question.getTest().getIdTest());
        test.setUpdated(new Timestamp(System.currentTimeMillis()));
        testDao.save(test);
    }

    @Override
    public void deleteAnswer(long idAnswer, long idQuestion) throws InvalidUserInputException {
        Answer answer = getAnswer(idAnswer, idQuestion);
        answerDao.delete(answer);
        Question question = questionDao.findById(idQuestion);
        question.setUpdated(new Timestamp(System.currentTimeMillis()));
        questionDao.save(question);
        Test test = testDao.findById(question.getTest().getIdTest());
        test.setUpdated(new Timestamp(System.currentTimeMillis()));
        testDao.save(test);
    }

    @Override
    public Answer getAnswer(long idAnswer, long idQuestion) throws InvalidUserInputException {
        Answer answer = answerDao.findById(idAnswer);
        if (answer == null) {
            throw new InvalidUserInputException(messageSource.getMessage("answer.not.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (answer.getQuestion().getId() != idQuestion) {
            throw new InvalidUserInputException(messageSource.getMessage("answer.access.denied",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        return answer;
    }

    @Override
    public AnswerForm generateFormBasedOnAnswer(Answer answer) {
        AnswerForm answerForm = new AnswerForm();
        answerForm.setContent(answer.getContent());
        answerForm.setCorrect(answer.getCorrect());
        answerForm.setActive(answer.getActive());
        return answerForm;
    }

    @Override
    public void editAnswer(long idAnswer, AnswerForm answerForm) throws InvalidUserInputException {
        Answer answer = answerDao.findById(idAnswer);
        boolean isNewValue = false;
        if (!StringUtils.equals(answer.getContent(), answerForm.getContent())) {
            isNewValue = true;
            answer.setContent(answerForm.getContent());
        }
        if (!answer.getCorrect().equals(answerForm.getCorrect())) {
            isNewValue = true;
            answer.setCorrect(answerForm.getCorrect());
        }
        if (!answer.getActive().equals(answerForm.getActive())) {
            isNewValue = true;
            answer.setActive(answerForm.getActive());
        }

        if (isNewValue) {
            answer.setUpdated(new Timestamp(System.currentTimeMillis()));
            Question question = questionDao.findById(answer.getQuestion().getIdQuestion());
            question.setUpdated(new Timestamp(System.currentTimeMillis()));
            Test test = testDao.findById(question.getTest().getIdTest());
            test.setUpdated(new Timestamp(System.currentTimeMillis()));
            answerDao.save(answer);
            questionDao.save(question);
            testDao.save(test);
        } else {
            throw new InvalidUserInputException(messageSource.getMessage("nothing.save",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }
}
