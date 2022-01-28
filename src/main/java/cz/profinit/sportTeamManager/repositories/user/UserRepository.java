package cz.profinit.sportTeamManager.repositories.user;

import cz.profinit.sportTeamManager.exceptions.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.Guest;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;

public interface UserRepository {

    /**
     * check if user already exists in db
     * @param registeredUser user to insert
     */
    void insertRegisteredUser(RegisteredUser registeredUser) throws EntityAlreadyExistsException;

    /**
     * find user, if found then update
     * @param registeredUser user to update
     * @return updated user or null
     * @throws EntityNotFoundException if registeredUser wasn't found
     */
    RegisteredUser updateRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException;

    /**
     * find user by email, if null then throw
     * @param email to find user by
     * @return found user
     * @throws EntityNotFoundException if user wasn't found
     */
    RegisteredUser findUserByEmail(String email) throws EntityNotFoundException;

    /**
     * find user by id, if null then throw
     * @param id to find user by
     * @return found user
     * @throws EntityNotFoundException if user wasn't found
     */
    RegisteredUser findUserById(Long id) throws EntityNotFoundException;


    /**
     * checks whether user has an id or deleteTeam it by email instead
     * @param userId id to find user to delete
     * @throws EntityNotFoundException user to be deleted wasn't found
     */
    void deleteRegisteredUser(Long userId) throws EntityNotFoundException;

    Guest insertGuest(Guest guest);
    Guest findGuestByUri(String uri) throws EntityNotFoundException;
    Guest updateGuest (Guest guest);
}
