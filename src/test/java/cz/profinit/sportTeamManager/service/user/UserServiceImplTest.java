/*
 * UserServiceImplTest
 *
 * 0.1
 *
 * Author: J. Janský
 */

package cz.profinit.sportTeamManager.service.user;

import cz.profinit.sportTeamManager.configuration.ApplicationConfigurationTest;
import cz.profinit.sportTeamManager.mappers.UserMapper;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.UserRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.StubUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Unit tests for User service implementation
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@ActiveProfiles("stub")
public class UserServiceImplTest {
    private UserServiceImpl userService;
    private RegisteredUser user;
    private RegisteredUser user2;

    @Autowired
    private ApplicationContext context;

    /**
     * Before a test create new UserServiceImpl using stub repositories and create two stub users.
     */
    @Before
    public void setUp() {
        UserRepository userRepository = new StubUserRepository();
        UserMapper userMapper = new UserMapper();
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        userService = new UserServiceImpl(passwordEncoder,userRepository);
        user = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        user2 = new RegisteredUser("Tomas", "Smutny", "pass", "ab@gmail.com", RoleEnum.USER);
    }

    /**
     * Tests a successful registration of the new user.
     */
    @Test
    public void newUserRegistration() {
        RegisteredUser newUser = userService.newUserRegistration(user2);
        assertEquals(user2.getName(),newUser.getName());
        assertEquals(user2.getSurname(),newUser.getSurname());
        assertEquals(user2.getEmail(),newUser.getEmail());
        assertEquals(user2.getRole(),newUser.getRole());
        assertNotEquals(user2.getPassword(),newUser.getPassword());
    }

    /**
     * Tests unsuccessful registration of already registered user.
     */
    @Test
    public void registrationOfExistingUser() {
        try {
             user = userService.newUserRegistration(user);
        } catch (Exception e) {
            assertEquals("Account with e-mail address " + user.getEmail() + "already exists.",e.getMessage());
        }
    }


    /**
     * Tests a successful user logging.
     */
    @Test
    public void userLogInSuccess() {
       RegisteredUser user = userService.userLogIn("is@gmail.com","pass");
       assertEquals("is@gmail.com",user.getEmail());
       assertEquals("$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",user.getPassword());
       assertEquals("Stastny",user.getSurname());
       assertEquals("Ivan",user.getName());
       assertEquals(RoleEnum.USER,user.getRole());
    }

    /**
     * Tests an unsuccessful logging of non-existent user.
     */
    @Test
    public void userLogInNotExistingUser() {
        try {
            RegisteredUser user = userService.userLogIn("is@gmal.com", "pass");
            assertNull(user);
        } catch (Exception e) {
            assertEquals("User and password do not match",e.getMessage());
        }
    }

    /**
     * Tests an unsuccessful logging of existent user with .
     */
    @Test
    public void userLogInBadPassword() {
        try {
            RegisteredUser user = userService.userLogIn("is@gmail.com","pass24");
            assertNull(user);
        } catch (Exception e) {
            assertEquals("User and password do not match",e.getMessage());
        }
    }


}