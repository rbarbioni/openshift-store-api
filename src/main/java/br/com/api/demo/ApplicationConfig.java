package br.com.api.demo;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by root on 10/12/15.
 */
@Configuration
@EnableJpaRepositories(basePackages={Constants.PACKAGE_BASE})
@ComponentScan(basePackages={Constants.PACKAGE_BASE})
@EnableTransactionManagement
public class ApplicationConfig {

    @Autowired
    private Environment env;

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource (){

        try{

            BasicDataSource ds = new BasicDataSource();
            ds.setUsername(env.getProperty("spring.datasource.username") );
            ds.setPassword(env.getProperty("spring.datasource.password").replace("EMPTY", "").trim());
            ds.setUrl(env.getProperty("spring.datasource.url"));
            ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));

            ds.setMaxTotal((Integer.valueOf(env.getProperty("spring.datasource.max-active", "20"))));
            ds.setMinIdle(Integer.valueOf(env.getProperty("spring.datasource.min-idle", "5")));
            ds.setInitialSize(Integer.valueOf(env.getProperty("spring.datasource.initial-size", "5")));
            ds.setValidationQuery(env.getProperty("spring.datasource.validation-query", "SELECT 1"));
            ds.setValidationQueryTimeout(5);
            ds.setDefaultQueryTimeout(5);
            ds.setTestOnBorrow(true);
            return ds;
        }catch ( Exception e ){

        }

        return null;
    }
}
