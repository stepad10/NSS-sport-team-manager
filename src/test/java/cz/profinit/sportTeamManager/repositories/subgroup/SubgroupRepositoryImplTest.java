package cz.profinit.sportTeamManager.repositories.subgroup;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import cz.profinit.sportTeamManager.mapperMyBatis.team.TeamMapperMyBatis;

/**
 * Test class for team repository, autowired stub classes
 */
@RunWith(MockitoJUnitRunner.class)
public class SubgroupRepositoryImplTest {

    @InjectMocks
    private SubgroupRepositoryImpl teamRepository;

    @Mock
    private TeamMapperMyBatis teamMapperMyBatis;

    @Mock
    private SubgroupRepositoryImpl subgroupRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

}