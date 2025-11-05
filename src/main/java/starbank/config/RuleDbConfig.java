package starbank.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.starbank.rule.repository",
        entityManagerFactoryRef = "ruleEntityManagerFactory",
        transactionManagerRef = "ruleTransactionManager"
)
public class RuleDbConfig {

    @Bean
    @ConfigurationProperties("rule.datasource")
    public DataSourceProperties ruleDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource ruleDataSource(DataSourceProperties ruleDataSourceProperties) {
        return ruleDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean ruleEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("ruleDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.starbank.rule.model") // Указываем, где лежат Entity для этой БД
                .persistenceUnit("rule")
                .build();
    }

    @Bean
    public PlatformTransactionManager ruleTransactionManager(
            @Qualifier("ruleEntityManagerFactory") LocalContainerEntityManagerFactoryBean ruleEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(ruleEntityManagerFactory.getObject()));
    }
}