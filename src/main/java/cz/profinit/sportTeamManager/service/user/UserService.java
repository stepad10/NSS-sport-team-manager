/*
 * UserService
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.service.user;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;

/**
 * The UserService controls the creation of the new user and checks if given logging
 * credentials corresponds to any user in database.
 */
public interface UserService {
    RegisteredUser newUserRegistration(RegisteredUser newUser) throws EntityNotFoundException;

    RegisteredUser findUserByEmail(String email) throws EntityNotFoundException;

    //TODO
    RegisteredUser getLogedUser();

    RegisteredUser changeUserName(String email, String newName) throws EntityNotFoundException;
    RegisteredUser changeUserSurname(String email, String newSurname) throws EntityNotFoundException;
    RegisteredUser changeUserEmail(String email,String newEmail) throws EntityNotFoundException;
    RegisteredUser changeUserRole(String email, RoleEnum newRole) throws EntityNotFoundException;

}
