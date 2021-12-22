package cz.profinit.sportTeamManager.oauth;

import cz.profinit.sportTeamManager.service.user.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Map;

public class PrincipalExtractorImpl implements PrincipalExtractor {
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Override
    public Map<String, Object> extractPrincipal(Map<String, Object> map) {
        return map;
    }

    private Map<String, Object> getPrincipalMap() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String className = String.valueOf(authentication.getPrincipal().getClass());
        if (className.equals("class org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser")) {
            DefaultOidcUser userPrincipals = (DefaultOidcUser) authentication.getPrincipal();
            return userPrincipals.getAttributes();
        } else if (className.equals("class org.springframework.security.oauth2.core.user.DefaultOAuth2User")) {
            DefaultOAuth2User userPrincipals = (DefaultOAuth2User) authentication.getPrincipal();
            return userPrincipals.getAttributes();
        }
        return null;
    }

   public String getPrincipalEmail() {
        return (String) extractPrincipal(getPrincipalMap()).get("email");
   }

   public String[] getPrincipalNameAndSurname() {
       String fullName = (String) extractPrincipal(getPrincipalMap()).get("name");
       int surnameFrom = fullName.lastIndexOf(" ");
       String name = fullName.substring(0,surnameFrom);
       String surname = fullName.substring(surnameFrom+1);
       return new String[]{name, surname};
   }
}
