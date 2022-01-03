/*
 * StubUserServiceImpl
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.stubs.stubService.user;

import cz.profinit.sportTeamManager.exceptions.EmailExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.service.user.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * Stub User service for Unit tests.
 */
@Service
public class StubUserServiceImpl implements UserService {

    private RegisteredUser loggedUser1 = new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com", RoleEnum.USER);
    private RegisteredUser loggedUser2 = new RegisteredUser("Pavel", "Smutny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@seznam.cz", RoleEnum.USER);
    private RegisteredUser loggedUser3 = new RegisteredUser("Jirka", "Vesely", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@email.cz", RoleEnum.USER);
    private RegisteredUser loggedUser4 = new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER);
    private RegisteredUser loggedUser5 = new RegisteredUser("Adam", "Stastny", "2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "email@gmail.com", RoleEnum.USER);

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
     * Changes user name.
     *
     * @param email   email address of user to find him in database
     * @param newName new user name
     * @return user with changed name
     * @throws EntityNotFoundException if user is not found
     */
    @Override
    public RegisteredUser changeUserName(String email, String newName) throws EntityNotFoundException {
        RegisteredUser user = findUserByEmail(email);
        RegisteredUser userOut = new RegisteredUser(
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getEmail(),
                RoleEnum.USER);
        userOut.setName(newName);
        return userOut;
    }

    /**
     * Changes user surname
     *
     * @param email      email address of user to find him in database
     * @param newSurname new user surname
     * @return user with changed surname
     * @throws EntityNotFoundException if user is not found
     */
    @Override
    public RegisteredUser changeUserSurname(String email, String newSurname) throws EntityNotFoundException {
        RegisteredUser user = findUserByEmail(email);
        RegisteredUser userOut = new RegisteredUser(
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getEmail(),
                RoleEnum.USER);
        userOut.setSurname(newSurname);
        return userOut;
    }

    /**
     * Changes user email. Before checks if email is not already taken.
     *
     * @param email    email address of user to find him in database
     * @param newEmail new email address
     * @return user with changed email address
     * @throws EntityNotFoundException if user is not found
     */
    @Override
    public RegisteredUser changeUserEmail(String email, String newEmail) throws EntityNotFoundException {
        RegisteredUser user = findUserByEmail(email);
        if (emailExists(newEmail)) {
            throw new EmailExistsException("Account with e-mail address " + newEmail + "already exists.");
        }
        RegisteredUser userOut = new RegisteredUser(
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getEmail(),
                RoleEnum.USER);
        userOut.setEmail(newEmail);
        return userOut;
    }


    /**
     * Changes user role.
     *
     * @param email   email address of user to find him in database
     * @param newRole new user role
     * @return user with changed user role
     * @throws EntityNotFoundException if user is not found
     */
    @Override
    public RegisteredUser changeUserRole(String email, RoleEnum newRole) throws EntityNotFoundException {
        RegisteredUser user = findUserByEmail(email);
        user.setRole(newRole);
        return user;
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
        RegisteredUser user = new RegisteredUser();
        if (Objects.equals(email, "is@seznam.cz")) {
            return loggedUser2;
        } else if (email.equals("is@gmail.com")) {
            return loggedUser1;
        } else if (email.equals("is@email.cz")) {
            return loggedUser3;
        } else if (email.equals("ts@gmail.com")) {
            return loggedUser4;
        } else if (email.equals("email@gmail.com")) {
            return loggedUser5;
        } else {
            throw new EntityNotFoundException("User");
        }
    }

    /**
     * Finds if user exists in database by email.
     *
     * @param email user email
     * @return true if user is in database, false otherwise
     */
    private boolean emailExists(String email) {
        try {
            findUserByEmail(email);
        } catch (Exception e) {
            if (e.getMessage().equals("User entity not found!")) {
                return false;
            }
        }
        return true;
    }
}
