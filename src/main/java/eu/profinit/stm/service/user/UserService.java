/*
 * UserService
 *
 * 0.1
 *
 * Author: J. Janský
 */
package eu.profinit.stm.service.user;

import eu.profinit.stm.exception.EntityAlreadyExistsException;
import eu.profinit.stm.exception.EntityNotFoundException;
import eu.profinit.stm.model.user.Guest;
import eu.profinit.stm.model.user.RoleEnum;
import eu.profinit.stm.model.user.User;

/**
 * The UserService controls the creation of the new user and checks if given logging
 * credentials corresponds to any user in database.
 */
public interface UserService {
    User newUserRegistration(User newUser) throws EntityNotFoundException, EntityAlreadyExistsException;

    User findUserByEmail(String email) throws EntityNotFoundException;

    User changeUserName(String email, String newName) throws EntityNotFoundException;
    User changeUserSurname(String email, String newSurname) throws EntityNotFoundException;
    User changeUserEmail(String email, String newEmail) throws EntityNotFoundException;
    User changeUserRole(String email, RoleEnum newRole) throws EntityNotFoundException;

    Guest createNewGuest(String name, Long eventId) throws EntityNotFoundException;
    Guest findGuestByUri(String uri) throws EntityNotFoundException;

}
