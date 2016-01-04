package net.itquiz.services.impl;

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
@Transactional
public class SchedulerService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountRegistrationDao accountRegistrationDao;

    @Scheduled(cron = "0 0 * * * *")
    public void deleteUnconfirmedAccounts() {
        if (accountDao.countUnconfirmedAccounts() > 0) {
            List<Account> accounts = accountDao.getAllUnconfirmed();
            for (Account account : accounts) {
                if (System.currentTimeMillis() - account.getCreated().getTime() > 86400000) {
                    accountDao.delete(account);
                }
            }
        }
    }

    @Scheduled(cron = "0 30 * * * *")
    public void deleteOverduePasswordHash() {
        if (accountRegistrationDao.countEntityWithPasswordHash() > 0) {
            List<AccountRegistration> registrations = accountRegistrationDao.getAllEntityWithPasswordHash();
            for (AccountRegistration registration : registrations) {
                if (System.currentTimeMillis() - registration.getPassHashCreated().getTime() > 86400000) {
                    registration.setPassHash(null);
                    registration.setPassHashCreated(null);
                    accountRegistrationDao.update(registration);
                }
            }
        }
    }

}
