package cz.profinit.sportTeamManager.stubs.stubRepositories.subgroup;

import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.repositories.subgroup.SubgroupRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class StubSubgroupRepository implements SubgroupRepository {
    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));
    @Override
    public Subgroup insertSubgroup(Subgroup subgroup) {
        logger.info("STUB: Inserting subgroup in database");
        return subgroup;
    }

    @Override
    public Subgroup updateSubgroup(Subgroup subgroup) {
        logger.info("STUB: Updating subgroup in database");
        return subgroup;
    }

    @Override
    public Subgroup deleteSubgroup(Subgroup subgroup) {
        return null;
    }

    @Override
    public List<Subgroup> findSubgroupsByTeamID(Long teamId) {
        return null;
    }
}
