/*
 * WebApplicationConfiguration
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.configuration;

import cz.profinit.sportTeamManager.repositories.UserRepository;
import cz.profinit.sportTeamManager.service.user.UserDetailServiceImpl;
import cz.profinit.sportTeamManager.service.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration of a web services, mainly of authorization provider and http security protocols.
 */
@Configuration
@Profile({"Main"})
@Import(ApplicationConfiguration.class)
@EnableWebSecurity
@EnableWebMvc
public class WebApplicationConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    /**
     * Initialize Authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        UserDetailsService userDetailsService = new UserDetailServiceImpl(userRepository);
        UserDetails userDetails = new UserDetailsImpl();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    /**
     * Sets http security authorization protocols.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();
    }


}
