package cz.profinit.sportTeamManager;

import cz.profinit.sportTeamManager.configuration.WebApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ContextConfiguration(classes = WebApplicationConfiguration.class)
@ActiveProfiles("Main")
public class SportTeamManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportTeamManagerApplication.class, args);
    }

}
