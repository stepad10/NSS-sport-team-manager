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

public class StubTeamRepository implements TeamRepository {
    private Logger logger = Logger.getLogger(String.valueOf(getClass()));
    private final String allUsersSubgroupName = "All Users";
    private final String coachesSubgroupName = "Coaches";

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

    @Override
    public void updateTeam(Team team) {
        logger.info("STUB: Updating team");
    }


}
