/*
 * WebApplicationConfiguration
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration of a web services, mainly of authorization provider and http security protocols.
 */
@Configuration
@EnableWebSecurity
@EnableWebMvc
@Profile("Main")
public class WebApplicationConfiguration extends WebSecurityConfigurerAdapter {

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
                .permitAll()
                .and()
                .oauth2Login().defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure");
    }


}
