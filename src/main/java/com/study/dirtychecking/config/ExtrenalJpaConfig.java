package com.study.dirtychecking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "externalEntityManager",
        transactionManagerRef = "externalTransactionManager",
        basePackages = "com.study.dirtychecking.service"
)
public class ExtrenalJpaConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource externalDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getProperty("spring.external.datasource.driver-class-name"));
        ds.setUrl(env.getProperty("spring.external.datasource.url"));
        ds.setUsername(env.getProperty("spring.external.datasource.username"));
        ds.setPassword(env.getProperty("spring.external.datasource.password"));

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean externalEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(externalDataSource());
        em.setPackagesToScan(new String[]{"com.anymobi.manager.external"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.hbm2ddl.import_files", env.getProperty("spring.external.datasource.data"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager externalTransactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(externalEntityManager().getObject());

        return tm;
    }
}
