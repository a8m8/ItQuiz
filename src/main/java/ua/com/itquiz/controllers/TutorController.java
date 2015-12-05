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

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "tutor/home";
    }

}
