/*
 * TeamServiceImplTest
 *
 * 0.1
 *
 * Author: J. Janský
 */

package cz.profinit.sportTeamManager.service.team;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.team.TeamRepository;
import cz.profinit.sportTeamManager.repositories.user.UserRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.team.StubTeamRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.user.StubUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * Unit tests for Team service implementation
 */
@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {ApplicationConfigurationTest.class})
@ActiveProfiles({"stub_repository"})
public class TeamServiceImplTest {
    private TeamServiceImpl teamService;
    private RegisteredUser loggedUser;
    private Team team;

    /**
     * Before a test create new TeamServiceImpl using stub repositories, TeamDTO and new user.
     */
    @Before
    public void setUp() {

        UserRepository userRepository = new StubUserRepository();
        TeamRepository teamRepository = new StubTeamRepository();
        List<Subgroup> subgroupList = new ArrayList<>();

        teamService = new TeamServiceImpl(teamRepository, userRepository);
        loggedUser = new RegisteredUser(
                "Ivan",
                "Stastny",
                "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                "is@gmail.com",
                RoleEnum.USER);
        team = new Team("A team", "golf", subgroupList, loggedUser);
        team.setEntityId(10L);
    }

    /**
     * Tests creation of a new team
     */
    @Test
    public void createNewTeam() {
        Team newTeam = teamService.createNewTeam(team);
        assertEquals(loggedUser, newTeam.getOwner());
        assertEquals(team.getName(), newTeam.getName());
        assertEquals(team.getSport(), newTeam.getSport());
        assertEquals(team.getOwner(), newTeam.getOwner());
        assertEquals(team.getListOfSubgroups(), newTeam.getListOfSubgroups());
    }

    /**
     * Tests of getting team by id
     */
    @Test
    public void getsTeam() throws EntityNotFoundException {
            teamService.getTeamById(10L);
    }

    /**
     * Tests creation of getting non-existent team
     */
    @Test
    public void getsNonExistentTeam() {
        try {
            teamService.getTeamById(20L);
        } catch (Exception e) {
            assertEquals("Team entity not found!",e.getMessage());
        }
    }

    /**
     * Tests of adding a new subgroup to the specific team.
     */
    @Test
    public void addNewSubgroup() throws EntityNotFoundException {
        String subgroupName = "Players";
        Team team = teamService.getTeamById(10L);
        team = teamService.addSubgroup(team.getEntityId(), subgroupName);
        assertEquals(4, team.getListOfSubgroups().size());
        assertEquals(subgroupName, team.getListOfSubgroups().get(3).getName());
        try {
            team = teamService.addSubgroup(team.getEntityId(), subgroupName);
        } catch (Exception e) {
            assertEquals("Subgroup already exists", e.getMessage());
        }

    }

    /**
     * Tests removing a subgroup from a team
     */
    @Test
    public void deleteSubgroup() throws EntityNotFoundException {
        String subgroupName = "Empty subgroup";
        Team team = teamService.getTeamById(10L);
        team = teamService.deleteSubgroup(team.getEntityId(), subgroupName);
        assertEquals(2, team.getListOfSubgroups().size());
        try {
            teamService.deleteSubgroup(team.getEntityId(), subgroupName);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Subgroup enitity not found!");
        }
    }

    /**
     * Tests adding new user to the team.
     */
    @Test
    public void addUserToTeam() throws EntityNotFoundException {
        RegisteredUser user = new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
        Team team = teamService.getTeamById(10L);
        team = teamService.addUserToTeam(team.getEntityId(), user);
        assertEquals(user, team.getListOfSubgroups().get(0).getUserList().get(1));
    }

    /**
     * Tests adding new user to the subgroup which is not in already in a team.
     */
    @Test
    public void addUserToSubgroupWhoIsNotInAllUsers() throws EntityNotFoundException {
        RegisteredUser user = new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
        Team team = teamService.getTeamById(10L);
        team = teamService.addUserToSubgroup(team.getEntityId(), "Coaches", user);
        System.out.println(team.toString());
        assertEquals(user, team.getListOfSubgroups().get(1).getUserList().get(1));
        assertEquals(user, team.getListOfSubgroups().get(0).getUserList().get(1));
    }


    /**
     * Tests adding a new user to the subgroup.
     */
    @Test
    public void addUserToSubgroupWhoIsInAllUsers() throws EntityNotFoundException {
        Team team = teamService.getTeamById(10L);
        team = teamService.addUserToSubgroup(team.getEntityId(), "Empty subgroup", loggedUser);
        System.out.println(team.toString());
        assertEquals(loggedUser, team.getListOfSubgroups().get(2).getUserList().get(0));
        assertEquals(1, team.getListOfSubgroups().get(0).getUserList().size());
    }


