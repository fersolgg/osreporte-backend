package com.tuxpan.soportesw.osreporte_backend.config;

// Importaciones necesarias para la configuración JPA y manejo de transacciones
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

// Indica que esta clase es una configuración de Spring
@Configuration
// Habilita los repositorios JPA para el paquete especificado y configura los beans de EntityManager y TransactionManager
@EnableJpaRepositories(basePackages = "com.tuxpan.soportesw.osreporte_backend.reportes.repositories", entityManagerFactoryRef = "reportesEntityManagerFactory", transactionManagerRef = "reportesTransactionManager")
public class ReportesJpaConfig {

    // Bean para el EntityManagerFactory específico de Reportes
    @Bean(name = "reportesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean reportesEntityManagerFactory(
            @Qualifier("reportesDataSource") DataSource dataSource,
            JpaProperties jpaProperties) {
        // Configura el adaptador de Hibernate para JPA
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true); // Mostrar SQL en consola
        // Copia las propiedades JPA
        Map<String, Object> props = new java.util.HashMap<>();
        props.putAll(jpaProperties.getProperties());
        // Crea y configura el EntityManagerFactory
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource); // Asocia el DataSource
        factory.setPackagesToScan("com.tuxpan.soportesw.osreporte_backend.reportes.entities"); // Paquete de entidades
        factory.setJpaVendorAdapter(adapter); // Usa Hibernate
        factory.setJpaPropertyMap(props); // Propiedades JPA
        return factory;
    }

    // Bean para el TransactionManager específico de Reportes
    @Bean(name = "reportesTransactionManager")
    public JpaTransactionManager reportesTransactionManager(
            @Qualifier("reportesEntityManagerFactory") LocalContainerEntityManagerFactoryBean reportesEntityManagerFactory) {
        // Retorna el TransactionManager usando el EntityManagerFactory configurado
        return new JpaTransactionManager(reportesEntityManagerFactory.getObject());
    }
}
