package net.itquiz.controllers;

import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.tutors.AnswerForm;
import net.itquiz.forms.tutors.QuestionForm;
import net.itquiz.forms.tutors.TestForm;
import net.itquiz.security.SecurityUtils;
import net.itquiz.services.TutorService;
import net.itquiz.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/tutor")
public class TutorController extends AbstractTutorController {

    @Autowired
    private TutorService tutorService;

    @Value("${tutor.test.pagination.count}")
    private int testPaginationCount;

    @Value("${tutor.question.pagination.count}")
    private int questionPaginationCount;

    private String role = "tutor";
    private String pageName = "mytests";

    @RequestMapping(value = "/mytests/page/{pageNumber}", method = RequestMethod.GET)
    public String showMyTests(@PathVariable int pageNumber, Model model, HttpSession session) {
        super.checkIdTestIn(session);
        Pagination pagination = new Pagination(testPaginationCount, tutorService.getAccountTestsCount(SecurityUtils.getCurrentIdAccount
                ()), pageNumber);
        List<Test> accountTests = tutorService.getAccountTests(SecurityUtils.getCurrentIdAccount(), pagination.getOffset()
                , pagination.getCount());
        return super.showTests(model, accountTests, "/tutor/mytests", pagination);
    }

    @RequestMapping(value = "/mytests/delete", method = RequestMethod.GET)
    public String removeTest(@RequestParam("id") long idTest, HttpSession session) {
        try {
            tutorService.removeTest(idTest, SecurityUtils.getCurrentIdAccount());
            setMessage(session, "test.delete.successful");
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/tutor/mytests/page/1";
    }

    @RequestMapping(value = "/new-test", method = RequestMethod.GET)
    public String showCreateTest(Model model) {
        return super.showCreateTest(model, role);
    }

    @RequestMapping(value = "/new-test", method = RequestMethod.POST)
    public String createNewTest(@ModelAttribute("testForm") TestForm testForm, HttpSession session,
                                Model model) {
        return super.createNewTest(testForm, session, model, tutorService, role, pageName);
    }

    @RequestMapping(value = "mytests/edit-test", method = RequestMethod.GET)
    public String showEditTest(@RequestParam("id") long idTest, Model model, HttpSession session) {
        return super.showEditTest(model, session, tutorService, idTest, role, pageName);
    }

    @RequestMapping(value = "mytests/edit-test", method = RequestMethod.POST)
    public String editTest(@ModelAttribute("testForm") TestForm testForm, HttpSession session, Model model) {
        return super.editTest(testForm, session, model, tutorService, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/page/{pageNumber}", method = RequestMethod.GET)
    public String showQuestions(@PathVariable int pageNumber, Model model, HttpSession session) {
        super.checkIdQuestionIn(session);
        return super.showQuestions(model, session, pageNumber, tutorService, role, pageName,
                questionPaginationCount);
    }

    @RequestMapping(value = "mytests/test/questions/add-question", method = RequestMethod.GET)
    public String showAddQuestion(Model model) {
        return super.showAddQuestion(model, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/add-question", method = RequestMethod.POST)
    public String addNewQuestion(@ModelAttribute("questionForm") QuestionForm questionForm, Model model, HttpSession
            session) {
        return super.addNewQuestion(model, session, questionForm, tutorService, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/delete", method = RequestMethod.GET)
    public String deleteQuestion(@RequestParam("id") long idQuestion, HttpSession session) {
        return super.deleteQuestion(idQuestion, session, tutorService, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/edit-question", method = RequestMethod.GET)
    public String showEditQuestion(@RequestParam("id") long idQuestion, HttpSession session,
                                   Model model) {
        return super.showEditQuestion(idQuestion, session, model, tutorService, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/edit-question", method = RequestMethod.POST)
    public String editQuestion(@ModelAttribute("questionForm") QuestionForm questionForm, HttpSession session, Model
            model) {
        return super.editQuestion(questionForm, session, model, tutorService, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/question/add-answer", method = RequestMethod.GET)
    public String showAddAnswer(Model model) {
        return super.showAddAnswer(model, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/question/add-answer", method = RequestMethod.POST)
    public String addNewAnswer(@ModelAttribute("answerForm") AnswerForm answerForm, Model model,
                               HttpSession session) {
        return super.addNewAnswer(model, session, answerForm, tutorService, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/question/answer/delete", method = RequestMethod.GET)
    public String deleteAnswer(@RequestParam("id") long idAnswer, HttpSession session) {
        return super.deleteAnswer(idAnswer, session, tutorService, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/question/edit-answer", method = RequestMethod.GET)
    public String showEditAnswer(@RequestParam("id") long idAnswer, HttpSession session, Model model) {
        return super.showEditAnswer(idAnswer, session, model, tutorService, role, pageName);
    }

    @RequestMapping(value = "mytests/test/questions/question/edit-answer", method = RequestMethod.POST)
    public String EditAnswer(@ModelAttribute("answerForm") AnswerForm answerForm,
                             @RequestParam("id") long idAnswer,
                             HttpSession session, Model model) {
        return super.editAnswer(answerForm, idAnswer, session, model, tutorService, role, pageName);
    }
}
