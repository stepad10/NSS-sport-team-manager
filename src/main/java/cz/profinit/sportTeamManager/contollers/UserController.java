/*
 * UserController
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.contollers;

import cz.profinit.sportTeamManager.dto.UserDetailsDTO;
import cz.profinit.sportTeamManager.mappers.UserMapper;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Maps incoming user requests.
 * TODO not all features implemented
 */
@RestController
@Profile({"Main", "stub"})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DaoAuthenticationProvider authenticationProvider;
    @Autowired
    private UserMapper userMapper;


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
     * User login
     *
     * @param user    UserDetails data transfer object containing email and password
     * @param request http request
     * @deprecated Maybe will be used later.
     */
    @PostMapping("/login")
    public void test2(@RequestBody UserDetailsDTO user, HttpServletRequest request) {
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


//    @ExceptionHandler({ EmailExistsException.class })
//    public ResponseEntity<Object> handleUserAlreadyExist(RuntimeException ex, WebRequest request) {
//
//        String bodyOfResponse = "aaaaa";
//
//
//        return handleExceptionInternal(
//                ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }

}
