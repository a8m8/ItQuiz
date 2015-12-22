package net.itquiz.security;

import net.itquiz.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Artur Meshcheriakov
 */
@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    @Autowired
    @Qualifier("accountAuthentificationService")
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    @Autowired
    @Qualifier("pwdEncoder")
    public void setPasswordEncoder(Object passwordEncoder) {
        super.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        CurrentAccount account = (CurrentAccount) userDetails;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        short role = Short.parseShort(request.getParameter("idRole"));
        if (!ApplicationConstants.ROLES.contains(role)) {
            throw new AuthenticationException("Invalid role") {

                private static final long serialVersionUID = -2778071393179081488L;

            };
        }
        account.setRole(role);
    }

}
