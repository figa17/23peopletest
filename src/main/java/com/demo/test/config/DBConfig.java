package com.demo.test.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Created by Felipe González Alfaro on 18-03-20.
 */
@Configuration
@PropertySource(value = {
        "classpath:application.properties",
        //"file:${user.home}/docker_app.properties"
})
public class DBConfig {


    @Value("${db.driver.app}")
    private String driverClass;

    @Value("${db.url.app}")
    private String url;

    @Value("${db.username.app}")
    private String user;

    @Value("${db.password.app}")
    private String pass;

    @Bean
    public DataSource dataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(this.driverClass);
        dataSourceConfig.setJdbcUrl(this.url);
        dataSourceConfig.setUsername(this.user);
        dataSourceConfig.setPassword(this.pass);

        return new HikariDataSource(dataSourceConfig);

    }
}
