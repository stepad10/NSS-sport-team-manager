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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for User mapper
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@TestPropertySource("/test.properties")
public class UserMapperMyBatisTest {

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    @Test
    public void insertRegisteredUser() {
        RegisteredUser regUs = new RegisteredUser(
                "Insert",
                "RegUs",
                "pass1",
                "insert.regUs@test.com",
                RoleEnum.USER);
        userMapperMyBatis.insertUser(regUs);
        Assert.assertNotNull(regUs.getEntityId());
        Assert.assertNotNull(userMapperMyBatis.findUserById(regUs.getEntityId()));
    }

    @Test
    public void DeleteRegisteredUserById() {
        Long userId = 1L;
        userMapperMyBatis.deleteUserById(userId);
        Assert.assertNull(userMapperMyBatis.findUserById(userId));
    }

    @Test
    public void DeleteRegisteredUserByEmail() {
        String userEmail = "delete.byEmail@test.com";
        userMapperMyBatis.deleteUserByEmail(userEmail);
        Assert.assertNull(userMapperMyBatis.findUserByEmail(userEmail));
    }

    @Test
    public void findUserByEmail() {
        String userEmail = "find.byEmail@test.com";
        RegisteredUser foundUser = userMapperMyBatis.findUserByEmail(userEmail);
        Assert.assertNotNull(foundUser);
    }

    @Test
    public void findUserById() {
        Long userId = 4L;
        RegisteredUser foundUser = userMapperMyBatis.findUserById(userId);
        Assert.assertNotNull(foundUser);
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
        RegisteredUser regUs = userMapperMyBatis.findUserById(3L);
        String prevEmail = regUs.getEmail();
        regUs.setEmail("update.regUs@updated.com");
        userMapperMyBatis.updateUser(regUs);
        Assert.assertNotEquals(prevEmail, regUs.getSurname());
    }
}