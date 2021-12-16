/*
 * UserServiceImpl
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.service.user;

import cz.profinit.sportTeamManager.exceptions.EmailExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.exceptions.UserOrPasswordNotMatchException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * This implementation of the UserService controls the creation of the new user and checks if given logging
 * credentials corresponds to any user in database.
 */
@Service
@Profile("Main")
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;


    /**
     * Register a new user to the database. Before registration, checks if given email address is in the database.
     * Then encrypt a given password. Lastly, the new user is saved to the database.
     *
     * @param newUser newly registered user collected by controller
     * @return registered user
     * @throws EmailExistsException thrown, when given email is already in database
     */
    public RegisteredUser newUserRegistration(RegisteredUser newUser) throws EmailExistsException {
        if (emailExists(newUser.getEmail())) {
            throw new EmailExistsException(
                    "Account with e-mail address " + newUser.getEmail() + "already exists.");
        }

        RegisteredUser registeredUser = new RegisteredUser();

        registeredUser.setName(newUser.getName());
        registeredUser.setSurname(newUser.getSurname());
        registeredUser.setEmail(newUser.getEmail());
        registeredUser.setRole(RoleEnum.USER);

        registeredUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        return userRepository.saveRegistredUser(registeredUser);
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

    /**
     * Checks if the given logging credentials match any user in the database. If not, throws an exception.
     *
     * @param userEmail logging email address
     * @param password  logging password
     * @return Logged user if successful
     * @throws UserOrPasswordNotMatchException if the logging credentials do not match any user in the database
     */
    public RegisteredUser userLogIn(String userEmail, String password) throws UserOrPasswordNotMatchException {
        RegisteredUser user = new RegisteredUser();

        try {
            user = userRepository.findUserByEmail(userEmail);
        } catch (Exception e) {
            if (e.getMessage().equals("User entity not found!")) {
                throw new UserOrPasswordNotMatchException();
            }
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new UserOrPasswordNotMatchException();
        }
    }


    /**
     * Gets a user form database sought by email.
     *
     * @param email user email
     * @return found user
     * @throws EntityNotFoundException thrown if user is not found in database
     */
    public RegisteredUser findUserByEmail(String email) throws EntityNotFoundException {
        return userRepository.findUserByEmail(email);
    }

    //TODO
    @Override
    public RegisteredUser getLogedUser() {
        return null;
    }


}
