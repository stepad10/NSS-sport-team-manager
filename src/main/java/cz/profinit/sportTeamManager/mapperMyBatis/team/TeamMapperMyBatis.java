package cz.profinit.sportTeamManager.mapperMyBatis.team;

import cz.profinit.sportTeamManager.model.team.Team;

import java.util.List;

public interface TeamMapperMyBatis {

    /**
     *
     * @param team to insert
     * @return inserted team or null
     */
    Team insertTeam(Team team);

    /**
     *
     * @param team to be updated
     * @return updated team or null
     */
    Team updateTeam(Team team);

    /**
     *
     * @param id to find team by
     * @return team or null
     */
    Team deleteTeamById(Long id);

    /**
     *
     * @param name to find teams by
     * @return found teams or null
     */
    List<Team> findTeamsByName(String name);

    /**
     *
     * @param id to find team by
     * @return found team or null
     */
    Team findTeamById(Long id);
}
