package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;

public interface UserRepository {

    /**
     * check if user already exists in db
     * @param registeredUser user to insert
     * @return output of insertion, user or null
     */
    RegisteredUser insertRegisteredUser(RegisteredUser registeredUser);

    /**
     *
     * @param registeredUser user to update
     * @return output of update, updated user or null
     */
    RegisteredUser updateRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException;

    /**
     * if user doesn't have id then it is not in db, and we will use email instead
     * @param registeredUser user to find
     * @return found user
     * @throws EntityNotFoundException if user wasn't found
     */
    RegisteredUser findRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException;

    /**
     * checks whether email exists in db
     * @param email email to find
     * @return whether email was found
     */
    boolean emailExistsInDatabase(String email);

    /**
     *
     * @param email param for finding user
     * @return found user
     * @throws EntityNotFoundException if user wasn't found
     */
    RegisteredUser findUserByEmail(String email) throws EntityNotFoundException;

    /**
     *
     * @param id param for finding user
     * @return found user
     * @throws EntityNotFoundException if user wasn't found
     */
    RegisteredUser findUserById(Long id) throws EntityNotFoundException;


    /**
     * checks whether user has an id or delete it by email instead
     * @param registeredUser user to delete
     * @return deleted user
     * @throws EntityNotFoundException user to be deleted wasn't found
     */
    RegisteredUser deleteRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException;
}
