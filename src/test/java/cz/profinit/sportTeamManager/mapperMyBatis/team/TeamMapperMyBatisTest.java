/*
 * TeamMapperMyBatis
 *
 * 0.1
 *
 * Author: D. Štěpánek
 */


package cz.profinit.sportTeamManager.mapperMyBatis.team;

import cz.profinit.sportTeamManager.configuration.MyBatisConfigurationTest;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for Team mapper
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@TestPropertySource("/test.properties")
public class TeamMapperMyBatisTest {

    @Autowired
    private TeamMapperMyBatis teamMapperMyBatis;

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    @Test
    public void insertTeam() {
        Long userId = 6L;
        RegisteredUser owner = userMapperMyBatis.findUserById(userId);
        Team team = new Team("Insert team", "Insert", new ArrayList<>(), owner);
        teamMapperMyBatis.insertTeam(team);
        Assert.assertNotNull(team.getEntityId());
        Assert.assertNotNull(teamMapperMyBatis.findTeamById(team.getEntityId()));
    }

    @Test
    public void updateTeam() {
        Long teamId = 2L;
        Team team = teamMapperMyBatis.findTeamById(teamId);
        String prevName = team.getName();
        team.setName("New updated team name");
        teamMapperMyBatis.updateTeam(team);
        Assert.assertNotEquals(prevName, team.getName());
    }

    @Test
    public void deleteTeamById() {
        Long teamId = 1L;
        teamMapperMyBatis.deleteTeamById(teamId);
        Assert.assertNull(teamMapperMyBatis.findTeamById(teamId));
    }

    @Test
    public void findTeamById() {
        Long teamId = 3L;
        Team foundTeam = teamMapperMyBatis.findTeamById(teamId);
        Assert.assertNotNull(foundTeam);
    }

    @Test
    public void findTeamsByName() {
        String teamsName = "Find teams";
        List<Team> foundTeams = teamMapperMyBatis.findTeamsByName(teamsName);
        Assert.assertEquals(2, foundTeams.size());
    }
}
