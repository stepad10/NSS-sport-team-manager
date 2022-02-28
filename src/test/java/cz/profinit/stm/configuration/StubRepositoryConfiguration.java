/*
 * StubRepositoryConfiguration
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.stm.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Configuration bringing STUB repositories.
 */
@Configuration
@Profile({"stub_repository"})
@ComponentScan(basePackages = "cz.profinit.stm.repository")
@Import(PasswordEncoderConfiguration.class)
public class StubRepositoryConfiguration {

}
