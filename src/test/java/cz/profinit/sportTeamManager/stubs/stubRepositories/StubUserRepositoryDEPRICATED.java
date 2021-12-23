/*
 * TODOMERGEStubUserRepository
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.stubs.stubRepositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.UserRepository;

import java.util.logging.Logger;

/**
 * Stub User repository for Unit testing.
 */

public class StubUserRepositoryDEPRICATED implements UserRepository {
    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));

    /**
     * Virtually save user to database
     *
     * @param registeredUser saving user
     * @return saved user
     */
    @Override
    public RegisteredUser saveRegistredUser(RegisteredUser registeredUser) {
        logger.info("User saved to database");
        return registeredUser;
    }

    /**
     * Gets user email from database what is equal to "ab@gmail.com"
     *
     * @param email user email
     * @return true only if email is "ab@gmail.com"
     */
    @Override
    public boolean emailExistsInDatabase(String email) {
        return email.equals("ab@gmail.com");
    }

    /**
     * Return predefined user
     *
     * @param id do not play any role
     * @return predefined user
     */
    @Override
    public RegisteredUser findUserById(long id) {
        return new RegisteredUser(
                "Ivan",
                "Stastny",
                "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                "is@gmail.com",
                RoleEnum.USER);
    }

    /**
     * Returns users found by email. Returns two possible users with email address "is@gmail.com" or "ts@gmail.com".
     * Other emails throws EntityNotFoundException.
     *
     * @param email user email
     * @return one of the possible predefined users
     * @throws EntityNotFoundException when email is not equeal to "is@gmail.com" or "ts@gmail.com"
     */
    @Override
    public RegisteredUser findUserByEmail(String email) throws EntityNotFoundException {
        if (email.equals("is@gmail.com")) {
            logger.info("Found user in database by email: " + email);
            return new RegisteredUser(
                    "Ivan",
                    "Stastny",
                    "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                    "is@gmail.com",
                    RoleEnum.USER);
        } else if (email.equals("ts@gmail.com")) {
            return new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
        } else if (email.equals("email@gmail.com")) {
            return new RegisteredUser("Adam", "Stastny", "2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "email@gmail.com", RoleEnum.USER);
        } else {
            logger.info("User with email: " + email + " is not in database");
            throw new EntityNotFoundException("User not found");
        }
    }


}
