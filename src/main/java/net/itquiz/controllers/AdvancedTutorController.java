package net.itquiz.controllers;

import net.itquiz.entities.Test;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.tutors.AnswerForm;
import net.itquiz.forms.tutors.QuestionForm;
import net.itquiz.forms.tutors.TestForm;
import net.itquiz.services.AdvancedTutorService;
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
@RequestMapping("/advanced-tutor")
public class AdvancedTutorController extends AbstractTutorController {

    @Autowired
    private AdvancedTutorService advancedTutorService;

    @Value("${advanced-tutor.test.pagination.count}")
    private int testPaginationCount;

    @Value("${advanced-tutor.question.pagination.count}")
    private int questionPaginationCount;

    private String role = "advanced-tutor";
    private String pageName = "alltests";

    @RequestMapping(value = "/alltests/page/{pageNumber}", method = RequestMethod.GET)
    public String showAllTests(@PathVariable int pageNumber, Model model, HttpSession session) {
        super.checkIdTestIn(session);
        Pagination pagination = new Pagination(testPaginationCount, advancedTutorService.getAllTestsCount(), pageNumber);
        List<Test> tests = advancedTutorService.getAllTests(pagination.getOffset(), pagination.getCount());
        return super.showTests(model, tests, "/advanced-tutor/alltests", pagination);
    }

    @RequestMapping(value = "/alltests/delete", method = RequestMethod.GET)
    public String removeTest(@RequestParam("id") long idTest, HttpSession session) {
        try {
            advancedTutorService.removeTest(idTest);
            setMessage(session, "test.delete.successful");
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/advanced-tutor/alltests/page/1";
    }

    @RequestMapping(value = "/new-test", method = RequestMethod.GET)
    public String showCreateTest(Model model) {
        return super.showCreateTest(model, role);
    }

    @RequestMapping(value = "/new-test", method = RequestMethod.POST)
    public String createNewTest(@ModelAttribute("testForm") TestForm testForm, HttpSession session,
                                Model model) {
        return super.createNewTest(testForm, session, model, advancedTutorService, role, pageName);
    }

    @RequestMapping(value = "alltests/edit-test", method = RequestMethod.GET)
    public String showEditTest(@RequestParam("id") long idTest, Model model, HttpSession session) {
        return super.showEditTest(model, session, advancedTutorService, idTest, role, pageName);
    }

    @RequestMapping(value = "alltests/edit-test", method = RequestMethod.POST)
    public String editTest(@ModelAttribute("testForm") TestForm testForm, HttpSession session, Model model) {
        return super.editTest(testForm, session, model, advancedTutorService, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/page/{pageNumber}", method = RequestMethod.GET)
    public String showAddQuestion(@PathVariable int pageNumber, Model model, HttpSession session) {
        return super.showQuestions(model, session, pageNumber, advancedTutorService, role, pageName,
                questionPaginationCount);
    }

    @RequestMapping(value = "alltests/test/questions/add-question", method = RequestMethod.GET)
    public String showAddQuestion(Model model) {
        return super.showAddQuestion(model, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/add-question", method = RequestMethod.POST)
    public String addNewQuestion(@ModelAttribute("questionForm") QuestionForm questionForm, Model model, HttpSession
            session) {
        return super.addNewQuestion(model, session, questionForm, advancedTutorService, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/delete", method = RequestMethod.GET)
    public String deleteQuestion(@RequestParam("id") long idQuestion, HttpSession session) {
        return super.deleteQuestion(idQuestion, session, advancedTutorService, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/edit-question", method = RequestMethod.GET)
    public String showEditQuestion(@RequestParam("id") long idQuestion, HttpSession session,
                                   Model model) {
        return super.showEditQuestion(idQuestion, session, model, advancedTutorService, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/edit-question", method = RequestMethod.POST)
    public String editQuestion(@ModelAttribute("questionForm") QuestionForm questionForm, HttpSession session, Model
            model) {
        return super.editQuestion(questionForm, session, model, advancedTutorService, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/question/add-answer", method = RequestMethod.GET)
    public String showAddAnswer(Model model) {
        return super.showAddAnswer(model, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/question/add-answer", method = RequestMethod.POST)
    public String addNewAnswer(@ModelAttribute("answerForm") AnswerForm answerForm, Model model,
                               HttpSession session) {
        return super.addNewAnswer(model, session, answerForm, advancedTutorService, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/question/answer/delete", method = RequestMethod.GET)
    public String deleteAnswer(@RequestParam("id") long idAnswer, HttpSession session) {
        return super.deleteAnswer(idAnswer, session, advancedTutorService, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/question/edit-answer", method = RequestMethod.GET)
    public String showEditAnswer(@RequestParam("id") long idAnswer, HttpSession session, Model model) {
        return super.showEditAnswer(idAnswer, session, model, advancedTutorService, role, pageName);
    }

    @RequestMapping(value = "alltests/test/questions/question/edit-answer", method = RequestMethod.POST)
    public String EditAnswer(@ModelAttribute("answerForm") AnswerForm answerForm,
                             @RequestParam("id") long idAnswer,
                             HttpSession session, Model model) {
        return super.editAnswer(answerForm, idAnswer, session, model, advancedTutorService, role, pageName);
    }
}
