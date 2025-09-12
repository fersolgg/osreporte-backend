package com.tuxpan.soportesw.osreporte_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class ReportesDataSourceConfig {
    @Bean(name = "reportesDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.reportes")
    public DataSource reportesDataSource() {
        return DataSourceBuilder.create().build();
    }
}
