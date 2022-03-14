package com.xxgame.logsrv.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 主数据源配置
 */
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef="primaryEntityManager",
        transactionManagerRef="primaryTransactionManager",
        basePackages= { "com.xxgame.logsrv.module.collectlog" })
public class PrimaryJpaConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;
    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private PrimaryDataSourceConfig primaryDataSourceConfig;
    @Autowired
    private JpaProperties jpaProperties;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManager() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(this.primaryDataSource());
        factory.setPackagesToScan(new String[] { "com.xxgame.logsrv.module.collectlog" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factory.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.putAll(jpaProperties.getProperties());
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");

        factory.setJpaPropertyMap(properties);

        return factory;
    }

    /**
     * 主数据源
     * @return
     */
    @Bean
    @Primary
    public DataSource primaryDataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(primaryDataSourceConfig.getUrl());
        datasource.setUsername(primaryDataSourceConfig.getUsername());
        datasource.setPassword(primaryDataSourceConfig.getPassword());
        datasource.setDriverClassName(dataSourceConfig.getDriver());
        datasource.setInitialSize(dataSourceConfig.getInitialSize());
        datasource.setMinIdle(dataSourceConfig.getMinIdle());
        datasource.setMaxActive(dataSourceConfig.getMaxActive());
        datasource.setMaxWait(dataSourceConfig.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(dataSourceConfig.getValidationQuery());
        datasource.setTestWhileIdle(dataSourceConfig.isTestWhileIdle());
        datasource.setTestOnBorrow(dataSourceConfig.isTestOnBorrow());
        datasource.setTestOnReturn(dataSourceConfig.isTestOnReturn());
        datasource.setPoolPreparedStatements(dataSourceConfig.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(dataSourceConfig.getFilters());
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }

        datasource.setConnectionProperties(dataSourceConfig.getConnectionProperties());
        //datasource.setTimeBetweenLogStatsMillis(15 * 60 * 1000L);

        return datasource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager primaryTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(this.primaryEntityManager().getObject());
        return transactionManager;
    }

}
