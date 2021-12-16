/*
 * AuthorisationAspect
 *
 * 0.1
 *
 * Author: J. Jansky
 */
package cz.profinit.sportTeamManager.aspects;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.service.team.TeamService;
import cz.profinit.sportTeamManager.service.user.AuthenticationFacade;
import cz.profinit.sportTeamManager.service.user.UserDetailsImpl;
import cz.profinit.sportTeamManager.service.user.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile({"Main", "Authorisation"})
public class AuthorisationAspect {

    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    @Before("execution(public * *..TeamService.*(..)) " +
            "&& !execution(public * *..TeamService.getTeamById(..))" +
            " && !execution(public * *..TeamService.createNewTeam(..))")
    public void CoachAuthorisation(JoinPoint point) throws EntityNotFoundException {
        Authentication authentication = authenticationFacade.getAuthentication();
        Team team = null;
        RegisteredUser user = null;
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        try {
            team = teamService.getTeamById((Long) point.getArgs()[0]);
            user = userService.findUserByEmail(userDetails.getUsername());
        } catch (Exception e) {
            throw e;
        }

        if (!team.getTeamSubgroup("Coaches").isUserInList(user)) {
            System.out.println("Access denied");
            throw new RuntimeException("Access denied");
        }
    }
}
