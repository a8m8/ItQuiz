package net.itquiz.services.impl;

import net.itquiz.dao.AccountDao;
import net.itquiz.entities.Account;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.AccountInfoForm;
import net.itquiz.forms.PasswordForm;
import net.itquiz.services.CommonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account getAccountById(int idAccount) {
        return accountDao.findById(idAccount);
    }

    @Override
    public AccountInfoForm generateAccountForm(int idAccount) {
        Account account = accountDao.findById(idAccount);
        AccountInfoForm accountInfoForm = new AccountInfoForm();
        accountInfoForm.setEmail(account.getEmail());
        accountInfoForm.setLogin(account.getLogin());
        accountInfoForm.setFio(account.getFio());
        return accountInfoForm;
    }

    @Override
    public void changePassword(int idAccount, PasswordForm passwordForm, boolean oldPasswordChecking) throws
            InvalidUserInputException {
        if (oldPasswordChecking && StringUtils.isBlank(passwordForm.getOldPassword())) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("old.password.required", new Object[]{}, LocaleContextHolder.getLocale()));
        }
        Account account = accountDao.findById(idAccount);
        if (oldPasswordChecking && !StringUtils.equals(passwordEncoder.encode(passwordForm.getOldPassword()),
                account.getPassword())) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("old.password.not.match", new Object[]{}, LocaleContextHolder.getLocale()));
        }
        String encodedPassword = passwordEncoder.encode(passwordForm.getPassword());
        account.setPassword(encodedPassword);
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
