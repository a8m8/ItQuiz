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

    @Transactional
    public void removeTest(Test test) throws InvalidUserInputException {
        if (test == null) {
            throw new InvalidUserInputException(messageSource.getMessage("test.not.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        testDao.delete(test);
    }

    @Transactional
    @Override
    public void addNewTest(int authorID, TestForm newTestForm) throws InvalidUserInputException {
        if (testDao.findByTitle(newTestForm.getTitle()) != null) {
            throw new InvalidUserInputException(messageSource.getMessage("test.title.exist",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        Test test = entityBuilder.buildTest(accountDao.getProxy(authorID));
        newTestForm.copyFieldsTo(test);
        testDao.save(test);
    }

    @Transactional
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

    @Transactional
    @Override
    public long countTestQuestions(long idTest) {
        return questionDao.countAllIn(idTest);
    }

    @Transactional
    @Override
    public List<Question> listTestQuestions(long idTest, int offset, int count) {
        return questionDao.listRelatedTo(idTest, offset, count);
    }

    @Transactional
    @Override
    public void addNewQuestion(long idTest, QuestionForm questionForm) {
        Test test = testDao.getProxy(idTest);
        Question question = entityBuilder.buildQuestion(test);
        question.setContent(questionForm.getContent());
        question.setActive(questionForm.getActive());
        test.setUpdated(new Timestamp(System.currentTimeMillis()));
        testDao.save(test);
        questionDao.save(question);
    }

    @Transactional
    @Override
    public void deleteQuestion(long idQuestion, long idTest) throws InvalidUserInputException {
        Question question = getQuestion(idQuestion, idTest);
        questionDao.delete(question);
        Test test = testDao.findById(idTest);
        test.setUpdated(new Timestamp(System.currentTimeMillis()));
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    @Override
    public QuestionForm generateFormBasedOnQuestion(Question question) {
        QuestionForm questionForm = new QuestionForm();
        questionForm.setContent(question.getContent());
        questionForm.setActive(question.getActive());
        return questionForm;
    }

    @Transactional
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

    @Transactional
    @Override
    public void addNewAnswer(long idQuestion, AnswerForm answerForm) {
        Question question = questionDao.getProxy(idQuestion);
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

    @Transactional
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

    @Transactional
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

    @Transactional
    @Override
    public AnswerForm generateFormBasedOnAnswer(Answer answer) {
        AnswerForm answerForm = new AnswerForm();
        answerForm.setContent(answer.getContent());
        answerForm.setCorrect(answer.getCorrect());
        answerForm.setActive(answer.getActive());
        return answerForm;
    }

    @Transactional
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
