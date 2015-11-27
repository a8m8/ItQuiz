package ua.com.itquiz.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itquiz.dao.AccountDao;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.forms.IForm;
import ua.com.itquiz.services.AdminService;

/**
 *
 * @author Artur Meshcheriakov
 */
@Service("adminService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AdminServiceImpl implements AdminService {

    @Autowired
    AccountDao accountDao;

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
    public void removeAccount(int accountId) {
	// TODO Auto-generated method stub

    }

    @Override
    public void activateAccount(int accountId) {
	// TODO Auto-generated method stub

    }

    @Override
    public void deactivateAccount(int accountId) {
	// TODO Auto-generated method stub

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
