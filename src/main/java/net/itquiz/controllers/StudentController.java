package net.itquiz.controllers;

import net.itquiz.entities.Question;
import net.itquiz.entities.Test;
import net.itquiz.entities.TestResult;
import net.itquiz.security.SecurityUtils;
import net.itquiz.services.StudentService;
import net.itquiz.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/student")
public class StudentController extends AbstractController {

    @Value("${student.tests.pagination.count}")
    private int testsPaginationCount;

    @Value("${student.test.results.pagination.count}")
    private int testResultsPaginationCount;

    @Autowired
    protected StudentService studentService;

    @RequestMapping(value = "/tests/page/{pageNumber}", method = RequestMethod.GET)
    public String showTests(@PathVariable int pageNumber, Model model, HttpSession session) {
        deleteValuesFromSession(session);
        Pagination pagination = new Pagination(testsPaginationCount, studentService.availableTestsCount(), pageNumber);
        List<Test> tests = studentService.getAvailableTests(pagination.getOffset(), pagination.getCount());
        model.addAttribute("tests", tests);
        model.addAttribute("location", "/student/tests");
        model.addAttribute("pagination", pagination);
        return "student/tests";
    }

    private void deleteValuesFromSession(HttpSession session) {
        if (session.getAttribute("idTest") != null) {
            session.removeAttribute("idTest");
        }
        if (session.getAttribute("testResult") != null) {
            session.removeAttribute("testResult");
        }
        if (session.getAttribute("question") != null) {
            session.removeAttribute("question");
        }
        if (session.getAttribute("questionNumber") != null) {
            session.removeAttribute("questionNumber");
        }
        if (session.getAttribute("maxQuestions") != null) {
            session.removeAttribute("maxQuestions");
        }
    }

    @RequestMapping(value = "/pass-test", method = RequestMethod.GET)
    public String prepareToTestPassing(@RequestParam("id") long idTest, HttpSession session) {
        TestResult testResult = studentService.prepareTestResult(SecurityUtils.getCurrentIdAccount(), idTest);
        session.setAttribute("testResult", testResult);
        session.setAttribute("idTest", idTest);
        session.setAttribute("questionNumber", 0);
        session.setAttribute("maxQuestions", (testResult.getAllQuestionsCount()));
        return "redirect:/student/passing-test";
    }

    @RequestMapping(value = "/passing-test", method = RequestMethod.GET)
    public String showQuestions(Model model, HttpSession session) {
        if ((int) session.getAttribute("questionNumber") == (int) session.getAttribute("maxQuestions")) {
            return "redirect:/student/test-result";
        }
        Question question = studentService.getQuestion((long) session.getAttribute("idTest"),
                (int) session.getAttribute("questionNumber"));
        session.setAttribute("question", question);
        model.addAttribute("question", question);
        return "student/passing-test";
    }

    @RequestMapping(value = "/passing-test", method = RequestMethod.POST)
    public String nextQuestion(@RequestParam(value = "answer", required = false) List<String> answerIds, HttpSession
            session) {
        session.setAttribute("questionNumber", ((int) session.getAttribute("questionNumber") + 1));
        TestResult testResult = studentService.checkAnswers(answerIds, (Question) session.getAttribute("question"),
                (TestResult) session.getAttribute("testResult"));
        session.setAttribute("testResult", testResult);
        return "redirect:/student/passing-test";
    }

    @RequestMapping(value = "/test-result", method = RequestMethod.GET)
    public String showTestResult(Model model, HttpSession session) {
        TestResult testResult = (TestResult) session.getAttribute("testResult");
        studentService.saveTestResult(testResult);
        model.addAttribute("testResult", testResult);
        deleteValuesFromSession(session);
        return "student/result";
    }

    @RequestMapping(value = "/test-results/page/{pageNumber}", method = RequestMethod.GET)
    public String showTestsResult(@PathVariable int pageNumber, Model model) {
        int idAccount = SecurityUtils.getCurrentIdAccount();
        Pagination pagination = new Pagination(testResultsPaginationCount,
                studentService.userTestResultsCount(idAccount), pageNumber);
        List<TestResult> testResults = studentService.getTestResults(idAccount, pagination.getOffset()
                , pagination.getCount());
        model.addAttribute("testResults", testResults);
        model.addAttribute("location", "/student/test-results");
        model.addAttribute("pagination", pagination);
        return "student/test-results";
    }
}
