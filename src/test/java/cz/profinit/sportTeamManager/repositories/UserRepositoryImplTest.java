package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.configuration.MyBatisConfiguration;
import cz.profinit.sportTeamManager.configuration.MyBatisConfigurationTest;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for User repository
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@ActiveProfiles("Main")
public class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param email unique attribute to insert user to db correctly
     * @return inserted user to db, could be null or RegisteredUser
     */
    private RegisteredUser insertUserHelp(String email) {
        return userRepository.insertRegisteredUser(new RegisteredUser("Tomas", "Smutny", "pass1", email, RoleEnum.USER));
    }

    /**
     *
     * @param regUs user to delete from db
     * @return deleted user, null if unsuccessful
     * @throws EntityNotFoundException if entity wasn't found
     */
    private RegisteredUser deleteUserByIdHelp(RegisteredUser regUs) throws EntityNotFoundException {
        return userRepository.deleteRegisteredUser(regUs);
    }

    /**
     * insert registeredUser, check conditions and post-delete it
     * @throws EntityNotFoundException called method exception
     */
    @Test
    public void insertRegisteredUser() throws EntityNotFoundException {
        RegisteredUser registeredUser = new RegisteredUser("Jan", "Vesely", "password123", "a@b.c", RoleEnum.USER);
        RegisteredUser user = userRepository.insertRegisteredUser(registeredUser);
        Assert.assertNotNull(user);
        RegisteredUser deletedUser = userRepository.deleteRegisteredUser(registeredUser);
        Assert.assertNotNull(deletedUser);
    }

    /**
     * pre-insert registeredUser update it and post-delete
     * @throws EntityNotFoundException called method exception
     */
    @Test
    public void updateRegisteredUser() throws EntityNotFoundException {
        RegisteredUser registeredUser = insertUserHelp("1@11.111");
        Assert.assertNotNull(registeredUser);
        registeredUser.setSurname("Skodak");
        RegisteredUser updatedUser = userRepository.updateRegisteredUser(registeredUser);
        Assert.assertNotNull(updatedUser);
        Assert.assertEquals(registeredUser.getSurname(), updatedUser.getSurname());
        deleteUserByIdHelp(registeredUser);
    }

    /**
     * pre-insert registeredUser and find it in db, post-delete it
     * @throws EntityNotFoundException called method exception
     */
    @Test
    public void findRegisteredUser() throws EntityNotFoundException {
        RegisteredUser registeredUser = insertUserHelp("2@22.222");
        Assert.assertNotNull(registeredUser);
        RegisteredUser foundUser = userRepository.findRegisteredUser(registeredUser);
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(registeredUser, foundUser);
        deleteUserByIdHelp(registeredUser);
    }
}
