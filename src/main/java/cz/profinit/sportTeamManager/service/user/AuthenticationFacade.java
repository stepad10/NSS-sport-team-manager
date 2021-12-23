package cz.profinit.sportTeamManager.service.user;

import org.springframework.security.core.Authentication;


public interface AuthenticationFacade {
    Authentication getAuthentication();
}
