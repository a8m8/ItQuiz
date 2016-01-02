package net.itquiz.services.impl;

import net.itquiz.dao.AccountDao;
import net.itquiz.entities.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountDao accountDao;

    @Scheduled(cron = "0 0 * * * *")
    public void testScheduler() {
        if (accountDao.countUnconfirmedAccounts() > 0) {
            List<Account> accounts = accountDao.getAllUnconfirmed();
            for (Account account : accounts) {
                if (System.currentTimeMillis() - account.getCreated().getTime() > 86400000) {
                    LOGGER.error("I will delete account with email " + account.getEmail() + " created " +
                            account.getCreated());
                    accountDao.delete(account);
                }
            }
        }
    }

}
