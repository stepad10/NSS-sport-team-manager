/*
 * AuthenticationConfigurationTest
 *
 * 0.1
 *
 * Author: J. Jansky
 */
package cz.profinit.sportTeamManager.configuration;

import cz.profinit.sportTeamManager.repositories.UserRepository;
import cz.profinit.sportTeamManager.service.user.UserDetailServiceImpl;
import cz.profinit.sportTeamManager.service.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Configuration bringing classes necessary for Authentication.
 */
@Configuration
@Profile({"authentication"})
@Import(PasswordEncoderBean.class)
public class AuthenticationConfigurationTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationContext context;



    /**
     * Initialize Authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        UserDetailsService userDetailsService = new UserDetailServiceImpl(userRepository);
        UserDetails userDetails = new UserDetailsImpl();
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }


}
