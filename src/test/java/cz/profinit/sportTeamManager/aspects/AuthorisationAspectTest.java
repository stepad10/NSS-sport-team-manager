/*
 * AuthorisationAspectTest
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.aspects;

import cz.profinit.sportTeamManager.SportTeamManagerApplication;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.repositories.user.UserRepository;
import cz.profinit.sportTeamManager.service.team.TeamService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests of team authorization.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"authorization", "stub_services", "aspects", "authentication","webFull"})
@EnableAspectJAutoProxy
@WebAppConfiguration
@AutoConfigureMockMvc
@ContextConfiguration(classes = AuthorisationAspect.class)
@SpringBootTest(classes = SportTeamManagerApplication.class)
public class AuthorisationAspectTest {
    private RegisteredUser user_in_team;
    private RegisteredUser user_in_subgroups;

    @Autowired
    private TeamService teamService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    /**
     * Creating stub users
     */
    @Before
    public void setUp() throws EntityNotFoundException {
        user_in_team = userRepository.findUserByEmail("email@gmail.com");
        user_in_subgroups = userRepository.findUserByEmail("is@gmail.com");
    }


    /**
     * Authorized requests
     */
    @Test
    @WithMockUser(username="email@gmail.com",roles={"USER","ADMIN"})
    public void authorizationSuccess() throws EntityNotFoundException {
        Team team = null;

        team = teamService.changeTeamName(10L, "dd");
        assertNotEquals(null, team);

        team = null;
        team = teamService.changeTeamSport(10L, "dd");
        assertNotEquals(null, team);

        team = null;
        team = teamService.changeTeamOwner(10L, user_in_team);
        assertNotEquals(null, team);

        team = null;
        team = teamService.addUserToSubgroup(10L, "All Users", user_in_subgroups);
        assertNotEquals(null, team);

        team = null;
        team = teamService.addUserToTeam(10L, user_in_subgroups);
        assertNotEquals(null, team);

        team = null;
        team = teamService.addSubgroup(10L, "dd");
        assertNotEquals(null, team);

        team = null;
        team = teamService.deleteSubgroup(10L, "Empty");
        assertNotEquals(null, team);

        team = null;
        team = teamService.deleteUserFromSubgroup(10L, "Coaches", user_in_team);
        assertNotEquals(null, team);

        team = null;
        team = teamService.deleteUserFromTeam(10L, user_in_team);
        assertNotEquals(null, team);

        teamService.deleteTeam(10L);

        team = null;
        team = teamService.changeSubgroupName(10L, "Empty", "DD");
        assertNotEquals(null, team);

    }

    /**
     * Unauthorized requests
     */
    @Test
    @WithMockUser(username="is@gmail.com",roles={"USER","ADMIN"})
    public void authorizationFail() throws EntityNotFoundException {
        Team team = null;
        try {
            team = teamService.addSubgroup(10L, "dd");
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.changeTeamName(10L, "dd");
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.changeTeamSport(10L, "dd");
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.changeTeamOwner(10L, user_in_team);
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.addUserToSubgroup(10L, "All Users", user_in_subgroups);
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.addUserToTeam(10L, user_in_subgroups);
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.addSubgroup(10L, "dd");
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.deleteSubgroup(10L, "Empty");
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.deleteUserFromSubgroup(10L, "Coaches", user_in_team);
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.deleteUserFromTeam(10L, user_in_team);
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            teamService.deleteTeam(10L);
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }

        try {
            team = teamService.changeSubgroupName(10L, "Empty", "DD");
        } catch (Exception e) {
            assertEquals("Access denied", e.getMessage());
        }


        assertEquals(null, team);
    }

    /**
     * Requests without required authorization
     */
    @Test
    public void authorizationNotNeeded() throws EntityNotFoundException {
        Team team = teamService.getTeamById(10L);
        team = teamService.createNewTeam(new Team("A team", "golf", new ArrayList<>(), user_in_team));
    }


}