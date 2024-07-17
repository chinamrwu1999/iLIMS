package com.amswh.iLIMS.configs;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = {"com.amswh.iLIMS.project.mapper.oa"}, sqlSessionFactoryRef = "OASqlSessionFactory")
public class OADataSourceConfig {


    @Bean(name = "oaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.oa")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "OASqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("oaDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "OATransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("oaDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "OASqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("OASqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}