package ru.max.nc.ncapp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

import javax.sql.DataSource;

@Configuration
@EnableJdbcHttpSession
@ConditionalOnProperty(value = "spring.session.store-type", havingValue = "jdbc")
public class JdbcSessionConfig extends AbstractHttpSessionApplicationInitializer {

    @Bean
    @ConfigurationProperties("spring.session.database")
    public DataSourceProperties sessionDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @SpringSessionDataSource
    public DataSource springSessionDataSource() {
        return sessionDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
}