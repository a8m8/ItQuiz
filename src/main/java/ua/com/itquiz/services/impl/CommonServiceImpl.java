package ua.com.itquiz.services.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itquiz.dao.AccountDao;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AccountInfoForm;
import ua.com.itquiz.forms.PasswordForm;
import ua.com.itquiz.services.CommonService;

import java.sql.Timestamp;

/**
 * @author Artur Meshcheriakov
 */
@Service("commonService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CommonServiceImpl implements CommonService {

    @Autowired
    protected AccountDao accountDao;

    @Autowired
    protected MessageSource messageSource;

    @Override
    public Account getAccountById(int idAccount) {
        return accountDao.findById(idAccount);
    }

    @Override
    public void changePassword(int idAccount, PasswordForm passwordForm) {
        Account account = accountDao.findById(idAccount);
        account.setPassword(passwordForm.getPassword());
        account.setUpdated(new Timestamp(System.currentTimeMillis()));
        accountDao.save(account);
    }

    @Override
    public void editPersonalData(int idAccount, AccountInfoForm editDataForm) throws InvalidUserInputException {
        Account account = accountDao.findById(idAccount);
        boolean isNewValue = false;
        if (!StringUtils.equals(account.getLogin(), editDataForm.getLogin())) {
            if (accountDao.findByLogin(editDataForm.getLogin()) == null) {
                account.setLogin(editDataForm.getLogin());
                isNewValue = true;
            } else {
                throw new InvalidUserInputException(
                        messageSource.getMessage("login.busy", new Object[]{}, LocaleContextHolder.getLocale()));
            }
        }
        if (!StringUtils.equals(account.getFio(), editDataForm.getFio())) {
            account.setFio(editDataForm.getFio());
            isNewValue = true;
        }
        if (isNewValue) {
            account.setUpdated(new Timestamp(System.currentTimeMillis()));
            accountDao.save(account);
        } else {
            throw new InvalidUserInputException(messageSource.getMessage("nothing.save",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }
}
