package cz.profinit.sportTeamManager.repositories.team;

import cz.profinit.sportTeamManager.configuration.MapperMyBatisConfigurationStub;
import cz.profinit.sportTeamManager.configuration.StubRepositoryConfiguration;
import cz.profinit.sportTeamManager.mapperMyBatis.team.TeamMapperMyBatis;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for team repository, autowired stub classes
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MapperMyBatisConfigurationStub.class, StubRepositoryConfiguration.class})
@ActiveProfiles({"mapper_mybatis_stub", "stub_repository"})
public class TeamRepositoryImplTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMapperMyBatis teamMapperMyBatis;

    @Test
    public void insertNullTeam() {

    }
}
