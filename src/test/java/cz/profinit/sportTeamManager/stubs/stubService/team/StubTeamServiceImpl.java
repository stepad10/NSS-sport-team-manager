/*
 * StubTeamServiceImpl
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.stubs.stubService.team;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mappers.TeamMapper;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.repositories.user.UserRepository;
import cz.profinit.sportTeamManager.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Stub Team service for Unit tests.
 */
@Service
public class StubTeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserRepository userRepository;
    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));
    private final String ALL_USER_SUBGROUP = "All Users";
    private final String COACHES_SUBGROUP = "Coaches";


    /**
     * Creates a team
     *
     * @param team name of created team
     * @return created team
     */
    public Team createNewTeam(Team team) {
        team.addNewSubgroup(ALL_USER_SUBGROUP);
        team.getListOfSubgroups().get(0).addUser(team.getOwner());
        team.addNewSubgroup(COACHES_SUBGROUP);
        team.getListOfSubgroups().get(1).addUser(team.getOwner());
        team.setEntityId(10L);
        logger.info("Saving team");

        return team;
    }


    /**
     * Changes a team name
     *
     * @param teamId  team what name should be changed
     * @param newName new name of a team
     * @return team with changed team name
     */
    @Override
    public Team changeTeamName(Long teamId, String newName) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        team.setName(newName);
        return team;
    }

    /**
     * Changes a team sport
     *
     * @param teamId   team what sport should be changed
     * @param newSport new sport of a team
     * @return team with changed sport
     */
    @Override
    public Team changeTeamSport(Long teamId, String newSport) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        team.setSport(newSport);
        return team;
    }

    /**
     * Change a team owner. When owner is not in All Users subgroup, throws an Exception.
     * If user is not in Coach subgroup, it is added.
     *
     * @param teamId team what name should be changed
     * @param user   new owner of a team
     * @return team with changed owner
     */
    @Override
    public Team changeTeamOwner(Long teamId, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (!team.getTeamSubgroup(ALL_USER_SUBGROUP).isUserInList(user)) {
            throw new RuntimeException("User is not in team");
        }
        try {
            team.getTeamSubgroup(COACHES_SUBGROUP).getUserList().get(1).getEmail().equals(user.getEmail());
        } catch (Exception e) {
            team.getTeamSubgroup(COACHES_SUBGROUP).addUser(user);
        }
        team.setOwner(user);
        return team;
    }

    /**
     * Gets two possible STUB teams. One with id 10 and one with 20.
     * Team with id 20 is similar to the team with 10 and have only added one additional user in both subgroups.
     *
     * @param teamId Id of team which we want to get
     * @return Team
     */
    public Team getTeamById(Long teamId) throws EntityNotFoundException {
        RegisteredUser user = userRepository.findUserByEmail("email@gmail.com");

        List<RegisteredUser> userList1 = new ArrayList<>();
        userList1.add(user);
        List<RegisteredUser> userList2 = new ArrayList<>();
        userList2.add(user);
        List<Subgroup> subgroupList = new ArrayList<>();
        Team team = new Team("Ateam", "golf", subgroupList, user);
        Subgroup subgroupA = new Subgroup("All Users", team);
        Subgroup subgroupC = new Subgroup("Coaches", team);
        subgroupA.setUserList(userList1);
        subgroupC.setUserList(userList2);
        subgroupList.add(subgroupA);
        subgroupList.add(subgroupC);
        subgroupList.add(new Subgroup("Empty", team));
        team.setListOfSubgroups(subgroupList);

        if (teamId == 10L) {
            team.setEntityId(10L);
        } else if (teamId == 20L) {
            team.setEntityId(20L);
            RegisteredUser newMenber = userRepository.findUserByEmail("ts@gmail.com");
            team.getTeamSubgroup("All Users").addUser(newMenber);
            team.getTeamSubgroup("Coaches").addUser(newMenber);
        } else {
            throw new EntityNotFoundException("Team");
        }
        return team;
    }


    /**
     * Adds user to the subgroup. If user email is equal to "email@gmail.com", throws an Exception.
     *
     * @param teamId       team where is a subgroup
     * @param subgroupName name of subgroup where we want to add
     * @param user         user which we want to add to the subgroup
     * @return updated team
     */
    @Override
    public Team addUserToSubgroup(Long teamId, String subgroupName, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (user.getEmail().equals("email@gmail.com")) {
            throw new RuntimeException("User is already in subgroup");
        }
        team.getListOfSubgroups().get(1).getUserList().add(user);
        team.getListOfSubgroups().get(0).getUserList().add(user);
        return team;
    }

    /**
     * Adds user to the team. If user email is equal to "email@gmail.com", throws an Exception.
     *
     * @param teamId team where user should be added
     * @param user   user which should be added
     * @return updated team
     */
    @Override
    public Team addUserToTeam(Long teamId, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (user.getEmail().equals("email@gmail.com")) {
            throw new RuntimeException("User is already in team");
        }
        team.getListOfSubgroups().get(0).getUserList().add(user);
        return team;
    }

    /**
     * Adds subgroup to the team. If subgroup already exists, throws an Exception.
     *
     * @param teamId       team where the subgroup should be added
     * @param subgroupName name of the new subgroup
     * @return updated team
     */
    @Override
    public Team addSubgroup(Long teamId, String subgroupName) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (team.isSubgroupInTeam(subgroupName)) {
            throw new RuntimeException("Subgroup already exists");
        } else {
            team.addNewSubgroup(subgroupName);
        }
        return team;
    }

    /**
     * Delete a subgroup from a team. If subgroup does not exist, throws an Exception.
     *
     * @param teamId       team id from which subgroup should be removed
     * @param subgroupName name of the subgroup what should be removed
     * @return updated team
     * @throws EntityNotFoundException
     */
    @Override
    public Team deleteSubgroup(Long teamId, String subgroupName) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (team.isSubgroupInTeam(subgroupName)) {
            team.getListOfSubgroups().remove(1);
        } else {
            throw new EntityNotFoundException("Subgroup");
        }
        return team;
    }

    /**
     * Deletes user from a subgroup specified by its name. If subgroup is not in team, throws EntityNotFoundException.
     *
     * @param teamId       name of id containing a subgroup from which user should be removed
     * @param subgroupName name of the subgroup from which user should be removed
     * @param user         user which should be removed
     * @return updated team
     * @throws EntityNotFoundException thrown when team do not contain selected subgroup
     */
    @Override
    public Team deleteUserFromSubgroup(Long teamId, String subgroupName, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (!team.isSubgroupInTeam(subgroupName)) {
            throw new EntityNotFoundException("Subgroup");
        }
        team.getTeamSubgroup(subgroupName).removeUser(user);
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
    @Override
    public Team deleteUserFromTeam(Long teamId, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        try {
            team.getTeamSubgroup("All Users").removeUser(user);
        } catch (Exception e) {
            throw new RuntimeException("User is not in team");
        }
        try {
            team.getTeamSubgroup("Coaches").removeUser(user);
        } catch (Exception e) {
            logger.info("User is not in Coach subgroup");
        }
        return team;
    }


    /**
     * Removes team from database
     *
     * @param teamId Id of team which we want to be removed
     */
    @Override
    public void deleteTeam(Long teamId) {
        logger.info("Team successfully deleted");
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
    @Override
    public Team changeSubgroupName(Long teamId, String subgroupName, String newName) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (team.isSubgroupInTeam(newName)) {
            throw new RuntimeException("Subgroup already exists");
        } else if (!team.isSubgroupInTeam(subgroupName)) {
            throw new EntityNotFoundException("Subgroup");
        } else {
            team.getTeamSubgroup(subgroupName).setName(newName);
        }
        return team;
    }


}
