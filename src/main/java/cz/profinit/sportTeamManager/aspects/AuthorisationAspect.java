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
import cz.profinit.sportTeamManager.oauth.PrincipalExtractorImpl;
import cz.profinit.sportTeamManager.service.team.TeamService;
import cz.profinit.sportTeamManager.service.user.UserService;
import org.aopalliance.aop.Advice;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


/**
 * Team authorization aspect. Checks if current authenticated user is in Coach subgroup.
 * If not, throws access denied exception.
 */
@Aspect
@Component
@Profile({"authorization","aspects","Main"})
public class AuthorisationAspect implements Advice {

    @Autowired
    private PrincipalExtractorImpl principalExtractor;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    /**
     * Checks if current user is in Coach subgroup and therefore have rights to changing team properties.
     * Called before all TeamService methods except createNewTeam and getTeamById.
     *
     * @param point joint point data
     * @throws EntityNotFoundException if user or team are not found
     */
    @Before("execution(public * *..TeamService.*(..)) " +
            "&& !execution(public * *..TeamService..getTeamById(..))" +
            " && !execution(public * *..TeamService..createNewTeam(..))")
    public void CoachAuthorisation(JoinPoint point) throws EntityNotFoundException {

        Team team = null;
        RegisteredUser user = null;
        String userEmail = principalExtractor.getPrincipalEmail();

        try {
            team = teamService.getTeamById((Long) point.getArgs()[0]);
            user = userService.findUserByEmail(userEmail);
        } catch (Exception e) {
            throw e;
        }

        if (!team.getTeamSubgroup("Coaches").isUserInList(user)) {
            System.out.println("Access denied");
            throw new RuntimeException("Access denied");
        }
    }
}
