package net.itquiz.services.impl;

import net.itquiz.constants.ApplicationConstants;
import net.itquiz.dao.AccountDao;
import net.itquiz.dao.AccountRegistrationDao;
import net.itquiz.entities.Account;
import net.itquiz.entities.AccountRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */

@Service
@EnableScheduling
public class SchedulerService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountRegistrationDao accountRegistrationDao;

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void deleteUnconfirmedAccounts() {
        if (accountDao.countUnconfirmed() > 0) {
            List<Account> accounts = accountDao.listUnconfirmed();
            for (Account account : accounts) {
                if (System.currentTimeMillis() - account.getCreated().getTime() >
                        ApplicationConstants.TWENTY_FOUR_HOURS) {
                    accountDao.delete(account);
                }
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 30 * * * *")
    public void deleteOverduePasswordHash() {
        if (accountRegistrationDao.countWithPasswordHash() > 0) {
            List<AccountRegistration> registrations = accountRegistrationDao.listWithPasswordHash();
            for (AccountRegistration registration : registrations) {
                if (System.currentTimeMillis() - registration.getPassHashCreated().getTime() >
                        ApplicationConstants.TWENTY_FOUR_HOURS) {
                    registration.setPassHash(null);
                    registration.setPassHashCreated(null);
                    accountRegistrationDao.update(registration);
                }
            }
        }
    }

}
