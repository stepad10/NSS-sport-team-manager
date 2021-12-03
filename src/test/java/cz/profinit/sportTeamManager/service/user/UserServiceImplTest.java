/*
 * UserServiceImplTest
 *
 * 0.1
 *
 * Author: J. Janský
 */

package cz.profinit.sportTeamManager.service.user;

import cz.profinit.sportTeamManager.configuration.ApplicationConfiguration;
import cz.profinit.sportTeamManager.dto.RegisteredUserDTO;
import cz.profinit.sportTeamManager.mappers.UserMapper;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.UserRepository;
import cz.profinit.sportTeamManager.stubRepositories.StubUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Unit tests for User service implementation
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class UserServiceImplTest {
    private UserServiceImpl userService;
    private RegisteredUserDTO userDto;
    private RegisteredUserDTO userDto2;

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
        userService = new UserServiceImpl(passwordEncoder,userMapper,userRepository);
        userDto = new RegisteredUserDTO("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        userDto2 = new RegisteredUserDTO("Tomas", "Smutny", "pass", "ab@gmail.com", RoleEnum.USER);
    }

    /**
     * Tests a successful registration of the new user.
     */
    @Test
    public void newUserRegistration() {
        RegisteredUser user = userService.newUserRegistration(userDto);
        assertEquals(userDto.getName(),user.getName());
        assertEquals(userDto.getSurname(),user.getSurname());
        assertEquals(userDto.getEmail(),userDto.getEmail());
        assertEquals(userDto.getRole(),user.getRole());
        assertNotEquals(userDto.getPassword(),user.getPassword());
    }

    /**
     * Tests unsuccessful registration of already registered user.
     */
    @Test
    public void registrationOfExistingUser() {
        try {
            RegisteredUser user = userService.newUserRegistration(userDto2);
        } catch (Exception e) {
            assertEquals("Account with e-mail address " + userDto2.getEmail() + "already exists.",e.getMessage());
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