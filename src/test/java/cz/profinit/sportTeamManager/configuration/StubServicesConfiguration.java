/*
 * StubRepositoryConfiguration
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.configuration;

import cz.profinit.sportTeamManager.stubs.stubService.StubTeamServiceImpl;
import cz.profinit.sportTeamManager.stubs.stubService.StubUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration bringing STUB services.
 */
@Configuration
@Profile({"stub_service"})
@ComponentScan(basePackageClasses = {StubTeamServiceImpl.class, StubUserServiceImpl.class})
public class StubServicesConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
