package cz.profinit.sportTeamManager.repositories.user;

import cz.profinit.sportTeamManager.exceptions.EmailExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;

import cz.profinit.sportTeamManager.stubs.stubRepositories.user.StubUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for User repository
 */
@RunWith(SpringRunner.class)
public class UserRepositoryImplTest {

    private final StubUserRepository userRepository = new StubUserRepository();
    private RegisteredUser presetUser;
    private RegisteredUser presetUserNotInDb;

    @Before
    public void SetUp() {
        presetUser = new RegisteredUser("Jan", "Vesely", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "a@seznam.cz", RoleEnum.USER);
        presetUserNotInDb = new RegisteredUser("Not", "InDb", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "x@xxx.com", RoleEnum.USER);
    }

    /**
     * insert registeredUser that already exists and throw
     * @throws EmailExistsException if registeredUser email already exists in database
     */
    @Test(expected = EmailExistsException.class)
    public void insertRegisteredUserThatAlreadyExists() throws EmailExistsException {
        userRepository.insertRegisteredUser(presetUser);
    }

    /**
     * insert registeredUser successfully
     */
    @Test
    public void insertRegisteredUser() {
        RegisteredUser insertedUser = userRepository.insertRegisteredUser(presetUserNotInDb);
        Assert.assertNotNull(insertedUser);
    }

    /**
     * update registeredUser and check changed name
     * @throws EntityNotFoundException if presetUser wasn't found
     */
    @Test
    public void updateRegisteredUser() throws EntityNotFoundException {
        RegisteredUser updatedUser = userRepository.updateRegisteredUser(presetUser);
        Assert.assertNotEquals(presetUser.getName(), updatedUser.getName());
    }

    /**
     * update registeredUser that is not in database
     * @throws EntityNotFoundException if presetUserNotInDb wasn't found
     */
    @Test(expected = EntityNotFoundException.class)
    public void updateNotExistingRegisteredUser() throws EntityNotFoundException {
        userRepository.updateRegisteredUser(presetUserNotInDb);
    }

    /**
     * find registeredUser that is in database
     * @throws EntityNotFoundException if registeredUser wasn't found
     */
    @Test
    public void findRegisteredUser() throws EntityNotFoundException {
        RegisteredUser foundUser = userRepository.findRegisteredUser(presetUser);
        Assert.assertEquals(presetUser.getEmail(), foundUser.getEmail());
    }

    /**
     * find registeredUser that is not in database
     * @throws EntityNotFoundException if registeredUser wasn't found
     */
    @Test(expected = EntityNotFoundException.class)
    public void findNotExistingRegisteredUser() throws EntityNotFoundException {
        userRepository.findRegisteredUser(presetUserNotInDb);
    }

    /**
     * find user by id that is in database
     * @throws EntityNotFoundException if registeredUser wasn't found
     */
    @Test
    public void findUserById() throws EntityNotFoundException {
        RegisteredUser foundUser = userRepository.findUserById(10L);
        Assert.assertNotNull(foundUser);
    }

    /**
     * find user by id that is not in database
     * @throws EntityNotFoundException if registeredUser wasn't found
     */
    @Test(expected = EntityNotFoundException.class)
    public void findNotExistingUserById() throws EntityNotFoundException {
        userRepository.findUserById(1L);
    }

    /**
     * find user by email that is in database
     * @throws EntityNotFoundException if registeredUser wasn't found
     */
    @Test
    public void findUserByEmail() throws EntityNotFoundException {
        RegisteredUser foundUser = userRepository.findUserByEmail(presetUser.getEmail());
        Assert.assertEquals(presetUser.getEmail(), foundUser.getEmail());
    }

    /**
     * find user by email that is not in database
     * @throws EntityNotFoundException if registeredUser wasn't found
     */
    @Test(expected = EntityNotFoundException.class)
    public void findNotExistingUserByEmail() throws EntityNotFoundException {
        userRepository.findUserByEmail(presetUserNotInDb.getEmail());
    }


    /**
     * find registeredUser that is in database
     * @throws EntityNotFoundException if registeredUser wasn't found
     */
    @Test
    public void deleteRegisteredUser() throws EntityNotFoundException {
        RegisteredUser deletedUser = userRepository.deleteRegisteredUser(presetUser);
        Assert.assertEquals(presetUser, deletedUser);
    }

    /**
     * find registeredUser that is not in database
     * @throws EntityNotFoundException if registeredUser wasn't found
     */
    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistingRegisteredUser() throws EntityNotFoundException {
        userRepository.deleteRegisteredUser(presetUserNotInDb);
    }
}
