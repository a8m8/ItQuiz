package ua.com.itquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.itquiz.services.StudentService;

/**
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/student")
public class StudentController extends AbstractController {

    @Autowired
    protected StudentService studentService;

    @RequestMapping(value = "/tests", method = RequestMethod.GET)
    public String showTests() {
        return "student/tests";
    }

    @RequestMapping(value = "/tests-result", method = RequestMethod.GET)
    public String showTestsResult() {
        return "student/tests-result";
    }
}
