package eu.profinit.stm.util;

import eu.profinit.stm.dto.SocialProvider;
import eu.profinit.stm.dto.user.LocalUser;
import eu.profinit.stm.dto.user.UserInfo;
import eu.profinit.stm.model.user.RoleEnum;
import eu.profinit.stm.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author Chinna
 *
 */
public class GeneralUtils {

	public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<RoleEnum> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
		/*
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (RoleEnum role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
		return authorities;
		 */
	}

	public static SocialProvider toSocialProvider(String providerType) {
		return Arrays.stream(SocialProvider.values()).filter(provider -> provider.getProviderType().equals(providerType)).findFirst().orElse(SocialProvider.LOCAL);
		/*
		for (SocialProvider socialProvider : SocialProvider.values()) {
			if (socialProvider.getProviderType().equals(providerType)) {
				return socialProvider;
			}
		}
		return SocialProvider.LOCAL;
		*/
	}

	public static UserInfo buildUserInfo(LocalUser localUser) {
		List<String> roles = localUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		User user = localUser.getUser();
		return new UserInfo(user.getName(), user.getSurname(), user.getEmail(), roles);
	}
}
