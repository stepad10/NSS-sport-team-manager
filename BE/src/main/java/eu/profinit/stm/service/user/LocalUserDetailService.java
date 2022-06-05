package eu.profinit.stm.service.user;

import eu.profinit.stm.dto.user.LocalUser;
import eu.profinit.stm.exception.EntityNotFoundException;
import eu.profinit.stm.exception.ResourceNotFoundException;
import eu.profinit.stm.model.user.User;
import eu.profinit.stm.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Chinna
 */
@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.findUserByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return createLocalUser(user);
    }

    @Transactional
    public LocalUser loadUserByEmail(String email) {
        try {
            return createLocalUser(userService.findUserByEmail(email));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User", "email", email);
        }
    }

    private LocalUser createLocalUser(User user) {
        return new LocalUser(user.getEmail(), user.getPassword(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(Set.of(user.getRole())), user);
    }
}
