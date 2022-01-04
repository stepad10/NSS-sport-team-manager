/*
 * StubTeamRepository
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.stubs.stubRepositories.team;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.team.TeamRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Stub Team repository for Unit testing.
 */

@Repository
public class StubTeamRepository implements TeamRepository {
    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));

    /**
     * Create stub team pulled from databese with owner user and 3 subgroups, 2 default and one Empty.
     * Gives logger info message.
     *
     * @param teamName team name is not used
     * @return created stub team
     */
    public List<Team> findTeamsByName(String teamName) throws EntityNotFoundException {
        // TODO test return list
        return new ArrayList<>();
    }

    /**
     * Simulate updating team in database.Gives logger info message.
     *
     * @param team team, not used
     * @return team
     */
    @Override
    public Team updateTeam(Team team) {
        logger.info("STUB: Updating team");
        return team;
    }

    /**
     * Virtually saves a team to database.
     *
     * @param team saving team
     * @return saved team
     */
    @Override
    public Team insertTeam(Team team) {
        logger.info("STUB: Saving team");
        return team;
    }

    /**
     * Virtually deletes team from database
     *
     * @param team deleting team
     */
    @Override
    public Team deleteTeam(Team team) {
        logger.info("STUB: deleting team");
        return team;
    }


    /**
     * Gets team from database virtually
     *
     * @param teamId team id
     * @return team
     */
    @Override
    public Team findTeamById(Long teamId) throws EntityNotFoundException {
        if (teamId != 10L) throw new EntityNotFoundException("Team");
        RegisteredUser owner = new RegisteredUser(
                "Ivan",
                "Stastny",
                "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                "is@gmail.com",
                RoleEnum.USER);
        List<Subgroup> subgroupList = new ArrayList<>();
        Team team = new Team("B team", "sipky", subgroupList, owner);
        team.setEntityId(10L);
        Subgroup allUsersSubgroup = new Subgroup("All Users", team.getEntityId());
        allUsersSubgroup.addUser(owner);
        Subgroup coachesSubgroup = new Subgroup("Coaches", team.getEntityId());
        coachesSubgroup.addUser(owner);
        Subgroup emptySubgroup = new Subgroup("Empty subgroup", team.getEntityId());
        subgroupList.add(allUsersSubgroup);
        subgroupList.add(coachesSubgroup);
        subgroupList.add(emptySubgroup);
        team.setListOfSubgroups(subgroupList);
        return team;
    }


}
