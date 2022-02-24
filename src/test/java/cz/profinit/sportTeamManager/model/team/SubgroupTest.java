/*
 * SubgroupTest
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.model.team;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import cz.profinit.sportTeamManager.exception.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for Subgroup class
 */
@RunWith(SpringRunner.class)
public class SubgroupTest {
    private Subgroup subgroup;
    private RegisteredUser user1;
    private RegisteredUser user2;

    /**
     * Before a test create a subgroup with one user.
     */
    @Before
    public void setUp() {
        List<RegisteredUser> userList = new ArrayList<>();

        user1 = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        user2 = new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
        userList.add(user1);
        subgroup = new Subgroup("Players", 0L);
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
     * @throws EntityNotFoundException if user to be removed wasn't found
     */
    @Test(expected = EntityNotFoundException.class)
    public void removeUser() throws EntityNotFoundException {
        subgroup.removeUser(user1);
        assertFalse(subgroup.getUserList().contains(user1));
        subgroup.removeUser(user2);
    }

    /**
     * Tests getting user from userList
     * @throws EntityNotFoundException if no user was found
     */
    @Test(expected = EntityNotFoundException.class)
    public void getUser() throws EntityNotFoundException {
        RegisteredUser regUs = subgroup.getUser(user1.getEmail());
        assertEquals(regUs, user1);
        subgroup.getUser(user2.getEmail());
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