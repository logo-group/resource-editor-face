package com.lbs.re.app.routing;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "com.lbs.re.data.repository" }, entityManagerFactoryRef = "appEntityManager", transactionManagerRef = "appTransactionManager")
@ComponentScan(basePackages = { "com.lbs.re.data.dao" })
@EnableTransactionManagement
public class DataSourceConfiguration {

	@Autowired(required = false)
	private PersistenceUnitManager persistenceUnitManager;

	/**
	 * Available data sources
	 *
	 *
	 */
	@Bean
	@ConfigurationProperties(prefix = "app.jplatform.connection")
	public DataSource jplatformDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "app.tiger.connection")
	public DataSource tigerDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * Adds all available datasources to datasource map.
	 *
	 * @return datasource of current context
	 */
	@Bean
	@Primary
	public DataSource appDataSource() {
		DataSourceRouter router = new DataSourceRouter();
		final HashMap<Object, Object> map = new HashMap<>(3);
		map.put(DatabaseEnvironment.JPLATFORM, jplatformDataSource());
		map.put(DatabaseEnvironment.TIGER, tigerDataSource());
		router.setTargetDataSources(map);
		return router;
	}

	@Bean
	@Primary
	@ConfigurationProperties("app.connection.jpa")
	public JpaProperties appJpaProperties() {
		return new JpaProperties();
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean appEntityManager(final JpaProperties appJpaProperties) {
		EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(appJpaProperties);
		return builder.dataSource(appDataSource()).packages("com.lbs.re.model", "com.lbs.re.util").persistenceUnit("appEntityManager").build();
	}

	@Bean
	@Primary
	public JpaTransactionManager appTransactionManager(@Qualifier("appEntityManager") final EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}

	private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties appJpaProperties) {
		JpaVendorAdapter jpaVendorAdapter = createJpaVendorAdapter(appJpaProperties);
		return new EntityManagerFactoryBuilder(jpaVendorAdapter, appJpaProperties.getProperties(), this.persistenceUnitManager);
	}

	private JpaVendorAdapter createJpaVendorAdapter(JpaProperties jpaProperties) {
		AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(jpaProperties.isShowSql());
		adapter.setDatabase(jpaProperties.getDatabase());
		adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
		adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
		return adapter;
	}
}
