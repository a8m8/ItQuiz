package ua.com.itquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/advanced-tutor")
public class AdvancedTutorController extends AbstractController {

    @RequestMapping(value = "/mytests", method = RequestMethod.GET)
    public String home() {
        return "advanced-tutor/mytests";
    }

    @RequestMapping(value = "/create-test", method = RequestMethod.GET)
    public String showCreateTest() {
        return "advanced-tutor/create-test";
    }

    @RequestMapping(value = "/alltests", method = RequestMethod.GET)
    public String showAllTests() {
        return "advanced-tutor/alltests";
    }

}
