/*
 * AspectConfigurationTest
 *
 * 0.1
 *
 * Author: J. Jansky
 */
package cz.profinit.sportTeamManager.configuration;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

/**
 * Configuration of aspects
 */
@Configuration
@EnableAspectJAutoProxy
@Profile({"aspects"})
@ComponentScan("cz.profinit.sportTeamManager.aspect")
public class AspectConfigurationTest {

}
