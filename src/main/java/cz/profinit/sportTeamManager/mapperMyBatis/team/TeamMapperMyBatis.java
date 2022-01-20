package cz.profinit.sportTeamManager.mapperMyBatis.team;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Team;

import java.util.List;

public interface TeamMapperMyBatis {

    /**
     * insert Team to database and update it's entityId
     * @param team to insert
     */
    void insertTeam(Team team);

    /**
     *
     * @param team to be updated
     */
    void updateTeam(Team team);

    /**
     *
     * @param id to find Team by
     */
    void deleteTeamById(Long id);

    /**
     *
     * @param id to find Team by
     * @return found Team or null
     */
    Team findTeamById(Long id);

    /**
     *
     * @param name to find list of Team by
     * @return found list of Team or null
     */
    List<Team> findTeamsByName(String name);
}
