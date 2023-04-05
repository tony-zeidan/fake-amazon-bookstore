package fakeamazon.bookstore.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * The configuration class used by the application to manage database interfaces
 */
@Configuration
public class DatabaseConfiguration {
    /**
     * The DataSource is a factory for connections to the physical data backend that the DataSource
     * object represents. It is used by Spring to understand what type of databases are accessible
     * to the application, and to generate appropriate query code for interactions.
     *
     * @return the datasource used by the application
     */
    @Bean
    @ApplicationScope
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }
}
