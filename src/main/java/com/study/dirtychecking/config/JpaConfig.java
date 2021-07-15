package com.study.dirtychecking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "internalEntityManager",
        transactionManagerRef = "internalTransactionManager",
        basePackages = "com.study.dirtychecking.entity"
)
public class JpaConfig {
    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource internalDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getProperty("spring.internal.datasource.driver-class-name"));
        ds.setUrl(env.getProperty("spring.internal.datasource.url"));
        ds.setUsername(env.getProperty("spring.internal.datasource.username"));
        ds.setPassword(env.getProperty("spring.internal.datasource.password"));
        return ds;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean internalEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(internalDataSource());
        em.setPackagesToScan(new String[]{"com.study.dirtychecking.entity"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.hbm2ddl.import_files", env.getProperty("spring.internal.datasource.data"));
//        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager internalTransactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(internalEntityManager().getObject());

        return tm;
    }
}

