package com.tuxpan.soportesw.osreporte_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class OsticketDataSourceConfig {
	@Bean(name = "osticketDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.osticket")
	public DataSource osticketDataSource() {
		return DataSourceBuilder.create().build();
	}
}
