package cz.profinit.sportTeamManager.repositories.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import cz.profinit.sportTeamManager.exceptions.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for User repository using Mockito
 */

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Mock
    private UserMapperMyBatis userMapperMyBatis;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insertRegisteredUserThatIsAlreadyInDb() {
        RegisteredUser regUs = new RegisteredUser();
        regUs.setEntityId(regUs.getEntityId());
        when(userMapperMyBatis.findUserById(regUs.getEntityId())).thenReturn(regUs);

        Assert.assertThrows(EntityAlreadyExistsException.class, () -> userRepository.insertRegisteredUser(regUs));
        verify(userMapperMyBatis, times(1)).findUserById(regUs.getEntityId());
        verify(userMapperMyBatis, times(0)).insertUser(regUs);
    }

    @Test
    public void insertRegisteredUserSuccessfully() throws EntityAlreadyExistsException {
        RegisteredUser regUs = new RegisteredUser();
        when(userMapperMyBatis.findUserById(regUs.getEntityId())).thenReturn(null);

        userRepository.insertRegisteredUser(regUs);
        verify(userMapperMyBatis, times(1)).findUserById(regUs.getEntityId());
        verify(userMapperMyBatis, times(1)).insertUser(regUs);
    }

    @Test
    public void updateNotExistingRegisteredUser() throws EntityNotFoundException {
        RegisteredUser regUs = new RegisteredUser();
        when(userMapperMyBatis.findUserById(regUs.getEntityId())).thenReturn(null);

        Assert.assertThrows(EntityNotFoundException.class, () -> userRepository.updateRegisteredUser(regUs));
        verify(userMapperMyBatis, times(1)).findUserById(regUs.getEntityId());
        verify(userMapperMyBatis, times(0)).updateUser(regUs);
    }

    @Test
    public void updateRegisteredUser() throws EntityNotFoundException {
        RegisteredUser regUs = new RegisteredUser();
        when(userMapperMyBatis.findUserById(regUs.getEntityId())).thenReturn(regUs);

        userRepository.updateRegisteredUser(regUs);
        verify(userMapperMyBatis, times(1)).findUserById(regUs.getEntityId());
        verify(userMapperMyBatis, times(1)).updateUser(regUs);
    }

    @Test
    public void findNotExistingRegisteredUserById() {
        Long userId = 0L;
        when(userMapperMyBatis.findUserById(userId)).thenReturn(null);

        Assert.assertThrows(EntityNotFoundException.class, () -> userRepository.findUserById(userId));
        verify(userMapperMyBatis, times(1)).findUserById(userId);
    }

    @Test
    public void findRegisteredUserById() throws EntityNotFoundException {
        Long userId = 1L;
        when(userMapperMyBatis.findUserById(userId)).thenReturn(new RegisteredUser());

        RegisteredUser regUs = userRepository.findUserById(userId);
        Assert.assertNotNull(regUs);
        verify(userMapperMyBatis, times(1)).findUserById(userId);
    }

    @Test
    public void findNotExistingRegisteredUserByEmail() {
        String userEmail = "";
        when(userMapperMyBatis.findUserByEmail(userEmail)).thenReturn(null);

        Assert.assertThrows(EntityNotFoundException.class, () -> userRepository.findUserByEmail(userEmail));
        verify(userMapperMyBatis, times(1)).findUserByEmail(userEmail);
    }

    @Test
    public void findRegisteredUserByEmail() throws EntityNotFoundException {
        String userEmail = "";
        when(userMapperMyBatis.findUserByEmail(userEmail)).thenReturn(new RegisteredUser());

        RegisteredUser regUs = userRepository.findUserByEmail(userEmail);
        Assert.assertNotNull(regUs);
        verify(userMapperMyBatis, times(1)).findUserByEmail(userEmail);
    }

    @Test
    public void deleteNotExistingRegisteredUser() {
        Long userId = 0L;
        when(userMapperMyBatis.findUserById(userId)).thenReturn(null);

        Assert.assertThrows(EntityNotFoundException.class, () -> userRepository.deleteRegisteredUser(userId));
        verify(userMapperMyBatis, times(1)).findUserById(userId);
        verify(userMapperMyBatis, times(0)).deleteUserById(userId);
    }

    @Test
    public void deleteRegisteredUser() throws EntityNotFoundException {
        Long userId = 1L;
        when(userMapperMyBatis.findUserById(userId)).thenReturn(new RegisteredUser());

        userRepository.deleteRegisteredUser(userId);
        verify(userMapperMyBatis, times(1)).findUserById(userId);
        verify(userMapperMyBatis, times(1)).deleteUserById(userId);
    }
}