    /**
     * Tests adding a user to the subgroup who is already in a subgroup.
     */
    @Test
    public void addAlreadyAddedUser() throws EntityNotFoundException {
        RegisteredUser user = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        Team team = teamService.getTeamById(10L);
        try {
            teamService.addUserToSubgroup(team.getEntityId(), "Coaches", user);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "User is already in subgroup");
        }
    }

    /**
     * Tests deleting a user from a team and all subgroups.
     */
    @Test
    public void deleteUser() throws EntityNotFoundException {
        Team team = teamService.getTeamById(10L);
        team = teamService.deleteUserFromTeam(team.getEntityId(), loggedUser);
        assertFalse(team.getTeamSubgroup("All Users").isUserInList(loggedUser));
        assertFalse(team.getTeamSubgroup("Coaches").isUserInList(loggedUser));
    }

    /**
     * Tests deleting a user from a subgroup.
     */
    @Test
    public void deleteUserFromSubgroup() throws EntityNotFoundException {
        Team team = teamService.getTeamById(10L);
        team = teamService.deleteUserFromSubgroup(team.getEntityId(), "Coaches", loggedUser);
        assertFalse(team.getTeamSubgroup("Coaches").isUserInList(loggedUser));
    }


    /**
     * Tests changing a team name.
     */
    @Test
    public void changeTeamName() throws EntityNotFoundException {
        team.setEntityId(10L);
        team = teamService.changeTeamName(team.getEntityId(), "C team");
        assertEquals("C team", team.getName());
    }

    /**
     * Tests a changing a team sport
     */
    @Test
    public void changeTeamSport() throws EntityNotFoundException {
        team = teamService.changeTeamSport(team.getEntityId(), "Rugby");
        assertEquals("Rugby", team.getSport());
    }

    /**
     * Tests a changing the owner
     */
    @Test
    public void changeTeamOwner() throws EntityNotFoundException {
        RegisteredUser user = new RegisteredUser(
                "Ivan",
                "Stastny",
                "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                "is@gmail.com",
                RoleEnum.USER);
        Team team = teamService.getTeamById(10L);
        try {
            team = teamService.changeTeamOwner(team.getEntityId(), user);
            assertEquals(user, team.getOwner());
            assertEquals(true, team.getTeamSubgroup("Coaches").isUserInList(user));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "");
        }
    }

    /**
     * Tests a changing the owner who is not in team
     */
    @Test
    public void changeTeamOwnerWhoIsNotInAllUsers() throws EntityNotFoundException {
        RegisteredUser user = new RegisteredUser(
                "Tomas",
                "Stastny",
                "pass",
                "ts@gmail.com",
                RoleEnum.USER);
        Team team = teamService.getTeamById(10L);
        try {
            team = teamService.changeTeamOwner(team.getEntityId(), user);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "User is not in team");
        }
    }


    /**
     * Tests a changing the owner who is not in coaches
     */
    @Test
    public void changeTeamOwnerWhoIsNotInCoaches() throws EntityNotFoundException {
        RegisteredUser user = new RegisteredUser(
                "Ivan",
                "Stastny",
                "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                "is@gmail.com",
                RoleEnum.USER);
        Team team = teamService.getTeamById(10L);
        team.getTeamSubgroup("Coaches").removeUser(user);
        try {
            team = teamService.changeTeamOwner(team.getEntityId(), user);
            assertEquals(true, team.getTeamSubgroup("Coaches").isUserInList(user));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "");
        }
    }


    /**
     * Tests a changing a subgroup name
     */
    @Test
    public void changeSubgroupName() throws EntityNotFoundException {
        Team team = teamService.getTeamById(10L);
        team = teamService.changeSubgroupName(10L, "Coaches", "Players");
        assertEquals("Players", team.getTeamSubgroup("Players").getName());
        assertEquals(false, team.isSubgroupInTeam("Coaches"));
    }

    /**
     * Tests a changing a subgroup name to the name of already existing one
     */
    @Test
    public void changeSubgroupNameToAlreadyExistingOne() throws EntityNotFoundException {
        Team team = teamService.getTeamById(10L);
        try {
            team = teamService.changeSubgroupName(10L, "Coaches", "All Users");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Subgroup already exists");
        }
    }

    /**
     * Tests a changing a subgroup name which do not exist in team
     */
    @Test
    public void changeSubgroupNameOfNotExistentSubgroup() throws EntityNotFoundException {
        Team team = teamService.getTeamById(10L);
        try {
            team = teamService.changeSubgroupName(10L, "Playes", "A");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Subgroup entity not found!");
        }
    }

}