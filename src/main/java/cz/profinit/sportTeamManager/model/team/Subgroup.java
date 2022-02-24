/*
 * Subgroup
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.model.team;

import cz.profinit.sportTeamManager.exception.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.entity.Entity;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * Class containing subgroup information such as subgroup name and user list.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class Subgroup extends Entity {
    private String name;
    private List<RegisteredUser> userList;
    private Long teamId;


    /**
     * Sole class constructor.
     *
     * @param name name of the subgroup
     * @param teamId id of parent team
     */
    public Subgroup(String name, Long teamId) {
        this.name = name;
        this.teamId = teamId;
        this.userList = new ArrayList<>();
    }


    /**
     * Adds user to the user list.
     *
     * @param user user which should be added
     */
    public void addUser(RegisteredUser user) {
        userList.add(user);
    }

    /**
     * Removes user from a subgroup if the user is in subgroup, if not throws runtime exception.
     *
     * @param user user which should be removed
     */
    public void removeUser(RegisteredUser user) throws EntityNotFoundException {
        if (userList.contains(user)) {
            userList.remove(user);
        } else {
            throw new EntityNotFoundException("User");
        }
    }

    /**
     * find user in list by email
     * @param email to find user by
     * @return found user
     * @throws EntityNotFoundException if no user was found
     */
    public RegisteredUser getUser(String email) throws EntityNotFoundException {
        for (RegisteredUser regUs : userList) {
            if (regUs.getEmail().equals(email)) {
                return regUs;
            }
        }
        throw new EntityNotFoundException("User");
    }

    /**
     * Checks if user is in the list.
     *
     * @param user user which is looked for
     * @return true if user is in subgroup, false if not.
     */
    public boolean isUserInList(RegisteredUser user) {
        return userList.contains(user);
    }

}
