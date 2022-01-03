/*
 * ApplicationConfigurationTest
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.configuration;

import cz.profinit.sportTeamManager.controllers.team.TeamController;
import cz.profinit.sportTeamManager.controllers.user.UserController;
import org.springframework.context.annotation.ComponentScan;

/**
 * Basic configuration of an application for tests.
 */
//@Configuration
//@Profile({"test"})
@ComponentScan(basePackageClasses = {UserController.class, TeamController.class})
public class ApplicationConfigurationTest {

}
