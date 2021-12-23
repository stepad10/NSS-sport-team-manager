/*
 * StubRepositoryConfiguration
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.configuration;

import cz.profinit.sportTeamManager.stubs.stubRepositories.StubEventRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.StubInvitationRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.StubTeamRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.StubUserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Configuration bringing STUB repositories.
 */
@Configuration
@Profile({"stub_repository"})
@ComponentScan(basePackageClasses = {StubTeamRepository.class, StubUserRepository.class, StubEventRepository.class, StubInvitationRepository.class})
@Import(PasswordEncoderBean.class)
public class StubRepositoryConfiguration {

}
