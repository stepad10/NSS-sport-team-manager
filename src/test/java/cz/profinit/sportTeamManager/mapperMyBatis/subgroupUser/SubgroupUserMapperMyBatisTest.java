package cz.profinit.sportTeamManager.mapperMyBatis.subgroupUser;

import cz.profinit.sportTeamManager.configuration.MyBatisConfigurationTest;
import cz.profinit.sportTeamManager.mapperMyBatis.subgroup.SubgroupMapperMyBatis;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.team.Subgroup;
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

import java.util.List;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@TestPropertySource("/test.properties")
public class SubgroupUserMapperMyBatisTest {

    @Autowired
    private SubgroupUserMapperMyBatis subgroupUserMapperMyBatis;

    @Autowired
    private SubgroupMapperMyBatis subgroupMapperMyBatis;

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    @Test
    public void insertUserToSubgroup() {
        Long subgroupId = 5L;
        Long userId = 8L;
        Assert.assertEquals(0, subgroupUserMapperMyBatis.findUsersBySubgroupId(subgroupId).size());
        subgroupUserMapperMyBatis.insertSubgroupUser(subgroupId, userId);
        Assert.assertEquals(1, subgroupUserMapperMyBatis.findUsersBySubgroupId(subgroupId).size());
    }

    @Test
    public void deleteUserFromSubgroup() {
        Long subgroupId = 5L;
        Long userId = 9L;
        Assert.assertEquals(1, subgroupUserMapperMyBatis.findUsersBySubgroupId(subgroupId).size());
        subgroupUserMapperMyBatis.deleteSubgroupUser(subgroupId, userId);
        Assert.assertEquals(0, subgroupUserMapperMyBatis.findUsersBySubgroupId(subgroupId).size());
    }

    @Test
    public void findUsersBySubgroupId() {
        Long subgroupId = 6L;
        List<RegisteredUser> userList = subgroupUserMapperMyBatis.findUsersBySubgroupId(subgroupId);
        Assert.assertEquals(2, userList.size());
        userList.forEach(Assert::assertNotNull);
    }
}