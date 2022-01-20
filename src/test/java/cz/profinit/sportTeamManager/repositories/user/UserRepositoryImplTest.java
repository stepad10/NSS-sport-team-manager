package cz.profinit.sportTeamManager.repositories.user;

import cz.profinit.sportTeamManager.exceptions.EmailExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.mappers.UserMapperTest;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;

import cz.profinit.sportTeamManager.stubs.stubRepositories.user.StubUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for User repository
 */
@RunWith(SpringRunner.class)
public class UserRepositoryImplTest {

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepositoryImpl(userMapperMyBatis);
    }

    @Test
    public void insertRegisteredUser() {

    }
}
