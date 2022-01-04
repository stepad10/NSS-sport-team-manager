/*
 * UserMapperMyBatis
 *
 * 0.1
 *
 * Author: D. Štěpánek
 */

package cz.profinit.sportTeamManager.mapperMyBatis;

import cz.profinit.sportTeamManager.configuration.MyBatisConfigurationTest;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for User mapper
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@ActiveProfiles("database")
public class UserMapperMyBatisTest {

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    private RegisteredUser insertUserHelp(String email) {
         return userMapperMyBatis.insertUser(new RegisteredUser("Tomas", "Smutny", "pass1", email, RoleEnum.USER));
    }

    private RegisteredUser deleteUserByIdHelp(Long id) {
        return userMapperMyBatis.deleteUserById(id);
    }

    @Test
    public void insertRegisteredUser() {
        RegisteredUser regUs = new RegisteredUser("Tomas", "Smutny", "pass1", "vesjan@gmail.com", RoleEnum.USER);
        RegisteredUser insertedUser = userMapperMyBatis.insertUser(regUs);
        Assert.assertNotNull(insertedUser);
        Assert.assertNotNull(insertedUser.getEntityId());
        deleteUserByIdHelp(insertedUser.getEntityId());
    }

    @Test
    public void DeleteRegisteredUserById() {
        RegisteredUser regUs = insertUserHelp("0@0.0");
        Assert.assertNotNull(regUs);
        RegisteredUser removedUser = userMapperMyBatis.deleteUserById(regUs.getEntityId());
        Assert.assertNotNull(removedUser);
    }

    @Test
    public void DeleteRegisteredUserByEmail() {
        RegisteredUser regUs = insertUserHelp("1@1.1");
        Assert.assertNotNull(regUs);
        RegisteredUser removedUser = userMapperMyBatis.deleteUserByEmail(regUs.getEmail());
        Assert.assertNotNull(removedUser);
    }

    @Test
    public void DeleteNonExistingRegisteredUserByEmail() {
        RegisteredUser removedUser = userMapperMyBatis.deleteUserByEmail(".");
        Assert.assertNull(removedUser);
    }

    @Test
    public void findUserByEmail() {
        RegisteredUser regUs = insertUserHelp("a@aa.aaa");
        Assert.assertNotNull(regUs);
        RegisteredUser user = userMapperMyBatis.findUserByEmail(regUs.getEmail());
        Assert.assertNotNull(user);
        Assert.assertEquals(regUs.getEmail(), user.getEmail());
        deleteUserByIdHelp(regUs.getEntityId());
    }

    @Test
    public void findUserById() {
        RegisteredUser regUs = insertUserHelp("b@bb.bbb");
        Assert.assertNotNull(regUs);
        RegisteredUser user = userMapperMyBatis.findUserById(regUs.getEntityId());
        Assert.assertNotNull(user);
        Assert.assertEquals(regUs.getEmail(), user.getEmail());
        deleteUserByIdHelp(regUs.getEntityId());
    }

    @Test
    public void findNonExistingUserById() {
        RegisteredUser user = userMapperMyBatis.findUserById(0L);
        Assert.assertNull(user);
    }

    @Test
    public void findNonExistingUserByEmail() {
        RegisteredUser user = userMapperMyBatis.findUserByEmail("");
        Assert.assertNull(user);
    }

    @Test
    public void updateUser() {
        RegisteredUser user = insertUserHelp("c@cc.ccc");
        Assert.assertNotNull(user);
        user.setSurname("Kadenko");
        userMapperMyBatis.updateUser(user);
        Assert.assertEquals(user.getSurname(), userMapperMyBatis.findUserById(user.getEntityId()).getSurname());
        deleteUserByIdHelp(user.getEntityId());
    }
}