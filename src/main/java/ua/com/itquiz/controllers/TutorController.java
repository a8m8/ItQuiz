package ua.com.itquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.itquiz.entities.Test;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.tutors.NewTestForm;
import ua.com.itquiz.security.SecurityUtils;
import ua.com.itquiz.services.TutorService;
import ua.com.itquiz.utils.Pagination;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/tutor")
public class TutorController extends AbstractController {

    @Autowired
    protected TutorService tutorService;

    @Value("${tutor.pagination.count}")
    private int paginationCount;

    @RequestMapping(value = "/mytests/page/{pageNumber}", method = RequestMethod.GET)
    public String showMyTests(@PathVariable int pageNumber, Model model) {
        Pagination pagination = new Pagination(paginationCount, tutorService.getAccountTestsCount(SecurityUtils.getCurrentIdAccount
                ()), pageNumber);
        List<Test> accountTests = tutorService.getAccountTests(SecurityUtils.getCurrentIdAccount(), pagination.getOffset()
                , pagination.getCount());
        model.addAttribute("tests", accountTests);
        model.addAttribute("location", "/tutor/mytests");
        model.addAttribute("pagination", pagination);
        return "tutor/mytests";
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



    @RequestMapping(value = "/create-test", method = RequestMethod.GET)
    public String showCreateTest(Model model) {
        model.addAttribute("newTestForm", new NewTestForm());
        model.addAttribute("role", "tutor");
        return "tutor/create-test";
    }

    @RequestMapping(value = "/create-test", method = RequestMethod.POST)
    public String createNewTest(@ModelAttribute("newTestForm") NewTestForm newTestForm, HttpSession session,
                                Model model) {
        try {
            newTestForm.validate(messageSource);
            tutorService.addNewTest(SecurityUtils.getCurrentIdAccount(), newTestForm);
            setMessage(session, "test.create.successful");
            return "redirect:/tutor/mytests/page/1";
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("role", "tutor");
            return "tutor/create-test";
        }

    }

}
