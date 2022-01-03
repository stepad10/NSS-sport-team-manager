/*
 * StubUserRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 *
 * 0.2
 *
 * Author: D. Stepanek
 */
package cz.profinit.sportTeamManager.stubs.stubRepositories.user;

import cz.profinit.sportTeamManager.exceptions.EmailExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
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
    private final String emailSuffix = ".cz";
    private final String[] emails = {"a@seznam", "b@email", "c@gmail", "d@post", "e@stream", "f@tiscali"};
    private final String password = "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2";
    private final RegisteredUser loggedUser1 = new RegisteredUser("Ivan", "Stastny", password, emails[0] + emailSuffix, RoleEnum.USER);
    private final RegisteredUser loggedUser2 = new RegisteredUser("Pavel", "Smutny", password, emails[1] + emailSuffix, RoleEnum.USER);
    private final RegisteredUser loggedUser3 = new RegisteredUser("Jirka", "Vesely", password, emails[2] + emailSuffix, RoleEnum.USER);
    private final RegisteredUser loggedUser4 = new RegisteredUser("Tomas", "Smutny", password, emails[3] + emailSuffix, RoleEnum.USER);
    private final RegisteredUser loggedUser5 = new RegisteredUser("Adam", "Stastny", password, emails[4] + emailSuffix, RoleEnum.USER);

    /**
     * if registeredUser email contains ".cz" then throw EmailExistsException
     * @param registeredUser user to insert
     * @return registeredUser
     */
    @Override
    public RegisteredUser insertRegisteredUser(RegisteredUser registeredUser) {
        logger.info("User inserted! Stub");
        if (registeredUser.getEmail().contains(emailSuffix))
        {
            logger.info("User not inserted! Stub");
            throw new EmailExistsException("Unable to insert RegisteredUser, email already exists!");
        }
        return registeredUser;
    }

    /**
     * update registeredUser
     * @param registeredUser user to update
     * @return registeredUser
     */
    @Override
    public RegisteredUser updateRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException {
        if (registeredUser.getEmail().contains(emailSuffix)) {
            logger.info("User updated! Stub");
            return new RegisteredUser("NewName", registeredUser.getSurname(), registeredUser.getPassword(), registeredUser.getEmail(), registeredUser.getRole());
        }
        logger.info("User not updated! Stub");
        throw new EntityNotFoundException("User");
    }

    /**
     * Returns user if email has a suffix ".cz", else throw EntityNotFoundException
     * @param registeredUser user to find
     * @return registeredUser
     * @throws EntityNotFoundException if email  doesn't contain suffix
     */
    @Override
    public RegisteredUser findRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException {
        if (registeredUser.getEmail().contains(emailSuffix)) {
            logger.info("User found! Stub");
            return registeredUser;
        }
        logger.info("User not found! Stub");
        throw new EntityNotFoundException("User");
    }

    /**
     * Gets user email from database
     * @param email user email
     * @return true if email is contains ".cz"
     */
    @Override
    public boolean emailExistsInDatabase(String email) {
        return email.contains(emailSuffix);
    }

    /**
     * if id is not 10 then return new user
     * @param id param for finding user
     * @return new RegisteredUser
     * @throws EntityNotFoundException if id is not equal to 10
     */
    @Override
    public RegisteredUser findUserById(Long id) throws EntityNotFoundException {
        if (id != 10L) {
            logger.info("User not found by id! Stub");
            throw new EntityNotFoundException("User");
        }

        RegisteredUser regUs = new RegisteredUser(
                "Ivan",
                "Stastny",
                password,
                emails[5] + emailSuffix,
                RoleEnum.USER);
        regUs.setEntityId(10L);
        logger.info("User found by id! Stub");
        return regUs;
    }

    /**
     * returns user found by email or throw
     * @param userEmail user email
     * @return found user
     * @throws EntityNotFoundException if userEmail doesn't contain emailSuffix
     */
    @Override
    public RegisteredUser findUserByEmail(String userEmail) throws EntityNotFoundException {
        if (userEmail.contains(emailSuffix)) {
            logger.info("User found by email! Stub");
            return loggedUser1;
        }
        logger.info("User not found by email! Stub");
        throw new EntityNotFoundException("User");
    }

    /**
     * if email doesn't contain suffix throw exception, else return registeredUser
     * @param registeredUser user to delete
     * @return registeredUser
     * @throws EntityNotFoundException if email doesn't contain suffix
     */
    @Override
    public RegisteredUser deleteRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException {
        if (registeredUser.getEmail().contains(emailSuffix)) {
            logger.info("User deleted! Stub");
            return registeredUser;
        }
        logger.info("User not deleted! Stub");
        throw new EntityNotFoundException("User");
    }
}
