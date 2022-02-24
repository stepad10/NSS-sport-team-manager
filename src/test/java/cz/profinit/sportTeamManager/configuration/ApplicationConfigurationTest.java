/*
 * ApplicationConfigurationTest
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Basic configuration of an application for tests.
 */
@Configuration
@Import({WebApplicationConfiguration.class,
        AspectConfiguration.class,
        AuthenticationConfiguration.class})
public class ApplicationConfigurationTest {

}
