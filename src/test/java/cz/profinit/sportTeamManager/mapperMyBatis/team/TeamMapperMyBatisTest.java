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
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for Team mapper
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@ActiveProfiles("database")
public class TeamMapperMyBatisTest {

    @Autowired
    private TeamMapperMyBatis teamMapperMyBatis;

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    private RegisteredUser insertUserHelp(String email) {
        return userMapperMyBatis.insertUser(new RegisteredUser("Tomas", "Smutny", "pass1", email, RoleEnum.USER));
    }

    private Team insertTeamHelp(String userEmail, String teamName) {
        RegisteredUser owner = insertUserHelp(userEmail);
        Team team = new Team(teamName, "sipky", new ArrayList<>(), owner);
        return teamMapperMyBatis.insertTeam(team);
    }

    private RegisteredUser deleteUserByIdHelp(Long id) {
        return userMapperMyBatis.deleteUserById(id);
    }

    private Team deleteTeamHelp(Team team) {
        Team tm = teamMapperMyBatis.deleteTeamById(team.getEntityId());
        deleteUserByIdHelp(team.getOwner().getEntityId());
        return tm;
    }

    @Test
    public void insertTeam() {
        RegisteredUser presetUser = insertUserHelp("c@c.c");
        Assert.assertNotNull(presetUser);
        Team team = new Team("C team", "sipky", new ArrayList<>(), presetUser);
        Team insertedTeam = teamMapperMyBatis.insertTeam(team);
        Assert.assertNotNull(insertedTeam);
        Assert.assertNotNull(teamMapperMyBatis.findTeamById(insertedTeam.getEntityId()));
        deleteTeamHelp(insertedTeam);
        Assert.assertNull(teamMapperMyBatis.findTeamById(insertedTeam.getEntityId()));
        Assert.assertNull(userMapperMyBatis.findUserById(insertedTeam.getOwner().getEntityId()));
    }

    @Test
    public void updateTeam() {
        Team presetTeam = insertTeamHelp("d@d.d", "D team");
        Assert.assertNotNull(presetTeam);
        presetTeam.setSport("famfrpal");
        teamMapperMyBatis.updateTeam(presetTeam);
        Team updatedTeam = teamMapperMyBatis.findTeamById(presetTeam.getEntityId());
        Assert.assertNotNull(updatedTeam);
        Assert.assertEquals(presetTeam.getSport(), updatedTeam.getSport());
        deleteTeamHelp(presetTeam);
        Assert.assertNull(teamMapperMyBatis.findTeamById(presetTeam.getEntityId()));
        Assert.assertNull(userMapperMyBatis.findUserById(presetTeam.getOwner().getEntityId()));
    }

    @Test
    public void deleteTeamById() {
        Team presetTeam = insertTeamHelp("a@a.a", "A team");
        Assert.assertNotNull(presetTeam);
        Team deletedTeam = teamMapperMyBatis.deleteTeamById(presetTeam.getEntityId());
        Assert.assertNotNull(deletedTeam);
        deleteUserByIdHelp(deletedTeam.getOwner().getEntityId());
        Assert.assertNull(teamMapperMyBatis.findTeamById(deletedTeam.getEntityId()));
        Assert.assertNull(userMapperMyBatis.findUserById(deletedTeam.getOwner().getEntityId()));
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
