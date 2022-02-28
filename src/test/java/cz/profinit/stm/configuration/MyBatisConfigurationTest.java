package cz.profinit.stm.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@MapperScan("cz.profinit.stm.mapperMyBatis")
@Import(DataSourceConfiguration.class)
public class MyBatisConfigurationTest {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setTypeAliasesPackage("cz.profinit.stm.model");
        factoryBean.setTypeHandlersPackage("cz.profinit.stm.typeHandler");
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }
}
