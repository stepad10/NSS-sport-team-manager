package cz.profinit.sportTeamManager.service.user;

import liquibase.pro.packaged.P;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Profile({"main","aspect","Authentication"})
@NoArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
