package net.itquiz.services.impl;

import com.restfb.types.User;
import net.itquiz.components.EntityBuilder;
import net.itquiz.dao.AccountDao;
import net.itquiz.dao.AccountRegistrationDao;
import net.itquiz.dao.AccountRoleDao;
import net.itquiz.dao.RoleDao;
import net.itquiz.entities.Account;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.services.EmailService;
import net.itquiz.services.EntranceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Artur Meshcheriakov
 */
@RunWith(MockitoJUnitRunner.class)
public class TestEntranceServiceImpl {

    @InjectMocks
    private EntranceService entranceService = new EntranceServiceImpl();

    @Mock
    private AccountDao accountDao;

    @Mock
    private AccountRoleDao accountRoleDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private AccountRegistrationDao accountRegistrationDao;

    @Mock
    private EmailService emailService;

    @Mock
    private EntityBuilder entityBuilder;

    @Mock
    private MessageSource messageSource;

    @Mock
    private PasswordEncoder passwordEncoder;

    private String testEmail = "test@it-quiz.net";
    private String testPassword = "password";
    private String testUserName = "User";

    @Test
    public void loginExistingAccountTest() throws InvalidUserInputException {
        //Arrange
        User user = getFacebookUser();
        Account account = mock(Account.class);
        when(accountDao.findByEmail(testEmail)).thenReturn(account);

        //Act
        Account result = entranceService.login(user);

        //Assert
        verify(user).getEmail();
        verify(accountDao).findByEmail(testEmail);
        assertEquals(account, result);

    }

    @Test
    public void loginNewUserTest() throws InvalidUserInputException {
        //Arrange
        User user = getFacebookUser();
        when(accountDao.findByEmail(testEmail)).thenReturn(null);
        when(accountDao.findByLogin(anyString())).thenReturn(null);
        Account account = spy(new Account());
        when(entityBuilder.buildAccount()).thenReturn(account);
        when(passwordEncoder.encode(anyString())).thenReturn(testPassword);

        //Act
        Account result = entranceService.login(user);

        //Assert
        verify(user, times(3)).getEmail();
        assertEquals(testEmail, result.getEmail());
        assertNotNull(result.getLogin());
        assertEquals(testUserName, result.getFio());
        assertEquals(testPassword, account.getPassword());
        assertTrue(result.getActive());
        assertTrue(result.getConfirmed());


    }

    private User getFacebookUser() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn(testEmail);
        return user;
    }
}
