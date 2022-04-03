/*
 * StubRepositoryConfiguration
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.stm.configuration;

import cz.profinit.stm.service.team.TeamServiceStub;
import cz.profinit.stm.service.user.UserServiceStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration bringing STUB services.
 */
@Configuration
@Profile({"stub_service"})
@ComponentScan(basePackages = "cz.profinit.stm.repository")
@Import(PasswordEncoderConfiguration.class)
public class StubServiceConfiguration {
}
