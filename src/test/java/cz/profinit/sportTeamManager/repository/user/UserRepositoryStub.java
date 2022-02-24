/*
 * StubUserRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.repository.user;

import cz.profinit.sportTeamManager.exception.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.Guest;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;

import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

/**
 * Stub user repository
 */
@Repository
public class UserRepositoryStub implements UserRepository {
    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));
    public static RegisteredUser loggedUser1 = new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com", RoleEnum.USER);
    private final RegisteredUser loggedUser2 = new RegisteredUser("Pavel", "Smutny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@seznam.cz", RoleEnum.USER);
    private final RegisteredUser loggedUser3 = new RegisteredUser("Jirka", "Vesely", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@email.cz", RoleEnum.USER);
    private final RegisteredUser loggedUser4 = new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
    public static RegisteredUser loggedUser5 = new RegisteredUser("Adam", "Stastny", "2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "email@gmail.com", RoleEnum.USER);

    @Override
    public void insertRegisteredUser(RegisteredUser registeredUser) {
        logger.info("User saved to database");
        registeredUser.setEntityId(10L);
    }

    /**
     * Virtually save user to database
     *
     * @param registeredUser saving user
     * @return saved user
     */
    public RegisteredUser createRegistredUser(RegisteredUser registeredUser) {
        logger.info("User saved to database");
        return registeredUser;
    }

    @Override
    public void updateRegisteredUser(RegisteredUser registeredUser) {
        logger.info("User saved to database");
    }

    @Override
    public RegisteredUser findUserById(Long id) throws EntityNotFoundException {
        return new RegisteredUser(
                "Ivan",
                "Stastny",
                "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                "is@gmail.com",
                RoleEnum.USER);
    }

    @Override
    public void deleteRegisteredUser(Long userId) {

    }

    /**
     * Returns users found by email. Returns two possible users with email address from loggedUser1 up to loggedUser5.
     * Other emails throws EntityNotFoundException.
     *
     * @param userEmail user email
     * @return one of the possible predefined users
     * @throws EntityNotFoundException when email is not equeal to any loggedUser
     */
    @Override
    public RegisteredUser findUserByEmail(String userEmail) throws EntityNotFoundException {
        switch (userEmail) {
        case "is@gmail.com":
            return loggedUser1;
        case "is@seznam.cz":
            return loggedUser2;
        case "is@email.cz":
            return loggedUser3;
        case "ts@gmail.com":
            return loggedUser4;
        case "email@gmail.com":
            return loggedUser5;
        default:
            throw new EntityNotFoundException("User");
        }
    }

    //TODO IMPLEMENT THESE
    @Override
    public Guest insertGuest(Guest guest) {
        return null;
    }

    @Override
    public Guest findGuestByUri(String uri) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Guest updateGuest(Guest guest) {
        return null;
    }
}
