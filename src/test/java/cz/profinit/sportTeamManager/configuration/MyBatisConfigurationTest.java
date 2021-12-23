package cz.profinit.sportTeamManager.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@MapperScan("cz.profinit.sportTeamManager.mapperMyBatis")
@ComponentScan("cz.profinit.sportTeamManager.*")
@Profile("database")
public class MyBatisConfigurationTest {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dmds = new DriverManagerDataSource();
        dmds.setDriverClassName("org.postgresql.Driver");
        dmds.setUrl("jdbc:postgresql://localhost/STM-db");
        dmds.setUsername("postgres");
        dmds.setPassword("superuser");
        return dmds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setTypeAliasesPackage("cz.profinit.sportTeamManager.model");
        factoryBean.setTypeHandlersPackage("cz.profinit.sportTeamManager.typeHandler");
        factoryBean.setDataSource(dataSource());
        return factoryBean.getObject();
    }
}
