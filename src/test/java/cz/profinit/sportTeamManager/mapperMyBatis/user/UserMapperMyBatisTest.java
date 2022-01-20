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

    public RegisteredUser insertUserHelp(String email) {
        RegisteredUser regUs = new RegisteredUser("Tomas", "Smutny", "pass1", email, RoleEnum.USER);
        userMapperMyBatis.insertUser(regUs);
        return regUs;
    }

    public void deleteUserByIdHelp(Long id) {
        userMapperMyBatis.deleteUserById(id);
    }

    @Test
    public void insertRegisteredUser() {
        RegisteredUser regUs = new RegisteredUser("Tomas", "Smutny", "pass1", "vesjan@gmail.com", RoleEnum.USER);
        userMapperMyBatis.insertUser(regUs);
        Assert.assertNotNull(regUs.getEntityId());
        System.out.println("UserId is = " + regUs.getEntityId());
        Assert.assertNotNull(userMapperMyBatis.findUserById(regUs.getEntityId()));
        deleteUserByIdHelp(regUs.getEntityId());
        Assert.assertNull(userMapperMyBatis.findUserById(regUs.getEntityId()));
    }

    @Test
    public void DeleteRegisteredUserById() {
        RegisteredUser presetUser = insertUserHelp("0@0.0");
        Assert.assertNotNull(presetUser);
        userMapperMyBatis.deleteUserById(presetUser.getEntityId());
        Assert.assertNull(userMapperMyBatis.findUserById(presetUser.getEntityId()));
    }

    @Test
    public void DeleteRegisteredUserByEmail() {
        RegisteredUser presetUser = insertUserHelp("1@1.1");
        Assert.assertNotNull(presetUser);
        userMapperMyBatis.deleteUserByEmail(presetUser.getEmail());
        Assert.assertNull(userMapperMyBatis.findUserById(presetUser.getEntityId()));
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
        String presetSurname = presetUser.getName();
        presetUser.setSurname("Kadenko");
        userMapperMyBatis.updateUser(presetUser);
        Assert.assertNotEquals(presetSurname, presetUser.getSurname());
        deleteUserByIdHelp(presetUser.getEntityId());
        Assert.assertNull(userMapperMyBatis.findUserById(presetUser.getEntityId()));
    }
}