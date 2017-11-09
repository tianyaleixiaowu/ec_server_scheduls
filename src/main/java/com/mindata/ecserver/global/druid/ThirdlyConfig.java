package com.mindata.ecserver.global.druid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by wuweifeng on 2017/10/10.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryThirdly",
        transactionManagerRef = "transactionManagerThirdly",
        basePackages = {"com.mindata.ecserver.main.repository.thirdly"})
public class ThirdlyConfig {

    @Resource
    @Qualifier("thirdlyDataSource")
    private DataSource thirdlyDataSource;

    @Bean(name = "entityManagerThirdly")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryThirdly(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryThirdly")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryThirdly(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(thirdlyDataSource)
                .properties(getVendorProperties(thirdlyDataSource))
                .packages("com.mindata.ecserver.main.model.thirdly")
                .persistenceUnit("thirdlyPersistenceUnit")
                .build();
    }

    @Resource
    private JpaProperties jpaProperties;

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "transactionManagerThirdly")
    PlatformTransactionManager transactionManagerThirdly(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryThirdly(builder).getObject());
    }

}
