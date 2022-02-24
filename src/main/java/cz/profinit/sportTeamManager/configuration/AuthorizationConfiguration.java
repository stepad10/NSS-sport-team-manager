/*
 * AuthorizationConfiguration
 *
 * 0.1
 *
 * Author: J. Jansky
 */
package cz.profinit.sportTeamManager.configuration;

import cz.profinit.sportTeamManager.aspect.AuthorisationAspect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration bringing classes necessary for Authorization.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = {AuthorisationAspect.class})
public class AuthorizationConfiguration {
}
