package br.com.api.demo;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by root on 10/12/15.
 */
@Configuration
@EnableJpaRepositories(basePackages = { Constants.PACKAGE_BASE })
@ComponentScan(basePackages = { Constants.PACKAGE_BASE })
@PropertySource({"classpath:application.properties" })
@EnableWebMvc
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Bean(name = "dataSource", destroyMethod = "close")
	public DataSource dataSource() {

		try {

			Constants.logger().info("DATASOURCE");
			Constants.logger().info("url: " + env.getProperty("spring.datasource.url"));
			Constants.logger().info("password: " + env.getProperty("spring.datasource.password"));
			Constants.logger().info("username: " + env.getProperty("spring.datasource.username"));
			
			BasicDataSource ds = new BasicDataSource();
			ds.setUsername(env.getProperty("spring.datasource.username"));
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
		} catch (Exception e) {

		}

		return null;
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(Constants.PACKAGE_BASE);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect",env.getRequiredProperty("spring.jpa.database-platform"));
		jpaProperties.put("hibernate.hbm2ddl.auto",env.getRequiredProperty("spring.jpa.hibernate.hbm2ddl.auto"));
		jpaProperties.put("hibernate.ejb.naming_strategy",env.getRequiredProperty("spring.jpa.hibernate.naming-strategy"));
		jpaProperties.put("hibernate.show_sql",env.getRequiredProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.format_sql",env.getRequiredProperty("spring.jpa.show-sql"));
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		return entityManagerFactoryBean;
	}
	
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
    
    @Value("classpath:data.sql")
    private Resource dataScript;

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(dataScript);
        return populator;
    }    
}
