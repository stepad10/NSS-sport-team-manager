/*
 * StubRepositoryConfiguration
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.configuration;

import cz.profinit.sportTeamManager.stubs.stubRepositories.StubTeamRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.StubUserRepository;
import cz.profinit.sportTeamManager.stubs.stubService.StubTeamServiceImpl;
import cz.profinit.sportTeamManager.stubs.stubService.StubUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration bringing STUB repositories.
 */
@Configuration
@Profile({"stub_repository"})
@ComponentScan(basePackageClasses = {StubTeamRepository.class, StubUserRepository.class})
public class StubRepositoryConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
