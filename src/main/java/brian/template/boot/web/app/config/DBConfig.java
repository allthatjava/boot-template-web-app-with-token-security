package brian.template.boot.web.app.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DBConfig {

	@Autowired
	private Environment env;

//    @ConfigurationProperties(prefix="spring.datasource")
	@Bean
	@Profile("dev")
	public DataSource devDataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("h2.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("h2.datasource.url"));
		dataSource.setUsername(env.getProperty("h2.datasource.username"));
		dataSource.setPassword(env.getProperty("h2.datasource.password"));

		return dataSource;
	}

	@Bean
	@Profile("cloud")
	public DataSource cloudDataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("postgres.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("postgres.datasource.url"));
		dataSource.setUsername(env.getProperty("postgres.datasource.username"));
		dataSource.setPassword(env.getProperty("postgres.datasource.password"));

		return dataSource;
	}

	@Bean("entityManagerFactory")
	@Primary
	@Profile(value = { "dev", "default" })
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryDev() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(devDataSource());
		em.setPackagesToScan("brian.template.boot.web.app.domain");

		final Properties prop = new Properties();
		prop.setProperty("hibernate.dialect", env.getProperty("h2.jpa.properties.hibernate.dialect"));
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		em.setJpaProperties(prop);
		em.setJpaVendorAdapter(vendorAdapter);

		return em;
	}

	@Bean("entityManagerFactory")
	@Profile("cloud")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryCloud() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(cloudDataSource());
		em.setPackagesToScan("brian.template.boot.web.app.domain");

		final Properties prop = new Properties();
		prop.setProperty("hibernate.dialect", env.getProperty("postgres.jpa.properties.hibernate.dialect"));
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		em.setJpaProperties(prop);
		em.setJpaVendorAdapter(vendorAdapter);

		return em;
	}

}
