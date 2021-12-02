package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.configuration.ApplicationConfiguration;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegistredUser;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class TeamServiceImplTest {
    private TeamServiceImpl teamService;
    private User loggedUser;

    @Before
    public void setUp() throws Exception {

        SubgroupRepository subgroupRepository = new StubSubgroupRepository();
        UserRepository userRepository = new StubUserRepository();
        TeamRepository teamRepository = new StubTeamRepository();

        teamService = new TeamServiceImpl(teamRepository, userRepository, subgroupRepository);
        loggedUser = new RegistredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
    }

    @Test
    public void createNewTeam() {
        Team team = teamService.createNewTeam("A team", "golf", loggedUser);
        assertEquals(loggedUser, team.getOwner());
    }

    @Test
    public void addNewSubgroup() {
        String subgroupName = "Players";
        Team team = teamService.getTeamByName("B team");
        team = teamService.addSubgroup(team,subgroupName);
        assertEquals(4,team.getListOfSubgroups().size());
        assertEquals(subgroupName,team.getListOfSubgroups().get(3).getName());
    }

    @Test
    public void deleteSubgroup() {
        String subgroupName = "Empty subgroup";
        Team team = teamService.getTeamByName("B team");
        team = teamService.deleteSubgroup(team,subgroupName);
        assertEquals(2,team.getListOfSubgroups().size());
        try{
            team.getTeamSubgroup(subgroupName);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"No subgroup found");
        }
    }

    @Test
    public void addUserToTeam() {
        User user = new RegistredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
        Team team = teamService.getTeamByName("B team");
        team = teamService.addUserToTeam(team, user);
        assertEquals(user, team.getListOfSubgroups().get(0).getUserList().get(1));
    }


    @Test
    public void addUserToSubgroupWhoIsNotInAllUsers() {
        User user = new RegistredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
        Team team = teamService.getTeamByName("B team");
        team = teamService.addUserToSubgroup(team, "Coaches", user);
        System.out.println(team.toString());
        assertEquals(user, team.getListOfSubgroups().get(1).getUserList().get(1));
        assertEquals(user, team.getListOfSubgroups().get(0).getUserList().get(1));
    }

    @Test
    public void addUserToSubgroupWhoIsInAllUsers() {
        Team team = teamService.getTeamByName("B team");
        team = teamService.addUserToSubgroup(team, "Empty subgroup", loggedUser);
        System.out.println(team.toString());
        assertEquals(loggedUser, team.getListOfSubgroups().get(2).getUserList().get(0));
        assertEquals(1, team.getListOfSubgroups().get(0).getUserList().size());
    }


    @Test
    public void addAlreadyAddedUser() {
        User user = new RegistredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        Team team = teamService.getTeamByName("B team");
        try {
            teamService.addUserToSubgroup(team, "Coaches", user);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"user is already in subgroup");
        }
    }

    @Test
    public void deleteUser() {
        Team team = teamService.getTeamByName("B team");
        team = teamService.deleteUserFromTeam(team, loggedUser);
        assertFalse(team.getTeamSubgroup("All Users").isUserInList(loggedUser));
        assertFalse(team.getTeamSubgroup("Coaches").isUserInList(loggedUser));
    }

    @Test
    public void deleteUserFromSubgroup() {
        Team team = teamService.getTeamByName("B team");
        team = teamService.deleteUserFromSubgroup(team,"Coaches",loggedUser);
        assertFalse(team.getTeamSubgroup("Coaches").isUserInList(loggedUser));
    }
}