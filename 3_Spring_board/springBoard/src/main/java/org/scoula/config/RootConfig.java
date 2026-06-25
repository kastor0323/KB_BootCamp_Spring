package org.scoula.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration

// @Transactional을 사용할 수 있도록 설정합니다.
@EnableTransactionManagement

// application.properties 파일을 읽습니다.
@PropertySource("classpath:application.properties")

// Mapper 인터페이스가 있는 패키지를 검색합니다.
@MapperScan(basePackages = "org.scoula.mapper")

// Service 등의 클래스를 Bean으로 등록합니다.
// Controller는 ServletConfig가 관리하므로 제외합니다.
@ComponentScan(
        basePackages = "org.scoula",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = Controller.class
                )
        }
)
public class RootConfig {

    private final ApplicationContext applicationContext;

    public RootConfig(
            ApplicationContext applicationContext) {

        this.applicationContext =
                applicationContext;
    }

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {

        HikariConfig config =
                new HikariConfig();

        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        // 커넥션 풀의 최대 연결 개수입니다.
        config.setMaximumPoolSize(10);

        // 최소 유휴 커넥션 개수입니다.
        config.setMinimumIdle(2);

        return new HikariDataSource(config);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory()
            throws Exception {

        SqlSessionFactoryBean factoryBean =
                new SqlSessionFactoryBean();

        // MyBatis가 사용할 DataSource를 연결합니다.
        factoryBean.setDataSource(dataSource());

        // Mapper XML 파일의 위치를 지정합니다.
        factoryBean.setMapperLocations(
                applicationContext.getResources(
                        "classpath:/mappers/**/*.xml"
                )
        );

        // VO 클래스 패키지를 별칭 패키지로 등록합니다.
        factoryBean.setTypeAliasesPackage(
                "org.scoula.domain"
        );

        return factoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager
    transactionManager() {

        // Spring 트랜잭션 관리자를 등록합니다.
        return new DataSourceTransactionManager(
                dataSource()
        );
    }

}