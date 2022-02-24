package cz.profinit.sportTeamManager.repository.subgroup;

import cz.profinit.sportTeamManager.exception.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class SubgroupRepositoryStub implements SubgroupRepository {

    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));

    private final Subgroup presetSubgroup = new Subgroup("All Users", 1L);

    @Override
    public void insertSubgroup(Subgroup subgroup) {
        logger.info("STUB: Inserting subgroup to database!");
    }

    @Override
    public void updateSubgroup(Subgroup subgroup) {
        logger.info("STUB: Updating subgroup in database!");
    }

    @Override
    public void deleteSubgroup(Subgroup subgroup) {
        logger.info("STUB: Deleting subgroup from database!");
    }

    @Override
    public void deleteAllTeamSubgroups(Team team) {
        logger.info("STUB: Deleting all team subgroups from database!");
    }

    @Override
    public Subgroup findTeamSubgroupByName(Team team, String subgroupName) throws EntityNotFoundException {
        logger.info("STUB: Finding subgroup of a team by name!");
        if (subgroupName.equals("Wrong subgroup")) throw new EntityNotFoundException("Subgroup");
        return presetSubgroup;
    }

    @Override
    public List<Subgroup> findTeamSubgroups(Team team) {
        logger.info("STUB: Finding all team subgroups in database!");
        return null;
    }
}
