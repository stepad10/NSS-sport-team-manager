package cz.profinit.sportTeamManager.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@MapperScan("cz.profinit.sportTeamManager.mapperMyBatis")
//@ComponentScan("cz.profinit.sportTeamManager.*")
@Import(DataSourceConfiguration.class)
public class MyBatisConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setTypeAliasesPackage("cz.profinit.sportTeamManager.model");
        factoryBean.setTypeHandlersPackage("cz.profinit.sportTeamManager.typeHandler");
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }
}
