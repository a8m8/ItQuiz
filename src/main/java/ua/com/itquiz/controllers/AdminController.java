package ua.com.itquiz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.services.AdminService;

/**
 *
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    @Autowired
    protected AdminService adminService;

    @RequestMapping(value="/home", method=RequestMethod.GET)
    public String home(Model model) {
	List<Account> accounts = adminService.getAllAccounts();
	model.addAttribute("accounts", accounts);
	return "admin/home";
    }

}
