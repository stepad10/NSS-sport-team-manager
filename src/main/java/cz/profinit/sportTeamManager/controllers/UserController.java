/*
 * UserController
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.controllers;

import cz.profinit.sportTeamManager.dto.RegisteredUserDTO;
import cz.profinit.sportTeamManager.dto.UserDetailsDTO;
import cz.profinit.sportTeamManager.mappers.UserMapper;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.oauth.PrincipalExtractorImpl;
import cz.profinit.sportTeamManager.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Maps incoming user requests.
 * TODO not all features implemented
 */
@RestController
@Profile({"Main", "test"})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DaoAuthenticationProvider authenticationProvider;
    @Autowired
    private PrincipalExtractorImpl principalExtractor;


    /**
     * Provides a new user registration with UserDetailsDTO. Checks if user email is not already in database.
     * If is, throws BAD_REQUEST exception.
     *
     * @param newUser UserDetails data transfer object containing email and password
     * @param request http request
     */
    @PostMapping("/user/registration")
    public void registerNewUser(@RequestBody UserDetailsDTO newUser, HttpServletRequest request) {
        RegisteredUser registeredUser = UserMapper.mapUserDetailsDTOToRegisteredUser(newUser);
        System.out.println(newUser);
        try {
            registeredUser = userService.newUserRegistration(registeredUser);
        } catch (Exception e) {
            if (e.getMessage().equals("Account with e-mail address email@gmail.comalready exists.")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        }

    }

    /**
     * Gets registered user from database by email.
     *
     * @param userEmail email address of seek user
     * @return registered user data transfer object
     */
    @GetMapping("/user/{userEmail}")
    public RegisteredUserDTO refreshUser(@PathVariable String userEmail) {
        RegisteredUser user = new RegisteredUser();
        try {
            user = userService.findUserByEmail(userEmail);
        } catch (Exception e) {

        }
        return UserMapper.mapRegistredUserToRegistredUserDTO(user);
    }

    /**
     * User login
     *
     * @param user    UserDetails data transfer object containing email and password
     * @param request http request
     * @deprecated Maybe will be used later.
     */

    @PostMapping("/login")
    public void userLogin(@RequestBody UserDetailsDTO user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getName(), user.getPassword(), new ArrayList<>());
        token.setDetails(new WebAuthenticationDetails(request));
        SecurityContext context = SecurityContextHolder.getContext();
        try {
            final Authentication auth = authenticationProvider.authenticate(token);
            context.setAuthentication(auth);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        Authentication auth = context.getAuthentication();
        auth.getPrincipal();
    }

    /**
     * Changes name of the user.
     *
     * @param userEmail user what name should be changed
     * @param userNewName new name of the user
     * @return user DTO with updated name
     */
    @PutMapping("user/{userEmail}/name/{userNewName}")
    public RegisteredUserDTO changeUserName(@PathVariable String userEmail, @PathVariable String userNewName) {
        RegisteredUser user = new RegisteredUser();
        try {
            user = userService.changeUserName(userEmail,userNewName);
        } catch (Exception e) {
            if (e.getMessage().equals("User entity not found!")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        }
        return UserMapper.mapRegistredUserToRegistredUserDTO(user);
    }

    /**
     * Changes surname of the user.
     *
     * @param userEmail user what surname should be changed
     * @param userNewSurname new surname of the user
     * @return user DTO with updated surname
     */
    @PutMapping("user/{userEmail}/surname/{userNewSurname}")
    public RegisteredUserDTO changeUserSurname(@PathVariable String userEmail, @PathVariable String userNewSurname) {
        RegisteredUser user = new RegisteredUser();
        try {
            user = userService.changeUserSurname(userEmail,userNewSurname);
        } catch (Exception e) {
            if (e.getMessage().equals("User entity not found!")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        }
        return UserMapper.mapRegistredUserToRegistredUserDTO(user);
    }

    /**
     * Changes email of the user.
     *
     * @param userEmail user what surname should be changed
     * @param userNewEmail new surname of the user
     * @return user DTO with updated surname
     */
    @PutMapping("user/{userEmail}/email/{userNewEmail}")
    public RegisteredUserDTO changeUserEmail(@PathVariable String userEmail, @PathVariable String userNewEmail) {
        RegisteredUser user = new RegisteredUser();
        try {
            user = userService.changeUserEmail(userEmail,userNewEmail);
        } catch (Exception e) {
            if (e.getMessage().equals("User entity not found!")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, e.getMessage(), e);
            } else if (e.getMessage().equals("Account with e-mail address " + userNewEmail + "already exists.")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        }
        return UserMapper.mapRegistredUserToRegistredUserDTO(user);
    }

    /**
     * Maps a logging success message if user is already registered. Registration message if user is registering for a first time.
     *
     * @return logging message
     */
    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        String email = principalExtractor.getPrincipalEmail();
        try {
            userService.findUserByEmail(email);
        } catch (Exception e) {
            if (e.getMessage().equals("User entity not found!")) {
                RegisteredUser user = new RegisteredUser(
                        principalExtractor.getPrincipalNameAndSurname()[0],
                        principalExtractor.getPrincipalNameAndSurname()[1],
                        "",
                        email,
                        RoleEnum.USER);
                userService.newUserRegistration(user);
                return "Registration successful";
            }
        }
        return "Welcome back";
    }
}
