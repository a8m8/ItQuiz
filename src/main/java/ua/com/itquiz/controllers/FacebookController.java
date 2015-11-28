package ua.com.itquiz.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.services.EntranceService;

/**
 *
 * @author Artur Meshcheriakov
 */
@Controller
public class FacebookController implements InitializingBean {

    private final Logger LOGGER = Logger.getLogger(FacebookController.class);

    @Value("${facebook.clientId}")
    private String facebookClientId;

    @Value("${facebook.secretKey}")
    private String facebookSecretKey;

    @Value("${application.host}")
    private String applicationHost;

    @Autowired
    private EntranceService entranceService;

    private String fbReferrer;

    @Override
    public void afterPropertiesSet() throws Exception {

	fbReferrer = new StringBuilder().append("https://graph.facebook.com/oauth/authorize?client_id=")
		.append(facebookClientId).append("&redirect_uri=http://").append(applicationHost)
		.append("/fromfb&scope=user_likes,user_birthday,user_location,user_about_me,public_profile,email")
		.toString();
    }

    @RequestMapping(value = { "/fbLogin", "/fbSignup" }, method = RequestMethod.GET)
    public String fbLogin() throws Exception {
	return "redirect:" + fbReferrer;
    }

    @RequestMapping(value = "/fromfb", method = RequestMethod.GET)
    public String fromfb(HttpServletRequest request, HttpSession session, @RequestParam("code") String code)
	    throws Exception {
	if (code == null) {
	    return "redirect:/login";
	}
	User user = getFacebookUser(code);
	Account account = entranceService.login(user);
	session.setAttribute("CURRENT_ACCOUNT_ID", account.getId());
	return "redirect:/home";
    }

    protected User getFacebookUser (String code) throws IOException {
	String url = new StringBuilder().append("https://graph.facebook.com/oauth/access_token?client_id=")
		.append(facebookClientId).append("&redirect_uri=http://").append(applicationHost)
		.append("/fromfb?referrer=").append(fbReferrer).append("&client_secret=").append(facebookSecretKey)
		.append("&code=").append(code).toString();
	URLConnection connection = new URL(url).openConnection();
	InputStream inputStream = null;
	try {
	    inputStream = connection.getInputStream();
	    Scanner scanner = new Scanner(inputStream);
	    scanner.useDelimiter("\\Z");
	    String out = scanner.next();
	    String[] auth1 = out.split("=");
	    String[] auth2 = auth1[1].split("&");
	    FacebookClient facebookClient = new DefaultFacebookClient(auth2[0]);
	    User user = facebookClient.fetchObject("me", User.class,
		    Parameter.with("fields", "name,email,first_name,last_name"));
	    return user;
	} finally {
	    IOUtils.closeQuietly(inputStream);
	}
    }

}
