package ua.com.itquiz.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.scope.ExtendedPermissions;
import com.restfb.scope.ScopeBuilder;
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

	ScopeBuilder scopeBuilder = new ScopeBuilder();
	scopeBuilder.addPermission(ExtendedPermissions.EMAIL);
	FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_5);
	fbReferrer = client.getLoginDialogUrl(facebookClientId, applicationHost + "/fromfb", scopeBuilder);

	new StringBuilder().append("https://graph.facebook.com/oauth/authorize?client_id=")
		.append(facebookClientId).append("&redirect_uri=http://").append(applicationHost)
		.append("/fromfb&scope=user_about_me,email")
		.toString();
    }

    @RequestMapping(value = { "/fbLogin", "/fbSignup" }, method = RequestMethod.GET)
    public String fbLogin() throws Exception {
	return "redirect:" + fbReferrer;
    }

    @RequestMapping(value = "/fromfb", method = RequestMethod.GET)
    public String fromfb(HttpServletRequest request, HttpSession session,
	    @RequestParam(value = "code", required = false) String code)
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
	FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_5);
	AccessToken accessToken = client.obtainUserAccessToken(facebookClientId, facebookSecretKey,
		applicationHost + "/fromfb", code);
	client = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_5);
	User user = client.fetchObject("me", User.class, Parameter.with("fields", "name,email,first_name,last_name"));

	return user;
    }

}
