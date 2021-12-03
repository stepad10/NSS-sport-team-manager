/*
 * TeamServiceImplTest
 *
 * 0.1
 *
 * Author: J. Janský
 */

package cz.profinit.sportTeamManager.service.team;

import cz.profinit.sportTeamManager.configuration.ApplicationConfiguration;
import cz.profinit.sportTeamManager.dto.TeamDto;
import cz.profinit.sportTeamManager.mappers.TeamMapper;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.SubgroupRepository;
import cz.profinit.sportTeamManager.repositories.TeamRepository;
import cz.profinit.sportTeamManager.repositories.UserRepository;
import cz.profinit.sportTeamManager.stubRepositories.StubSubgroupRepository;
import cz.profinit.sportTeamManager.stubRepositories.StubTeamRepository;
import cz.profinit.sportTeamManager.stubRepositories.StubUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * Unit tests for Team service implementation
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class TeamServiceImplTest {
    private TeamServiceImpl teamService;
    private User loggedUser;
    private TeamDto teamDto;

    /**
     * Before a test create new TeamServiceImpl using stub repositories, TeamDto and new user.
     */
    @Before
    public void setUp() {

        SubgroupRepository subgroupRepository = new StubSubgroupRepository();
        UserRepository userRepository = new StubUserRepository();
        TeamRepository teamRepository = new StubTeamRepository();
        TeamMapper teamMapper = new TeamMapper();
        List<Subgroup> subgroupList = new ArrayList<>();

        teamService = new TeamServiceImpl(teamRepository, userRepository, subgroupRepository,teamMapper);
        loggedUser = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        teamDto = new TeamDto("A team", "golf",subgroupList, loggedUser);
    }

    /**
     * Tests creation of a new team
     */
    @Test
    public void createNewTeam() {
        Team team = teamService.createNewTeam(teamDto);
        assertEquals(loggedUser, team.getOwner());
        assertEquals(teamDto.getName(),team.getName());
        assertEquals(teamDto.getSport(),team.getSport());
        assertEquals(teamDto.getOwner(),team.getOwner());
        assertEquals(teamDto.getListOfSubgroups(),team.getListOfSubgroups());
            }

    /**
     * Tests of adding a new subgroup to the specific team.
     */
    @Test
    public void addNewSubgroup() {
        String subgroupName = "Players";
        Team team = teamService.getTeamByName("B team");
        team = teamService.addSubgroup(team, subgroupName);
        assertEquals(4, team.getListOfSubgroups().size());
        assertEquals(subgroupName, team.getListOfSubgroups().get(3).getName());
    }

    /**
     * Tests removing a subgroup from a team
     */
    @Test
    public void deleteSubgroup() {
        String subgroupName = "Empty subgroup";
        Team team = teamService.getTeamByName("B team");
        team = teamService.deleteSubgroup(team, subgroupName);
        assertEquals(2, team.getListOfSubgroups().size());
        try {
            team.getTeamSubgroup(subgroupName);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "No subgroup found");
        }
    }

    /**
     * Tests adding new user to the team.
     */
    @Test
    public void addUserToTeam() {
        User user = new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
        Team team = teamService.getTeamByName("B team");
        team = teamService.addUserToTeam(team, user);
        assertEquals(user, team.getListOfSubgroups().get(0).getUserList().get(1));
    }

    /**
     * Tests adding new user to the subgroup which is not in already in a team.
     */
    @Test
    public void addUserToSubgroupWhoIsNotInAllUsers() {
        User user = new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
        Team team = teamService.getTeamByName("B team");
        team = teamService.addUserToSubgroup(team, "Coaches", user);
        System.out.println(team.toString());
        assertEquals(user, team.getListOfSubgroups().get(1).getUserList().get(1));
        assertEquals(user, team.getListOfSubgroups().get(0).getUserList().get(1));
    }


    /**
     * Tests adding a new user to the subgroup.
     */
    @Test
    public void addUserToSubgroupWhoIsInAllUsers() {
        Team team = teamService.getTeamByName("B team");
        team = teamService.addUserToSubgroup(team, "Empty subgroup", loggedUser);
        System.out.println(team.toString());
        assertEquals(loggedUser, team.getListOfSubgroups().get(2).getUserList().get(0));
        assertEquals(1, team.getListOfSubgroups().get(0).getUserList().size());
    }


    /**
     * Tests adding a user to the subgroup who is already in a subgroup.
     */
    @Test
    public void addAlreadyAddedUser() {
        User user = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        Team team = teamService.getTeamByName("B team");
        try {
            teamService.addUserToSubgroup(team, "Coaches", user);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "User is already in subgroup");
        }
    }

    /**
     * Tests deleting a user from a team and all subgroups.
     */
    @Test
    public void deleteUser() {
        Team team = teamService.getTeamByName("B team");
        team = teamService.deleteUserFromTeam(team, loggedUser);
        assertFalse(team.getTeamSubgroup("All Users").isUserInList(loggedUser));
        assertFalse(team.getTeamSubgroup("Coaches").isUserInList(loggedUser));
    }

    /**
     * Tests deleting a user from a subgroup.
     */
    @Test
    public void deleteUserFromSubgroup() {
        Team team = teamService.getTeamByName("B team");
        team = teamService.deleteUserFromSubgroup(team, "Coaches", loggedUser);
        assertFalse(team.getTeamSubgroup("Coaches").isUserInList(loggedUser));
    }
}