package com.tuxpan.soportesw.osreporte_backend.config;

// Importa las anotaciones y clases necesarias para la configuración del DataSource
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

// Indica que esta clase es una clase de configuración de Spring
@Configuration
public class ReportesDataSourceConfig {
    // Define un bean llamado "reportesDataSource" que será gestionado por Spring
    @Bean(name = "reportesDataSource")
    // Indica que las propiedades de configuración se tomarán del prefijo "spring.datasource.reportes" en application.yml
    @ConfigurationProperties(prefix = "spring.datasource.reportes")
    public DataSource reportesDataSource() {
        // Crea y retorna el DataSource usando los datos de configuración
        return DataSourceBuilder.create().build();
    }
}
