/*
 * UserMapperMyBatis
 *
 * 0.1
 *
 * Author: D. Štěpánek
 */

package cz.profinit.sportTeamManager.mapperMyBatis.user;

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
        Assert.assertNull(userMapperMyBatis.findUserById(insertedUser.getEntityId()));
    }

    @Test
    public void DeleteRegisteredUserById() {
        RegisteredUser presetUser = insertUserHelp("0@0.0");
        Assert.assertNotNull(presetUser);
        RegisteredUser deletedUser = userMapperMyBatis.deleteUserById(presetUser.getEntityId());
        Assert.assertNotNull(deletedUser);
        Assert.assertNull(userMapperMyBatis.findUserById(deletedUser.getEntityId()));
    }

    @Test
    public void DeleteRegisteredUserByEmail() {
        RegisteredUser presetUser = insertUserHelp("1@1.1");
        Assert.assertNotNull(presetUser);
        RegisteredUser deletedUser = userMapperMyBatis.deleteUserByEmail(presetUser.getEmail());
        Assert.assertNotNull(deletedUser);
        Assert.assertNull(userMapperMyBatis.findUserById(deletedUser.getEntityId()));
    }

    @Test
    public void DeleteNonExistingRegisteredUserByEmail() {
        RegisteredUser deletedUser = userMapperMyBatis.deleteUserByEmail(".");
        Assert.assertNull(deletedUser);
    }

    @Test
    public void findUserByEmail() {
        RegisteredUser presetUser = insertUserHelp("a@aa.aaa");
        Assert.assertNotNull(presetUser);
        RegisteredUser foundUser = userMapperMyBatis.findUserByEmail(presetUser.getEmail());
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(presetUser.getEmail(), foundUser.getEmail());
        deleteUserByIdHelp(presetUser.getEntityId());
        Assert.assertNull(userMapperMyBatis.findUserById(foundUser.getEntityId()));
    }

    @Test
    public void findUserById() {
        RegisteredUser presetUser = insertUserHelp("b@bb.bbb");
        Assert.assertNotNull(presetUser);
        RegisteredUser foundUser = userMapperMyBatis.findUserById(presetUser.getEntityId());
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(presetUser.getEmail(), foundUser.getEmail());
        deleteUserByIdHelp(presetUser.getEntityId());
        Assert.assertNull(userMapperMyBatis.findUserById(foundUser.getEntityId()));
    }

    @Test
    public void findNonExistingUserById() {
        RegisteredUser regUs = userMapperMyBatis.findUserById(0L);
        Assert.assertNull(regUs);
    }

    @Test
    public void findNonExistingUserByEmail() {
        RegisteredUser regUs = userMapperMyBatis.findUserByEmail("");
        Assert.assertNull(regUs);
    }

    @Test
    public void updateUser() {
        RegisteredUser presetUser = insertUserHelp("c@cc.ccc");
        Assert.assertNotNull(presetUser);
        presetUser.setSurname("Kadenko");
        userMapperMyBatis.updateUser(presetUser);
        RegisteredUser updatedUser = userMapperMyBatis.findUserById(presetUser.getEntityId());
        Assert.assertNotNull(updatedUser);
        Assert.assertEquals(presetUser.getSurname(), updatedUser.getSurname());
        deleteUserByIdHelp(presetUser.getEntityId());
        Assert.assertNull(userMapperMyBatis.findUserById(presetUser.getEntityId()));
    }
}