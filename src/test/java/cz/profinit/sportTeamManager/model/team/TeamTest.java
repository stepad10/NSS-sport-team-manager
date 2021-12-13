/*
 * TeamTest
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.model.team;

import cz.profinit.sportTeamManager.configuration.ApplicationConfiguration;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Team class
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class TeamTest {
    private Team team;
    private RegisteredUser user1;
    private Subgroup subgroup;

    /**
     * Before a test create a team with one subgroup and owner.
     */
    @Before
    public void setUp() {
        List<RegisteredUser> userList = new ArrayList<>();
        List<Subgroup> subgroupList = new ArrayList<>();

        user1 = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        userList.add(user1);
        subgroup = new Subgroup("Players");
        subgroup.setUserList(userList);
        subgroupList.add(subgroup);

        team = new Team("A team", "Vodní lyžování", subgroupList, user1);
    }

    /**
     * Tests adding a new subgroup to the team.
     */
    @Test
    public void addNewSubgroup() {
        team.addNewSubgroup("Beginners");
        assertEquals(2, team.getListOfSubgroups().size());
        assertEquals("Beginners", team.getListOfSubgroups().get(1).getName());
    }

    /**
     * Tests obtaining a specific subgroup from a team.
     */
    @Test
    public void getTeamSubgroup() throws EntityNotFoundException {
        assertEquals(subgroup, team.getTeamSubgroup("Players"));
        try {
            team.getTeamSubgroup("A");
        } catch (Exception e) {
            assertEquals("No subgroup found", e.getMessage());
        }
    }

    /**
     * Tests deleting a specific subgroup from a team.
     */
    @Test
    public void deleteSubgroup() {
        team.deleteSubgroup("Players");
        assertEquals(0, team.getListOfSubgroups().size());
        try {
            team.deleteSubgroup("A");
        } catch (Exception e) {
            assertEquals("No subgroup found", e.getMessage());
        }
    }

    /**
     * Tests if the subgroup is in team or not.
     */
    @Test
    public void isSubgroupInTeam() {
        assertTrue(team.isSubgroupInTeam("Players"));
        assertFalse(team.isSubgroupInTeam("Nic"));
    }
}