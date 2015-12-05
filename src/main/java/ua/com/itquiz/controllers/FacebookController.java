package ua.com.itquiz.controllers;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.scope.ExtendedPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.security.SecurityUtils;
import ua.com.itquiz.services.EntranceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Artur Meshcheriakov
 */
@Controller
public class FacebookController extends AbstractController implements InitializingBean {

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
        fbReferrer = client.getLoginDialogUrl(facebookClientId, applicationHost + "/fromfb",
                scopeBuilder);
    }

    @RequestMapping(value = {"/fbLogin", "/fbSignup"}, method = RequestMethod.GET)
    public String fbLogin() throws Exception {
        return "redirect:" + fbReferrer;
    }

    @RequestMapping(value = "/fromfb", method = RequestMethod.GET)
    public String fromfb(HttpServletRequest request, HttpSession session,
                         @RequestParam(value = "code", required = false) String code) throws Exception {
        if (code == null) {
            return "redirect:/login";
        }
        User user = getFacebookUser(code);
        Account account = entranceService.login(user);
        SecurityUtils.authentificate(account, 4);
        return "redirect:/crossing";
    }

    protected User getFacebookUser(String code) throws IOException {
        FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_5);
        AccessToken accessToken = client.obtainUserAccessToken(facebookClientId, facebookSecretKey,
                applicationHost + "/fromfb", code);
        client = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_5);
        User user = client.fetchObject("me", User.class,
                Parameter.with("fields", "name,email,first_name,last_name"));

        return user;
    }

}
