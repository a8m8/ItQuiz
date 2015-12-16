package ua.com.itquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.itquiz.entities.Test;
import ua.com.itquiz.security.SecurityUtils;
import ua.com.itquiz.services.TutorService;
import ua.com.itquiz.utils.Pagination;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/tutor")
public class TutorController extends AbstractController {

    @Autowired
    protected TutorService tutorService;

    @RequestMapping(value = "/mytests/page/{pageNumber}", method = RequestMethod.GET)
    public String showMyTests(@PathVariable int pageNumber, Model model) {
        Pagination pagination = new Pagination(1, tutorService.getAccountTestsCount(SecurityUtils.getCurrentIdAccount
                ()), pageNumber);
        List<Test> accountTests = tutorService.getAccountTests(SecurityUtils.getCurrentIdAccount(), pagination.getOffset()
                , pagination.getCount());
        model.addAttribute("tests", accountTests);
        model.addAttribute("location", "/tutor/mytests");
        model.addAttribute("pagination", pagination);
        return "tutor/mytests";
    }

    @RequestMapping(value = "/create-test", method = RequestMethod.GET)
    public String showCreateTest() {
        return "tutor/create-test";
    }

}
