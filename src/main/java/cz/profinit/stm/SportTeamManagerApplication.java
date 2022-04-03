package cz.profinit.stm;

import cz.profinit.stm.configuration.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ContextConfiguration(classes = ApplicationConfiguration.class)
@ActiveProfiles("Main")
public class SportTeamManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportTeamManagerApplication.class, args);
    }

}
