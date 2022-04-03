/*
 * AuthenticationConfiguration
 *
 * 0.1
 *
 * Author: J. Jansky
 */

package cz.profinit.stm.configuration;

import cz.profinit.stm.oauth.PrincipalExtractorImpl;
import cz.profinit.stm.repository.user.UserRepository;
import cz.profinit.stm.service.user.AuthenticationFacade;
import cz.profinit.stm.service.user.AuthenticationFacadeImpl;
import cz.profinit.stm.service.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration bringing classes necessary for Authentication.
 */
@Configuration
@Import(PasswordEncoderConfiguration.class)
@ComponentScan(basePackageClasses = AuthenticationFacade.class)
@Profile("Main")
public class AuthenticationConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Initialize Authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        UserDetailsService userDetailsService = new UserDetailsServiceImpl(userRepository);
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }


    @Bean
    AuthenticationFacade authenticationFacade() {
        return new AuthenticationFacadeImpl();
    }

    @Bean
    PrincipalExtractorImpl principalExtractor() {
        return new PrincipalExtractorImpl();
    }
}

