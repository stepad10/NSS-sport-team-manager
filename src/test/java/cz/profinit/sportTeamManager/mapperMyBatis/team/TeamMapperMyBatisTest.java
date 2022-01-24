/*
 * TeamMapperMyBatis
 *
 * 0.1
 *
 * Author: D. Štěpánek
 */


package cz.profinit.sportTeamManager.mapperMyBatis.team;

import cz.profinit.sportTeamManager.configuration.MyBatisConfiguration;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.team.Team;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for Team mapper
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ContextConfiguration(classes = MyBatisConfiguration.class)
@TestPropertySource("/test.properties")
public class TeamMapperMyBatisTest {

    @Autowired
    private TeamMapperMyBatis teamMapperMyBatis;

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    private RegisteredUser insertUserHelp(String email) {
        RegisteredUser regUs = new RegisteredUser("Tomas", "Smutny", "pass1", email, RoleEnum.USER);
        userMapperMyBatis.insertUser(regUs);
        return regUs;
    }

    private Team insertTeamHelp(String userEmail, String teamName) {
        RegisteredUser owner = insertUserHelp(userEmail);
        Team team = new Team(teamName, "sipky", new ArrayList<>(), owner);
        teamMapperMyBatis.insertTeam(team);
        return team;
    }

    private void deleteUserByIdHelp(Long id) {
        userMapperMyBatis.deleteUserById(id);
    }

    private void deleteTeamHelp(Team team) {
        teamMapperMyBatis.deleteTeamById(team.getEntityId());
        deleteUserByIdHelp(team.getOwner().getEntityId());
    }

    @Test
    public void insertTeam() {
        RegisteredUser presetUser = insertUserHelp("c@c.c");
        Assert.assertNotNull(presetUser);
        Team team = new Team("C team", "sipky", new ArrayList<>(), presetUser);
        teamMapperMyBatis.insertTeam(team);
        Assert.assertNotNull(team.getEntityId());
        deleteTeamHelp(team);
        Assert.assertNull(teamMapperMyBatis.findTeamById(team.getEntityId()));
        Assert.assertNull(userMapperMyBatis.findUserById(team.getOwner().getEntityId()));
    }

    @Test
    public void updateTeam() {
        Team presetTeam = insertTeamHelp("d@d.d", "D team");
        Assert.assertNotNull(presetTeam);
        String teamSport = presetTeam.getSport();
        presetTeam.setSport("famfrpal");
        teamMapperMyBatis.updateTeam(presetTeam);
        Assert.assertNotEquals(teamSport, presetTeam.getSport());
        deleteTeamHelp(presetTeam);
        Assert.assertNull(teamMapperMyBatis.findTeamById(presetTeam.getEntityId()));
        Assert.assertNull(userMapperMyBatis.findUserById(presetTeam.getOwner().getEntityId()));
    }

    @Test
    public void deleteTeamById() {
        Team presetTeam = insertTeamHelp("a@a.a", "A team");
        Assert.assertNotNull(presetTeam);
        teamMapperMyBatis.deleteTeamById(presetTeam.getEntityId());
        deleteUserByIdHelp(presetTeam.getOwner().getEntityId());
        Assert.assertNull(teamMapperMyBatis.findTeamById(presetTeam.getEntityId()));
        Assert.assertNull(userMapperMyBatis.findUserById(presetTeam.getOwner().getEntityId()));
    }

    @Test
    public void findTeamById() {
        Team presetTeam = insertTeamHelp("a@a.a", "A team");
        Assert.assertNotNull(presetTeam);
        Team foundTeam = teamMapperMyBatis.findTeamById(presetTeam.getEntityId());
        Assert.assertNotNull(foundTeam);
        Assert.assertEquals(presetTeam.getName(), foundTeam.getName());
        deleteTeamHelp(presetTeam);
        Assert.assertNull(teamMapperMyBatis.findTeamById(presetTeam.getEntityId()));
        Assert.assertNull(userMapperMyBatis.findUserById(presetTeam.getOwner().getEntityId()));
    }

    @Test
    public void findTeamsByName() {
        Team presetTeam = insertTeamHelp("b@b.b", "B team");
        Assert.assertNotNull(presetTeam);
        List<Team> foundTeams = teamMapperMyBatis.findTeamsByName(presetTeam.getName());
        Assert.assertEquals(1, foundTeams.size());
        deleteTeamHelp(presetTeam);
        foundTeams = teamMapperMyBatis.findTeamsByName(presetTeam.getName());
        Assert.assertEquals(0, foundTeams.size());
        Assert.assertNull(teamMapperMyBatis.findTeamById(presetTeam.getEntityId()));
        Assert.assertNull(userMapperMyBatis.findUserById(presetTeam.getOwner().getEntityId()));
    }
}
