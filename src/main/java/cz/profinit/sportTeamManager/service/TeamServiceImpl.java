/*
 * TeamServiceImpl
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.SubgroupRepository;
import cz.profinit.sportTeamManager.repositories.TeamRepository;
import cz.profinit.sportTeamManager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The service manipulating with team and subgroup.
 * This service contains methods that can create a new team,
 * add/delete subgroup to the team
 * and add/delete user to team or subgroup.
 */
@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private SubgroupRepository subgroupRepository;

    private final String allUsersSubgroupName = "All Users";
    private final String coachesSubgroupName = "Coaches";

    /**
     * Create a new team with entered name and sport owned by currently logged registered user.
     * Team is created with two default subgroups: "All users" and "Coaches". Owner is added to both subgroups.
     *
     * @param teamName name of created team
     * @param sport name of sport what team practice
     * @param owner creator of the team and its owner
     * @return newly created team with two default subgroups
     */
    public Team createNewTeam(String teamName, String sport, User owner) {
        List<Subgroup> subgroupList = new ArrayList<>();
        Team team = new Team(teamName, sport, subgroupList, owner);
        team.addNewSubgroup(allUsersSubgroupName);
        team.getListOfSubgroups().get(0).addUser(owner);
        team.addNewSubgroup(coachesSubgroupName);
        team.getListOfSubgroups().get(1).addUser(owner);
        teamRepository.updateTeam(team);

        return team;

    }

    /**
     * Calls a repository to found a team in database by team name.
     *
     * @param teamName name of search team
     * @return founded team of searched name
     */
    public Team getTeamByName(String teamName) {
        return teamRepository.findTeamByName(teamName);
    }


    /**
     * Adds a new user to the subgroup in certain team.
     * Checks if the added user is in the default subgroup "All users". If not, user is also added to this subgroup.
     * If the user is in the subgroup, throws Exception.
     *
     * @param team team where is a subgroup
     * @param subgroupName name of subgroup where we want to add
     * @param user user which we want to add to the subgroup
     * @return team with updated subgroup now including added user
     */
    public Team addUserToSubgroup(Team team, String subgroupName, User user) {
        List<Subgroup> subgroupList = team.getListOfSubgroups();
        Subgroup subgroup = team.getTeamSubgroup(subgroupName);

        if (!team.getTeamSubgroup(allUsersSubgroupName).isUserInList(user)) {
            team = addUserToTeam(team, user);
        }

        if (subgroup.isUserInList(user)) {
            throw new RuntimeException("user is already in subgroup");
        } else {
            team.getTeamSubgroup(subgroupName).addUser(user);
        }

        teamRepository.updateTeam(team);
        return team;
    }

    /**
     * Adds a user to the team by adding it to the default subgroup "All users".
     * Checks if the user is already in the subgroup. If the user is in the subgroup, throws Exception.
     *
     * @param team  team where user should be added
     * @param user user which should be added
     * @return updated team with added user
     */
    public Team addUserToTeam(Team team, User user) {
        if (team.getTeamSubgroup(allUsersSubgroupName).isUserInList(user)) {
            throw new RuntimeException("user is already in team");
        } else {
            team.getTeamSubgroup(allUsersSubgroupName).addUser(user);
            teamRepository.updateTeam(team);
        }
        return team;
    }

    /**
     * Adds a new subgroup of selected name to the determined team. Team is selected by its name.
     *
     * @param team team where the subgroup should be added
     * @param subgroupName name of the new subgroup
     * @return team with including added subgroup
     */
    public Team addSubgroup(Team team, String subgroupName) {
        team.addNewSubgroup(subgroupName);
        teamRepository.updateTeam(team);
        return team;
    }

    /**
     * Removes a subgroup of determined name from the team.
     *
     * @param team team name from which subgroup should be removed
     * @param subgroupName name of the subgroup what should be removed
     * @return team excluding removed subgroup
     */
    public Team deleteSubgroup(Team team, String subgroupName) {
        team.deleteSubgroup(subgroupName);
        teamRepository.updateTeam(team);
        return team;
    }

    /**
     * Removes a user from subgroup determined by name.
     *
     * @param team name of team containing a subgroup from which user should be removed
     * @param subgroupName name of the subgroup from which user should be removed
     * @param user user which should be removed
     * @return team with updated subgroup excluding a removed user
     */
    public Team deleteUserFromSubgroup(Team team, String subgroupName, User user) {
        team.getTeamSubgroup(subgroupName).removeUser(user);
        teamRepository.updateTeam(team);
        return team;
    }

    /**
     * Removes user form all subgroups of determined team.
     *
     * @param team team from which user should be removed
     * @param user user which should be removed
     * @return team excluding a removed user
     */
    public Team deleteUserFromTeam(Team team, User user) {
        List<Subgroup> subgroupList = team.getListOfSubgroups();

        for (Subgroup subgroup : subgroupList) {
            try {
                subgroup.removeUser(user);
            } catch (Exception e) {

            }
        }
        teamRepository.updateTeam(team);
        return team;
    }
}

