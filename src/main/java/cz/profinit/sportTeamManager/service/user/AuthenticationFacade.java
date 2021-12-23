package cz.profinit.sportTeamManager.service.user;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;


public interface AuthenticationFacade {
    Authentication getAuthentication();
}
