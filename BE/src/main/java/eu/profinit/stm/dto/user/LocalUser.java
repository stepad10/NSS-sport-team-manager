package eu.profinit.stm.dto.user;

import eu.profinit.stm.model.user.User;
import eu.profinit.stm.util.GeneralUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Chinna
 */
public class LocalUser extends org.springframework.security.core.userdetails.User implements OAuth2User, OidcUser {

    private static final long serialVersionUID = -2845160792248762779L;
    private final OidcIdToken idToken;
    private final OidcUserInfo userInfo;
    private Map<String, Object> attributes;
    private final User user;

    public LocalUser(final String userID, final String password, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final User user) {
        this(userID, password, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, user, null, null);
    }

    public LocalUser(final String userID, final String password, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final User user, OidcIdToken idToken,
                     OidcUserInfo userInfo) {
        super(userID, password, true, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    public static LocalUser create(User user, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
        LocalUser localUser = new LocalUser(user.getEmail(), user.getPassword(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(Set.of(user.getRole())),
                user, idToken, userInfo);
        localUser.setAttributes(attributes);
        return localUser;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return this.user.getName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.attributes;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }

    public User getUser() {
        return user;
    }
}
