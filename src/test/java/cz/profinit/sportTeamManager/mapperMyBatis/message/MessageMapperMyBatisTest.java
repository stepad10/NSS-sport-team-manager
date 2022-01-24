package cz.profinit.sportTeamManager.mapperMyBatis.message;

import cz.profinit.sportTeamManager.configuration.MyBatisConfigurationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@TestPropertySource("/test.properties")
public class MessageMapperMyBatisTest {

    @Test
    public void test(){}
}
