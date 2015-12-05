package ua.com.itquiz.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itquiz.constants.ApplicationConstants;
import ua.com.itquiz.dao.AccountDao;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.AccountRole;

/**
 *
 * @author Artur Meshcheriakov
 */
@Service("accountAuthentificationService")
public class AuthentificationService implements UserDetailsService {

    private static final Map<Integer, String> ROLES = new HashMap<>();

    static {
	ROLES.put(ApplicationConstants.ADMIN_ROLE, "ROLE_ADMIN");
	ROLES.put(ApplicationConstants.ADVANCED_TUTOR_ROLE, "ROLE_ADVANCED_TUTOR");
	ROLES.put(ApplicationConstants.TUTOR_ROLE, "ROLE_TUTOR");
	ROLES.put(ApplicationConstants.STUDENT_ROLE, "ROLE_STUDENT");
    }

    static Collection<? extends GrantedAuthority> convert(List<AccountRole> roles) {
	Collection<SimpleGrantedAuthority> result = new ArrayList<SimpleGrantedAuthority>();
	for (AccountRole accountRole : roles) {
	    result.add(new SimpleGrantedAuthority(
		ROLES.get(accountRole.getRole().getIdRole().intValue())));
	}
	return result;
    }

    @Autowired
    private AccountDao accountDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Account account = accountDao.findByEmail(username);
	if (account == null) {
	    throw new UsernameNotFoundException("Current email is not registered");
	}
	return new CurrentAccount(account);
    }

}
