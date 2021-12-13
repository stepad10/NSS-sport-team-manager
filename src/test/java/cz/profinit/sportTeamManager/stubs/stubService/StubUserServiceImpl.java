/*
 * StubUserServiceImpl
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.stubs.stubService;

import cz.profinit.sportTeamManager.exceptions.EmailExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.service.user.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


/**
 * Stub User service for Unit tests.
 */
@Service
@Profile("stub_team_testing")
public class StubUserServiceImpl implements UserService {

    /**
     * Registers a new user.
     * If a new user email address is equal to "email@gmail.com",
     * the user is already in database and method throws EmailExistsException.
     *
     * @param newUser new user
     * @return registered user
     * @throws EmailExistsException thrown if new user email is equal to "email@gmail.com"
     */
    @Override
    public RegisteredUser newUserRegistration(RegisteredUser newUser) throws EmailExistsException {

        if (newUser.getEmail().equals("email@gmail.com")) {
            throw new EmailExistsException(
                    "Account with e-mail address " + newUser.getEmail() + "already exists.");
        }

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setName(newUser.getName());
        registeredUser.setSurname(newUser.getSurname());
        registeredUser.setEmail(newUser.getEmail());
        registeredUser.setRole(RoleEnum.USER);

        registeredUser.setPassword("hashedPassword");
        return registeredUser;
    }

    /**
     * /TODO badly implemented feature
     *
     * @return
     */
    @Override
    public RegisteredUser getLogedUser() {
        return new RegisteredUser("Adam", "Stastny", "pass", "email@gmail.com", RoleEnum.USER);
    }

    /**
     * Returns STUB user found by email. Two possible users with email "email@gmail.com" or "ts@gmail.com" are returned.
     * Otherwise, the exception EntityNotFoundException is thrown.
     *
     * @param email user email
     * @return a STUB user
     * @throws EntityNotFoundException thrown if new user email is not equal to "email@gmail.com" or "ts@gmail.com"
     */
    @Override
    public RegisteredUser findUserByEmail(String email) throws EntityNotFoundException {
        if (email.equals("email@gmail.com")) {
            return new RegisteredUser("Adam", "Stastny", "pass", "email@gmail.com", RoleEnum.USER);
        } else if (email.equals("ts@gmail.com")) {
            return new RegisteredUser("Tomas", "Smutny", "pass", "ts@gmail.com", RoleEnum.USER);
        }
        throw new EntityNotFoundException("User not found");
    }


}
