package net.itquiz.controllers;

import net.itquiz.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/test-results", method = RequestMethod.GET)
    public String showTestsResult() {
        return "student/test-results";
    }
}
