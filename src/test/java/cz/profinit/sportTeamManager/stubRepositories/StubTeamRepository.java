/*
 * StubTeamRepository
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.stubRepositories;

import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegistredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Stub Team repository for Unit testing.
 */
public class StubTeamRepository implements TeamRepository {
    private Logger logger = Logger.getLogger(String.valueOf(getClass()));
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
            User owner = new RegistredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
            List<Subgroup> subgroupList = new ArrayList<>();
            Subgroup allUsersSubgroup = new Subgroup(allUsersSubgroupName);
            allUsersSubgroup.addUser(owner);
            Subgroup coachesSubgroup = new Subgroup(coachesSubgroupName);
            coachesSubgroup.addUser(owner);
            Subgroup emptySubgroup = new Subgroup("Empty subgroup");
            subgroupList.add(allUsersSubgroup);
            subgroupList.add(coachesSubgroup);
            subgroupList.add(emptySubgroup);
            logger.info("STUB: Creating team");
            return new Team("B team", "sipky", subgroupList, owner);
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


}
