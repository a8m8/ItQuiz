package ua.com.itquiz.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.IForm;
import ua.com.itquiz.services.AdminService;

/**
 *
 * @author Artur Meshcheriakov
 */
@Service("adminService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AdminServiceImpl extends CommonServiceImpl implements AdminService {

    @Autowired
    private MessageSource messageSource;
    // TODO Implementation of AdminService here

    @Override
    public int accountCount() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void addUser(IForm form) {
	// TODO Auto-generated method stub

    }

    @Override
    public void editAccount(IForm form) {
	// TODO Auto-generated method stub

    }

    @Override
    public void removeAccount(int accountId) throws InvalidUserInputException {
	Account account = accountDao.findById(accountId);
	if (account == null) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("login.badcredentials", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	accountDao.delete(account);
    }

    @Override
    public List<Account> getAllAccounts() {
	List<Account> result = accountDao.findAll();
	for (Account account : result) {
	    account.getAccountRoles();
	}
	return result;
    }

}
