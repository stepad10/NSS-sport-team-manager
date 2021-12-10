/*
 * StubTeamServiceImpl
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.stubs.stubService;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mappers.TeamMapper;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Stub Team service for Unit tests.
 */
@Service
@Profile("stub")
public class StubTeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    private Logger logger = Logger.getLogger(String.valueOf(getClass()));
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
    public Team changeTeamName(Long teamId, String newName) {
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
    public Team changeTeamSport(Long teamId, String newSport) {
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
    public Team changeTeamOwner(Long teamId, RegisteredUser user) {
        Team team = getTeamById(teamId);
        try {
            team.getTeamSubgroup(ALL_USER_SUBGROUP).getUserList().get(1).getEmail().equals(user.getEmail());
        } catch (Exception e) {

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
    @Override
    public Team getTeamById(Long teamId) {
        RegisteredUser user = new RegisteredUser("Adam","Stastny","pass","email@gmail.com",RoleEnum.USER);

        List<RegisteredUser> userList1 = new ArrayList<>();
        userList1.add(user);
        List<RegisteredUser> userList2 = new ArrayList<>();
        userList2.add(user);
        Subgroup subgroupA = new Subgroup("All Users");
        Subgroup subgroupC = new Subgroup("Coaches");
        subgroupA.setUserList(userList1);
        subgroupC.setUserList(userList2);
        List<Subgroup> subgroupList = new ArrayList<>();
        subgroupList.add(subgroupA);
        subgroupList.add(subgroupC);
        Team team = new Team("Ateam","golf",subgroupList,user);

        if (teamId == 10L) {
            team.setEntityId(10L);
        } else if (teamId == 20L) {
            team.setEntityId(20L);
            RegisteredUser newMenber = new RegisteredUser("Tomas", "Smutny","pass", "ts@gmail.com", RoleEnum.USER);
            team.getTeamSubgroup("All Users").addUser(newMenber);
            team.getTeamSubgroup("Coaches").addUser(newMenber);
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
    public Team addUserToSubgroup(Long teamId, String subgroupName, RegisteredUser user) {
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
    public Team addUserToTeam(Long teamId, RegisteredUser user) {
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
    public Team addSubgroup(Long teamId, String subgroupName) {
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
            throw new EntityNotFoundException("No subgroup found");
        }
        return team;
    }

    /**
     * @param teamId       name of id containing a subgroup from which user should be removed
     * @param subgroupName name of the subgroup from which user should be removed
     * @param user         user which should be removed
     * @return updated team
     * @throws EntityNotFoundException
     */
    @Override
    public Team deleteUserFromSubgroup(Long teamId, String subgroupName, RegisteredUser user) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        team.getTeamSubgroup(subgroupName).removeUser(user);
        return team;
    }


    /**
     * @param teamId team from which user should be removed
     * @param user   user which should be removed
     * @return updated team
     */
    @Override
    public Team deleteUserFromTeam(Long teamId, RegisteredUser user) {
        Team team = getTeamById(teamId);
        team.getTeamSubgroup("All Users").removeUser(user);
        team.getTeamSubgroup("Coaches").removeUser(user);
        return team;
    }


    /**
     * @param teamId team which should be removed
     */
    @Override
    public void deleteTeam(Long teamId) {
        logger.info("Team successfully deleted");
    }

    /**
     * @param teamId       team where subgroup is
     * @param subgroupName name of subgroup
     * @param newName      new name of subgroup
     * @return updated team
     * @throws EntityNotFoundException
     */
    @Override
    public Team changeSubgroupName(Long teamId, String subgroupName, String newName) throws EntityNotFoundException {
        Team team = getTeamById(teamId);
        if (team.isSubgroupInTeam(newName)) {
            throw new RuntimeException("Subgroup of new name already exists");
        } else if (!team.isSubgroupInTeam(subgroupName)) {
            throw new EntityNotFoundException("No subgroup found");
        } else {
            team.getTeamSubgroup(subgroupName).setName(newName);
        }
        return team;
    }


}
