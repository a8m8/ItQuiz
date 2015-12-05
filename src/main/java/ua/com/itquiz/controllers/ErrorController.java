package ua.com.itquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Artur Meshcheriakov
 */
@Controller
public class ErrorController extends AbstractController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String showError() {
        return "redirect:error";
    }

}
