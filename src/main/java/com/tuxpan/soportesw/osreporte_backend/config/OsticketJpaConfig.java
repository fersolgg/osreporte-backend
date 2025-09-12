package com.tuxpan.soportesw.osreporte_backend.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
	basePackages = "com.tuxpan.soportesw.osreporte_backend.osticket.repositories",
	entityManagerFactoryRef = "osticketEntityManagerFactory",
	transactionManagerRef = "osticketTransactionManager"
)
public class OsticketJpaConfig {

	@Bean(name = "osticketEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean osticketEntityManagerFactory(
			@Qualifier("osticketDataSource") DataSource dataSource,
			JpaProperties jpaProperties) {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(false);
		Map<String, Object> props = new java.util.HashMap<>();
		props.putAll(jpaProperties.getProperties());
		props.put("hibernate.hbm2ddl.auto", "none");
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	factory.setDataSource(dataSource);
	factory.setPackagesToScan("com.tuxpan.soportesw.osreporte_backend.osticket.entities");
	factory.setJpaVendorAdapter(adapter);
	factory.setJpaPropertyMap(props);
	factory.setPersistenceUnitName("osticket");
		return factory;
	}

	@Bean(name = "osticketTransactionManager")
	public JpaTransactionManager osticketTransactionManager(
			@Qualifier("osticketEntityManagerFactory") LocalContainerEntityManagerFactoryBean osticketEntityManagerFactory) {
		return new JpaTransactionManager(osticketEntityManagerFactory.getObject());
	}
}
