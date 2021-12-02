/*
 * SubgroupTest
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.model.team;

import cz.profinit.sportTeamManager.configuration.ApplicationConfiguration;
import cz.profinit.sportTeamManager.model.user.RegistredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Subgroup class
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class SubgroupTest {
    private Subgroup subgroup;
    private User user1;
    private User user2;

    /**
     * Before a test create a subgroup with one user.
     */
    @Before
    public void setUp() {
        List<User> userList = new ArrayList<>();

        user1 = new RegistredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        user2 = new RegistredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
        userList.add(user1);
        subgroup = new Subgroup("Players");
        subgroup.setUserList(userList);
    }

    /**
     * Tests adding a user to the subgroup.
     */
    @Test
    public void addUser() {
        subgroup.addUser(user2);
        assertTrue(subgroup.getUserList().contains(user1));
        assertTrue(subgroup.getUserList().contains(user2));
    }

    /**
     * Tests removing a user form a subgroup.
     */
    @Test
    public void removeUser() {
        subgroup.removeUser(user1);
        assertFalse(subgroup.getUserList().contains(user1));
        try {
            subgroup.removeUser(user2);
        } catch (Exception e) {
            assertEquals("no user in list",e.getMessage());
        }
    }

    /**
     * Tests if the user is in the subgroup or not.
     */
    @Test
    public void isUserInList() {
        assertTrue(subgroup.isUserInList(user1));
        assertFalse(subgroup.isUserInList(user2));
    }

}