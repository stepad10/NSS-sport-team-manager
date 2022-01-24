package cz.profinit.sportTeamManager.mapperMyBatis.invitation;

import cz.profinit.sportTeamManager.configuration.MyBatisConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ContextConfiguration(classes = MyBatisConfiguration.class)
@TestPropertySource("/test.properties")
public class InvitationMapperMyBatisTest {

    @Test
    public void test(){}
}
