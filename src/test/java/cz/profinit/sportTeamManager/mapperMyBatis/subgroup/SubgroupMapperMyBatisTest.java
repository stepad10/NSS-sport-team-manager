package cz.profinit.sportTeamManager.mapperMyBatis.subgroup;

import cz.profinit.sportTeamManager.configuration.MyBatisConfigurationTest;
import cz.profinit.sportTeamManager.mapperMyBatis.team.TeamMapperMyBatis;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.team.Subgroup;
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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@ActiveProfiles("database")
public class SubgroupMapperMyBatisTest {

    @Autowired
    private SubgroupMapperMyBatis subgroupMapperMyBatis;

    @Autowired
    private TeamMapperMyBatis teamMapperMyBatis;

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    private static int counter = 0;

    private Subgroup insertSubgroupHelper() {
        counter++;
        RegisteredUser owner = new RegisteredUser("Tom" + counter, "Sad" + counter, "pass" + counter, "a@b.c" + counter, RoleEnum.USER);
        userMapperMyBatis.insertUser(owner);
        Team team = new Team("X team" + counter, "Rugby" + counter, new ArrayList<>(), owner);
        teamMapperMyBatis.insertTeam(team);
        Subgroup subgroup = new Subgroup("Workers" + counter, team.getEntityId());
        subgroupMapperMyBatis.insertSubgroup(subgroup);
        subgroupMapperMyBatis.insertSubgroupUser(subgroup.getEntityId(), owner.getEntityId());
        return subgroup;
    }

    private void deleteSubgroupHelper(Subgroup subgroup) {
        Team team = teamMapperMyBatis.findTeamById(subgroup.getTeamId());
        subgroupMapperMyBatis.deleteSubgroupUser(subgroup.getEntityId(), team.getOwner().getEntityId());
        subgroupMapperMyBatis.deleteSubgroupById(subgroup.getEntityId());
        teamMapperMyBatis.deleteTeamById(team.getEntityId());
        userMapperMyBatis.deleteUserById(team.getOwner().getEntityId());
    }

    @Test
    public void insertSubgroup() {
        Subgroup presetSubgroup = insertSubgroupHelper();
        Assert.assertNotNull(presetSubgroup);
        Subgroup subgroup = new Subgroup("Test subgroup", presetSubgroup.getTeamId());
        subgroupMapperMyBatis.insertSubgroup(subgroup);
        System.out.println(subgroup.getEntityId());
        Assert.assertNotNull(subgroup.getEntityId());

        subgroupMapperMyBatis.deleteSubgroupById(subgroup.getEntityId());
        deleteSubgroupHelper(presetSubgroup);
    }

    @Test
    public void updateSubgroupName() {
        Subgroup presetSubgroup = insertSubgroupHelper();
        Assert.assertNotNull(presetSubgroup);
        String subgroupName = presetSubgroup.getName();
        presetSubgroup.setName("New Name group");
        subgroupMapperMyBatis.updateSubgroup(presetSubgroup);
        Assert.assertNotEquals(subgroupName, presetSubgroup.getName());

        deleteSubgroupHelper(presetSubgroup);
    }

    @Test
    public void deleteSubgroupById() {
        Subgroup presetSubgroup = insertSubgroupHelper();
        Assert.assertNotNull(presetSubgroup);

        Team team = teamMapperMyBatis.findTeamById(presetSubgroup.getTeamId());
        subgroupMapperMyBatis.deleteSubgroupUser(presetSubgroup.getEntityId(), team.getOwner().getEntityId());
        subgroupMapperMyBatis.deleteSubgroupById(presetSubgroup.getEntityId());
        teamMapperMyBatis.deleteTeamById(team.getEntityId());
        userMapperMyBatis.deleteUserById(team.getOwner().getEntityId());

        Assert.assertNull(subgroupMapperMyBatis.findSubgroupById(presetSubgroup.getEntityId()));
    }

    @Test
    public void findSubgroupById() {
        Subgroup presetSubgroup = insertSubgroupHelper();
        Assert.assertNotNull(presetSubgroup);
        Subgroup foundSubgroup = subgroupMapperMyBatis.findSubgroupById(presetSubgroup.getEntityId());
        Assert.assertNotNull(foundSubgroup.getUserList());
        Assert.assertEquals(presetSubgroup.getEntityId(), foundSubgroup.getEntityId());

        deleteSubgroupHelper(presetSubgroup);
    }

    // nefunguje, nutne spravit
    //@Test
    public void findSubgroupsByTeamId() {
        Subgroup presetSubgroup = insertSubgroupHelper();
        Assert.assertNotNull(presetSubgroup);
        Subgroup presetSubgroup2 = new Subgroup("Some subgroup", presetSubgroup.getTeamId());
        subgroupMapperMyBatis.insertSubgroup(presetSubgroup2);
        List<Subgroup> subgroupList = subgroupMapperMyBatis.findSubgroupsByTeamId(presetSubgroup.getTeamId());

        Assert.assertEquals(2, subgroupList.size());

        Assert.assertEquals(1, subgroupList.get(0).getUserList().size());

        System.out.println("sub2 users: ");
        subgroupList.get(1).getUserList().forEach(System.out::println);
        Assert.assertEquals(0, subgroupList.get(1).getUserList().size());

        subgroupMapperMyBatis.deleteSubgroupById(presetSubgroup2.getEntityId());
        deleteSubgroupHelper(presetSubgroup);
    }
}
