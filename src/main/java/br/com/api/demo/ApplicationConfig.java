package br.com.api.demo;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
@PropertySource({"classpath:application.properties", "classpath:application-dev.properties" })
@EnableWebMvc
public class ApplicationConfig extends WebMvcConfigurerAdapter {


	@Bean(name = "dataSource")
	public DataSource dataSource() {

		try {
//			System.out.println("DATASOURCE");
//			System.out.println("url: " + getProperties().getProperty("spring.datasource.url"));
//			System.out.println("password: " + getProperties().getProperty("spring.datasource.password"));
//			System.out.println("username: " + getProperties().getProperty("spring.datasource.username"));
//			
//			BasicDataSource ds = new BasicDataSource();
//			ds.setUsername(getProperties().getProperty("spring.datasource.username"));
//			ds.setPassword(getProperties().getProperty("spring.datasource.password").replace("EMPTY", "").trim());
//			ds.setUrl(getProperties().getProperty("spring.datasource.url"));
//			ds.setDriverClassName(getProperties().getProperty("spring.datasource.driver-class-name"));
//			ds.setMaxTotal((Integer.valueOf(getProperties().getProperty("spring.datasource.max-active", "20"))));
//			ds.setMinIdle(Integer.valueOf(getProperties().getProperty("spring.datasource.min-idle", "5")));
//			ds.setInitialSize(Integer.valueOf(getProperties().getProperty("spring.datasource.initial-size", "5")));
//			ds.setValidationQuery(getProperties().getProperty("spring.datasource.validation-query", "SELECT 1"));
//			ds.setValidationQueryTimeout(5);
//			ds.setDefaultQueryTimeout(5);
//			ds.setTestOnBorrow(true);
//			return ds;
			
	        DriverManagerDataSource ds = new DriverManagerDataSource();        
	        ds.setDriverClassName(getProperties().getProperty("spring.datasource.driver-class-name"));
	        ds.setUrl(getProperties().getProperty("spring.datasource.url"));
	        ds.setUsername(getProperties().getProperty("spring.datasource.username"));
	        ds.setPassword(getProperties().getProperty("spring.datasource.password"));        
	        return ds;
	        
		} catch (Exception e) {
			System.out.println( e.getMessage() );
		}

		return null;
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) throws IOException {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(Constants.PACKAGE_BASE);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", getProperties().getProperty("spring.jpa.database-platform"));
		jpaProperties.put("hibernate.hbm2ddl.auto",getProperties().getProperty("spring.jpa.hibernate.hbm2ddl.auto"));
		jpaProperties.put("hibernate.ejb.naming_strategy",getProperties().getProperty("spring.jpa.hibernate.naming-strategy"));
		jpaProperties.put("hibernate.show_sql",getProperties().getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.format_sql",getProperties().getProperty("spring.jpa.show-sql"));
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		return entityManagerFactoryBean;
	}
	
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
    
    @Bean
    public Properties getProperties () throws IOException{
    	ApplicationContext applicationContext = ApiWebApplicationContext.getApplicationContext();
    	Environment environment = applicationContext.getEnvironment();
    	String[] profiles = environment.getActiveProfiles(); 
    	Resource resource = ApiWebApplicationContext.getApplicationContext().getResource("classpath:application"+ (profiles.length > 0 ? ("-"+ profiles[0].toString()) : "") + ".properties");
    	Properties properties = new Properties();
    	properties.load(resource.getInputStream());
    	return properties;
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
