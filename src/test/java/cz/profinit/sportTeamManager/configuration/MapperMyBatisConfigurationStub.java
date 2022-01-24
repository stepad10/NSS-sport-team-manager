package cz.profinit.sportTeamManager.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Configuration for MyBatis mapper stubs
 */
@Configuration
@Profile("mapper_mybatis_stub")
@ComponentScan(basePackages = {"cz.profinit.sportTeamManager.mapperMyBatis", "cz.profinit.sportTeamManager.repositories"})
public class MapperMyBatisConfigurationStub {
}
