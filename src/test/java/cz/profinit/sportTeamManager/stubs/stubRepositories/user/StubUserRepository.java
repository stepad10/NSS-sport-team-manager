/*
 * StubUserRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.stubs.stubRepositories.user;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.Guest;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

/**
 * Stub user repository
 */
@Repository
public class StubUserRepository implements UserRepository {
    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));
    private RegisteredUser loggedUser1 = new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com", RoleEnum.USER);
    private RegisteredUser loggedUser2 = new RegisteredUser("Pavel", "Smutny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@seznam.cz", RoleEnum.USER);
    private RegisteredUser loggedUser3 = new RegisteredUser("Jirka", "Vesely", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@email.cz", RoleEnum.USER);
    private RegisteredUser loggedUser4 = new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
    private RegisteredUser loggedUser5 = new RegisteredUser("Adam", "Stastny", "2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "email@gmail.com", RoleEnum.USER);

    private Guest guest = new Guest("Karel","mxPR4fbWzvai60UMLhD3aw==");

    @Override
    public RegisteredUser insertRegisteredUser(RegisteredUser registeredUser) {
        logger.info("User saved to database");
        return registeredUser;
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
    public RegisteredUser updateRegisteredUser(RegisteredUser registeredUser) {
        logger.info("User saved to database");
        return registeredUser;
    }

    @Override
    public RegisteredUser findRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException {
        return null;
    }

    /**
     * Gets user email from database what is equal to "is@gmail.com"
     *
     * @param email user email
     * @return true only if email is "is@gmail.com"
     */
    @Override
    public boolean emailExistsInDatabase(String email) {
        return email.equals("is@gmail.com");
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
    public RegisteredUser deleteRegisteredUser(RegisteredUser registeredUser) {
        return null;
    }

    @Override
    public Guest insertGuest(Guest guest) {
        guest.setEntityId(0L);
        this.guest = guest;
        return this.guest;
    }

    @Override
    public Guest findGuestByUri(String uri) throws EntityNotFoundException {
        if (uri.equals("mxPR4fbWzvai60UMLhD3aw==")) {
            return guest;
        } else if (uri.equals("jsem_place_holder")) {
            return guest;
        } else {
            throw new EntityNotFoundException("Guest");
        }
    }

    @Override
    public Guest updateGuest(Guest guest)  {
        this.guest = guest;
        return guest;
    }

    public RegisteredUser findUserById(long id) {
        return null;
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
        if (userEmail == "is@seznam.cz") {
            return loggedUser2;
        } else if (userEmail.equals("is@gmail.com")) {
            return loggedUser1;
        } else if (userEmail.equals("is@email.cz")) {
            return loggedUser3;
        } else if (userEmail.equals("ts@gmail.com")) {
            return loggedUser4;
        } else if (userEmail.equals("email@gmail.com")) {
            return loggedUser5;
        } else {
            throw new EntityNotFoundException("User");
        }
    }
}
