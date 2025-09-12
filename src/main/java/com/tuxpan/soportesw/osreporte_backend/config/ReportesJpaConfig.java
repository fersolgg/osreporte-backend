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
@EnableJpaRepositories(basePackages = "com.tuxpan.soportesw.osreporte_backend.reportes.repositories", entityManagerFactoryRef = "reportesEntityManagerFactory", transactionManagerRef = "reportesTransactionManager")
public class ReportesJpaConfig {

    @Bean(name = "reportesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean reportesEntityManagerFactory(
            @Qualifier("reportesDataSource") DataSource dataSource,
            JpaProperties jpaProperties) {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        Map<String, Object> props = new java.util.HashMap<>();
        props.putAll(jpaProperties.getProperties());
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("com.tuxpan.soportesw.osreporte_backend.reportes.entities");
        factory.setJpaVendorAdapter(adapter);
        factory.setJpaPropertyMap(props);
        return factory;
    }

    @Bean(name = "reportesTransactionManager")
    public JpaTransactionManager reportesTransactionManager(
            @Qualifier("reportesEntityManagerFactory") LocalContainerEntityManagerFactoryBean reportesEntityManagerFactory) {
        return new JpaTransactionManager(reportesEntityManagerFactory.getObject());
    }
}
