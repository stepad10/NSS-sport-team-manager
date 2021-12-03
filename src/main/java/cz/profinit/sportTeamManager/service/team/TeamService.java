/*
 * Team service
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.service.team;


import cz.profinit.sportTeamManager.dto.TeamDto;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.User;

/**
 * Team service interface.
 */
public interface TeamService {
    /**
     * Create a new team with entered name and sport owned by currently logged registered user.
     * Team is created with two default subgroups: "All users" and "Coaches". Owner is added to both subgroups.
     *
     * @param teamDto name of created team
     * @return newly created team with two default subgroups
     */
    Team createNewTeam(TeamDto teamDto);

    /**
     * Calls a repository to found a team in database by team name.
     *
     * @param teamName name of search team
     * @return founded team of searched name
     */
    Team getTeamByName(String teamName);

    /**
     * Adds a new user to the subgroup in certain team.
     * Checks if the added user is in the default subgroup "All users". If not, user is also added to this subgroup.
     * If the user is in the subgroup, throws Exception.
     *
     * @param team         team where is a subgroup
     * @param subgroupName name of subgroup where we want to add
     * @param user         user which we want to add to the subgroup
     * @return team with updated subgroup now including added user
     */
    Team addUserToSubgroup(Team team, String subgroupName, User user);

    /**
     * Adds a user to the team by adding it to the default subgroup "All users".
     * Checks if the user is already in the subgroup. If the user is in the subgroup, throws Exception.
     *
     * @param team team where user should be added
     * @param user user which should be added
     * @return updated team with added user
     */
    Team addUserToTeam(Team team, User user);

    /**
     * Adds a new subgroup of selected name to the determined team. Team is selected by its name.
     *
     * @param team         team where the subgroup should be added
     * @param subgroupName name of the new subgroup
     * @return team with including added subgroup
     */
    Team addSubgroup(Team team, String subgroupName);

    /**
     * Removes a subgroup of determined name from the team.
     *
     * @param team         team name from which subgroup should be removed
     * @param subgroupName name of the subgroup what should be removed
     * @return team excluding removed subgroup
     */
    Team deleteSubgroup(Team team, String subgroupName);

    /**
     * Removes a user from subgroup determined by name.
     *
     * @param team         name of team containing a subgroup from which user should be removed
     * @param subgroupName name of the subgroup from which user should be removed
     * @param user         user which should be removed
     * @return team with updated subgroup excluding a removed user
     */
    Team deleteUserFromSubgroup(Team team, String subgroupName, User user);

    /**
     * Removes user form all subgroups of determined team.
     *
     * @param team team from which user should be removed
     * @param user user which should be removed
     * @return team excluding a removed user
     */
    Team deleteUserFromTeam(Team team, User user);


}
