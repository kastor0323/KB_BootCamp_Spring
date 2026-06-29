package org.scoula.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@MapperScan("org.scoula.mapper")
@ComponentScan(
        basePackages = {
                "org.scoula.service",
                "org.scoula.repository",
                "org.scoula.security"
        },
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = Controller.class
                )
        }
)
public class RootConfig {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    /**
     * HikariCP DataSource를 생성합니다.
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.setPoolName("SecurePortalPool");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);

        return new HikariDataSource(config);
    }

    /**
     * MyBatis SqlSessionFactory를 생성합니다.
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(
            DataSource dataSource
    ) throws Exception {

        SqlSessionFactoryBean factory =
                new SqlSessionFactoryBean();

        factory.setDataSource(dataSource);

        factory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources(
                                "classpath:/org/scoula/mapper/*.xml"
                        )
        );

        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration();

        configuration.setMapUnderscoreToCamelCase(true);

        factory.setConfiguration(configuration);

        return factory.getObject();
    }

    /**
     * Spring 트랜잭션 관리자를 등록합니다.
     */
    @Bean
    public PlatformTransactionManager transactionManager(
            DataSource dataSource
    ) {
        return new DataSourceTransactionManager(dataSource);
    }
}