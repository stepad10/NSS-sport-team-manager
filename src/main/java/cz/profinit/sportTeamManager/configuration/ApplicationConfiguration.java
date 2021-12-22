/*
 * ApplicationConfiguration
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Basic configuration of an application.
 */
@Configuration
@Import({WebApplicationConfiguration.class,
        PasswordEncoderBean.class,
        AuthenticationConfiguration.class,
        AuthorizationConfiguration.class,
        AspectConfiguration.class})
//@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan
public class ApplicationConfiguration {


}
