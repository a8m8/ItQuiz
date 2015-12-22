package net.itquiz.controllers;

import net.itquiz.entities.Answer;
import net.itquiz.entities.Question;
import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.tutors.AnswerForm;
import net.itquiz.forms.tutors.QuestionForm;
import net.itquiz.forms.tutors.TestForm;
import net.itquiz.security.SecurityUtils;
import net.itquiz.services.CommonTutorService;
import net.itquiz.services.TutorService;
import net.itquiz.utils.Pagination;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public abstract class AbstractTutorController extends AbstractController {

    public String showTests(Model model, List<Test> accountTests, String location, Pagination pagination) {
        model.addAttribute("tests", accountTests);
        model.addAttribute("location", location);
        model.addAttribute("pagination", pagination);
        return location.replaceFirst("/", "");
    }

    public String showCreateTest(Model model, String role) {
        model.addAttribute("testForm", new TestForm());
        model.addAttribute("role", role);
        return role + "/add-test";
    }

    public String createNewTest(TestForm testForm, HttpSession session, Model model,
                                CommonTutorService commonTutorService, String role, String pageName) {
        try {
            testForm.validate(messageSource);
            commonTutorService.addNewTest(SecurityUtils.getCurrentIdAccount(), testForm);
            setMessage(session, "test.create.successful");
            return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName)
                    .append("/page/1").toString();
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("role", role);
            return new StringBuilder().append(role).append("/add-test").toString();
        }
    }


    public String showEditTest(Model model, HttpSession session, CommonTutorService commonTutorService,
                               long idTest, String role, String pageName) {
        try {
            if (commonTutorService instanceof TutorService) {
                ((TutorService) commonTutorService).checkTestCreator(idTest, SecurityUtils.getCurrentIdAccount());
            }
            TestForm form = commonTutorService.generateFormBasedOnTest(idTest);
            session.setAttribute("idTest", idTest);
            model.addAttribute("testForm", form);
            model.addAttribute("role", role);
            model.addAttribute("pageName", pageName);
            return role + "/edit-test";
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
            return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName).append
                    ("/page/1").toString();
        }

    }

    public String editTest(TestForm testForm, HttpSession session, Model model, CommonTutorService commonTutorService,
                           String role, String pageName) {
        try {
            testForm.validate(messageSource);
            commonTutorService.editTest((long) session.getAttribute("idTest"), testForm);
            setMessage(session, "changes.saved");
            return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName).append
                    ("/page/1").toString();
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("role", role);
            model.addAttribute("pageName", pageName);
            return role + "/edit-test";
        }
    }

    public String showQuestions(Model model, HttpSession session, int pageNumber, CommonTutorService commonTutorService,
                                String role, String pageName, int paginationCount) {

        long idTest = (long) session.getAttribute("idTest");
        Pagination pagination = new Pagination(paginationCount, commonTutorService.getTestQuestionsCount(idTest),
                pageNumber);
        List<Question> questions = commonTutorService.getTestQuestionsList(idTest, pagination.getOffset(),
                pagination.getCount());
        model.addAttribute("questions", questions);
        model.addAttribute("role", role);
        model.addAttribute("pageName", pageName);
        model.addAttribute("pagination", pagination);
        String location = new StringBuilder().append("/").append(role).append("/").append(pageName).append
                ("/test/questions").toString();
        model.addAttribute("location", location);
        return role + "/questions";
    }

    public String showAddQuestion(Model model, String role, String pageName) {
        model.addAttribute("questionForm", new QuestionForm());
        model.addAttribute("role", role);
        model.addAttribute("pageName", pageName);
        return role + "/add-question";
    }

    public String addNewQuestion(Model model, HttpSession session, QuestionForm questionForm,
                                 CommonTutorService commonTutorService, String role, String pageName) {
        try {
            questionForm.validate(messageSource);
            commonTutorService.addNewQuestion((long) session.getAttribute("idTest"), questionForm);
            setMessage(session, "question.created");
            return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName).append
                    ("/test/questions/page/1").toString();
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("role", role);
            model.addAttribute("pageName", pageName);
            return role + "/add-question";
        }
    }

    public String deleteQuestion(long idQuestion, HttpSession session, CommonTutorService commonTutorService,
                                 String role, String pageName) {
        try {
            commonTutorService.deleteQuestion(idQuestion, (long) session.getAttribute("idTest"));
            setMessage(session, "question.deleted");
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName)
                .append("/test/questions/page/1").toString();
    }

    public String showEditQuestion(long idQuestion, HttpSession session, Model model, CommonTutorService
            commonTutorService, String role, String pageName) {
        try {
            Question question = commonTutorService.getQuestion(idQuestion, (long) session.getAttribute("idTest"));
            QuestionForm questionForm = commonTutorService.generateFormBasedOnQuestion(question);
            model.addAttribute("questionForm", questionForm);
            model.addAttribute("role", role);
            model.addAttribute("pageName", pageName);
            model.addAttribute("question", question);
            session.setAttribute("idQuestion", idQuestion);
            return role + "/edit-question";
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
            return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName)
                    .append("/test/questions/page/1").toString();
        }
    }

    public String editQuestion(QuestionForm questionForm, HttpSession session, Model model,
                               CommonTutorService commonTutorService, String role, String pageName) {
        try {
            questionForm.validate(messageSource);
            commonTutorService.editQuestion((long) session.getAttribute("idQuestion"), questionForm);
            setMessage(session, "changes.saved");
            return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName).append
                    ("/test/questions/page/1").toString();
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("role", role);
            model.addAttribute("pageName", pageName);
            return role + "/edit-question";
        }
    }

    public String showAddAnswer(Model model, String role, String pageName) {
        model.addAttribute("answerForm", new AnswerForm());
        model.addAttribute("role", role);
        model.addAttribute("pageName", pageName);
        return role + "/add-answer";
    }

    public String addNewAnswer(Model model, HttpSession session, AnswerForm answerForm,
                               CommonTutorService commonTutorService, String role, String pageName) {
        try {
            answerForm.validate(messageSource);
            commonTutorService.addNewAnswer((long) session.getAttribute("idQuestion"), answerForm);
            setMessage(session, "answer.created");
            return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName).append
                    ("/test/questions/edit-question?id=").append(session.getAttribute("idQuestion")).toString();
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("role", role);
            model.addAttribute("pageName", pageName);
            return role + "/add-answer";
        }
    }

    public String deleteAnswer(long idAnswer, HttpSession session, CommonTutorService commonTutorService,
                               String role, String pageName) {
        try {
            commonTutorService.deleteAnswer(idAnswer, (long) session.getAttribute("idQuestion"));
            setMessage(session, "answer.deleted");
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName).append
                ("/test/questions/edit-question?id=").append(session.getAttribute("idQuestion")).toString();
    }

    public String showEditAnswer(long idAnswer, HttpSession session, Model model,
                                 CommonTutorService commonTutorService, String role, String pageName) {
        try {
            Answer answer = commonTutorService.getAnswer(idAnswer, (long) session.getAttribute("idQuestion"));
            AnswerForm answerForm = commonTutorService.generateFormBasedOnAnswer(answer);
            model.addAttribute("answerForm", answerForm);
            model.addAttribute("role", role);
            model.addAttribute("pageName", pageName);
            model.addAttribute("idAnswer", idAnswer);
            return role + "/edit-answer";
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
            return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName).append
                    ("/test/questions/edit-question?id=").append(session.getAttribute("idQuestion")).toString();
        }
    }

    public String editAnswer(AnswerForm answerForm, long idAnswer, HttpSession session, Model model,
                             CommonTutorService commonTutorService, String role, String pageName) {
        try {
            answerForm.validate(messageSource);
            commonTutorService.editAnswer(idAnswer, answerForm);
            setMessage(session, "changes.saved");
            return new StringBuilder().append("redirect:/").append(role).append("/").append(pageName).append
                    ("/test/questions/edit-question?id=").append(session.getAttribute("idQuestion")).toString();
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("role", role);
            model.addAttribute("pageName", pageName);
            model.addAttribute("idAnswer", idAnswer);
            return role + "/edit-answer";
        }
    }

    public void checkIdTestIn(HttpSession session) {
        if (session.getAttribute("idTest") != null) {
            session.removeAttribute("idTest");
        }
    }

    public void checkIdQuestionIn(HttpSession session) {
        if (session.getAttribute("idQuestion") != null) {
            session.removeAttribute("idQuestion");
        }
    }
}
