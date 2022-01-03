/*
 * TeamServiceImpl
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.service.team;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.exceptions.UserIsInSubgroupException;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.repositories.TeamRepository;
import cz.profinit.sportTeamManager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service manipulating with team and subgroup.
 * This service contains methods that can create a new team,
 * add/delete subgroup to the team
 * and add/delete user to team or subgroup.
 */
@Service
@AllArgsConstructor
@Profile("Main")
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;


    private final String ALL_USER_SUBGROUP = "All Users";
    private final String COACHES_SUBGROUP = "Coaches";


    /**
     * Create a new team with entered name and sport owned by currently logged registered user.
     * Team is created with two default subgroups: "All users" and "Coaches". Owner is added to both subgroups.
     *
     * @param team name of created team
     * @return newly created team with two default subgroups
     */
    public Team createNewTeam(Team team) {
        team.addNewSubgroup(ALL_USER_SUBGROUP);
        team.getListOfSubgroups().get(0).addUser(team.getOwner());
        team.addNewSubgroup(COACHES_SUBGROUP);
        team.getListOfSubgroups().get(1).addUser(team.getOwner());
        teamRepository.saveTeam(team);

        return team;

    }

    /**
     * Change team name to a new one.
     *
     * @param teamId  team id which name should be changed
     * @param newName new name of a team
     */
    public Team changeTeamName(Long teamId, String newName) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        team.setName(newName);
        teamRepository.updateTeam(team);
        return team;
    }


    /**
     * Change team sport to a new one.
     *
     * @param teamId   Id of team for which sport should be changed
     * @param newSport new sport of a team
     */
    public Team changeTeamSport(Long teamId, String newSport) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        team.setSport(newSport);
        teamRepository.updateTeam(team);
        return team;
    }

    /**
     * Change team owner to a new one. Owner must be from All Users subgroup.
     *
     * @param teamId Id of team what name should be changed
     * @param user   new owner of a team
     * @throws EntityNotFoundException if user is not found
     * @throws RuntimeException        if user is not in All Users subgroup
     */
    public Team changeTeamOwner(Long teamId, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (!team.getTeamSubgroup(ALL_USER_SUBGROUP).isUserInList(user)) {
            throw new RuntimeException("User is not in team");
        } else if (!team.getTeamSubgroup(COACHES_SUBGROUP).isUserInList(user)) {
            team = addUserToSubgroup(team.getEntityId(), COACHES_SUBGROUP, user);
        }
        team.setOwner(user);
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
     * @param teamId       Id of team where is a subgroup
     * @param subgroupName name of subgroup where we want to add
     * @param user         user which we want to add to the subgroup
     * @return team with updated subgroup now including added user
     * @throws EntityNotFoundException   when user is not found
     * @throws UserIsInSubgroupException when user is already in subgroup
     */
    public Team addUserToSubgroup(Long teamId, String subgroupName, RegisteredUser user) throws UserIsInSubgroupException, EntityNotFoundException {
        Team team = getTeamById(teamId);
        user = userRepository.findUserByEmail(user.getEmail());
        List<Subgroup> subgroupList = team.getListOfSubgroups();
        Subgroup subgroup = team.getTeamSubgroup(subgroupName);

        if (!team.getTeamSubgroup(ALL_USER_SUBGROUP).isUserInList(user)) {
            team = addUserToTeam(team.getEntityId(), user);
        }

        if (subgroup.isUserInList(user)) {
            throw new UserIsInSubgroupException("User is already in subgroup");
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
     * @param teamId Id of team where user should be added
     * @param user   user which should be added
     * @return updated team with added user
     * @throws EntityNotFoundException when user is not found
     */
    public Team addUserToTeam(Long teamId, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        user = userRepository.findUserByEmail(user.getEmail());
        if (team.getTeamSubgroup(ALL_USER_SUBGROUP).isUserInList(user)) {
            throw new RuntimeException("User is already in team");
        } else {
            team.getTeamSubgroup(ALL_USER_SUBGROUP).addUser(user);
            teamRepository.updateTeam(team);
        }
        return team;
    }

    /**
     * Adds a new subgroup of selected name to the determined team. Team is selected by its name.
     *
     * @param teamId       Id of team where the subgroup should be added
     * @param subgroupName name of the new subgroup
     * @return team with including added subgroup
     */
    public Team addSubgroup(Long teamId, String subgroupName) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (team.isSubgroupInTeam(subgroupName)) {
            throw new RuntimeException("Subgroup already exists");
        } else {
            team.addNewSubgroup(subgroupName);
            teamRepository.updateTeam(team);
        }

        return team;
    }

    /**
     * Removes a subgroup of determined name from the team.
     *
     * @param teamId       Id of team from which subgroup should be removed
     * @param subgroupName name of the subgroup what should be removed
     * @return team excluding removed subgroup
     * @throws EntityNotFoundException when subgroup is not found
     */
    public Team deleteSubgroup(Long teamId, String subgroupName) throws EntityNotFoundException {
        Team team = getTeamById(teamId);

        if (team.isSubgroupInTeam(subgroupName)) {
            team.deleteSubgroup(subgroupName);
            teamRepository.updateTeam(team);
        } else {
            throw new EntityNotFoundException("Subgroup");
        }

        return team;
    }

    /**
     * Removes a user from subgroup determined by name.
     *
     * @param teamId       Id of team containing a subgroup from which user should be removed
     * @param subgroupName name of the subgroup from which user should be removed
     * @param user         user which should be removed
     * @return team with updated subgroup excluding a removed user
     * @throws EntityNotFoundException when user is not found
     */
    public Team deleteUserFromSubgroup(Long teamId, String subgroupName, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        user = userRepository.findUserByEmail(user.getEmail());
        team.getTeamSubgroup(subgroupName).removeUser(user);
        teamRepository.updateTeam(team);
        return team;
    }

    /**
     * Removes user form all subgroups of determined team.
     *
     * @param teamId Id of team from which user should be removed
     * @param user   user which should be removed
     * @return team excluding a removed user
     * @throws EntityNotFoundException when user is not found
     */
    public Team deleteUserFromTeam(Long teamId, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        user = userRepository.findUserByEmail(user.getEmail());
        try {
            team.getTeamSubgroup(ALL_USER_SUBGROUP).removeUser(user);
        } catch (Exception e) {
            throw new EntityNotFoundException("User");
        }
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

    /**
     * Removes team from database
     *
     * @param teamId Id of team which we want to be removed
     */
    public void deleteTeam(Long teamId) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        teamRepository.delete(team);
    }

    /**
     * Changes name of the subgroup to a new one. Checks if subgroup exists and if a new selected name
     * do not coincide with already existing subgroup.
     *
     * @param teamId       Id of team where subgroup is
     * @param subgroupName name of subgroup
     * @param newName      new name of subgroup
     * @return team with updated subgroup
     * @throws EntityNotFoundException when subgroup is not found
     */
    public Team changeSubgroupName(Long teamId, String subgroupName, String newName) throws EntityNotFoundException {
        Team team = getTeamById(teamId);

        if (team.isSubgroupInTeam(newName)) {
            throw new RuntimeException("Subgroup already exists");
        } else if ((!team.isSubgroupInTeam(subgroupName))) {
            throw new EntityNotFoundException("Subgroup");
        } else {
            team.getTeamSubgroup(subgroupName).setName(newName);
            teamRepository.updateTeam(team);
        }

        return team;
    }


    /**
     * Gets team from repository
     *
     * @param teamId Id of team which we want to get
     * @return retrieved team
     */
    public Team getTeamById(Long teamId) throws EntityNotFoundException {
        Team team = null;
        team = teamRepository.findTeamById(teamId);
        return team;
    }
}

