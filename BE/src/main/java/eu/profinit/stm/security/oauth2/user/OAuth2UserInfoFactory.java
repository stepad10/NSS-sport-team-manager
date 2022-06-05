package eu.profinit.stm.security.oauth2.user;

import eu.profinit.stm.dto.SocialProvider;
import eu.profinit.stm.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(SocialProvider socialProvider, Map<String, Object> attributes) {
        if (SocialProvider.GOOGLE.equals(socialProvider)) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (SocialProvider.FACEBOOK.equals(socialProvider)) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (SocialProvider.GITHUB.equals(socialProvider)) {
            return new GithubOAuth2UserInfo(attributes);
        } else if (SocialProvider.LINKEDIN.equals(socialProvider)) {
            return new LinkedinOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + socialProvider + " is not supported yet.");
        }
    }
}