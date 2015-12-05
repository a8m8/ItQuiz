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

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "advanced-tutor/home";
    }

}
