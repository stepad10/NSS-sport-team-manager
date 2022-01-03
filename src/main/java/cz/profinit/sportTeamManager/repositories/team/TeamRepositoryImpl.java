package cz.profinit.sportTeamManager.repositories.team;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@Profile("Main")
public class TeamRepositoryImpl implements TeamRepository {
    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));
    private final String allUsersSubgroupName = "All Users";
    private final String coachesSubgroupName = "Coaches";


    /**
     * Create stub team pulled from databese with owner user and 3 subgroups, 2 default and one Empty.
     * Gives logger info message.
     *
     * @param teamName team name is not used
     * @return created stub team
     */
    public Team findTeamByName(String teamName) {
        RegisteredUser owner = new RegisteredUser(
                "Ivan",
                "Stastny",
                "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                "sportteammanagertest@gmail.com",
                RoleEnum.USER);
        List<Subgroup> subgroupList = new ArrayList<>();
        Subgroup allUsersSubgroup = new Subgroup(allUsersSubgroupName);
        allUsersSubgroup.addUser(owner);
        Subgroup coachesSubgroup = new Subgroup(coachesSubgroupName);
        coachesSubgroup.addUser(owner);
        Subgroup emptySubgroup = new Subgroup("Empty subgroup");
        subgroupList.add(allUsersSubgroup);
        subgroupList.add(coachesSubgroup);
        subgroupList.add(emptySubgroup);
        Team team = new Team("B team", "sipky", subgroupList, owner);
        team.setEntityId(10L);
        return team;
    }

    /**
     * Simulate updating team in database.Gives logger info message.
     *
     * @param team team, not used
     */
    @Override
    public void updateTeam(Team team) {
        logger.info("STUB: Updating team");
    }

    /**
     * Virtually saves a team to database.
     *
     * @param team saving team
     * @return saved team
     */
    @Override
    public Team saveTeam(Team team) {
        logger.info("STUB: Saving team");
        return team;
    }

    /**
     * Virtually deletes team from database
     *
     * @param team deleting team
     */
    @Override
    public void delete(Team team) {
        logger.info("STUB: deleting team");
    }


    /**
     * Gets team from database virtually
     *
     * @param teamId team id
     * @return team
     */
    @Override
    public Team findTeamById(Long teamId) throws EntityNotFoundException {
        Team team = findTeamByName("B team");
        return team;
    }

}
