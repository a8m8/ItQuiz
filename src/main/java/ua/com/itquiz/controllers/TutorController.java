package ua.com.itquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/tutor")
public class TutorController extends AbstractController {

    @RequestMapping(value = "/mytests", method = RequestMethod.GET)
    public String home() {
        return "tutor/mytests";
    }

    @RequestMapping(value = "/create-test", method = RequestMethod.GET)
    public String showCreateTest() {
        return "tutor/create-test";
    }

}
