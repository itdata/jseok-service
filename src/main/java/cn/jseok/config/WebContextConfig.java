package cn.jseok.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
//@ImportResource("classpath:classes/initconfig.properties") 只能是xml 文件
@PropertySource("classpath:/initconfig.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "cn.jseok.logs.**")
public class WebContextConfig {

//    @Value("${jdbc.url}")
//    private String url;
//
//    @Value("${jdbc.username}")
//    private String username;
//
//    @Value("${jdbc.password}")
//    private String password;
//
//    @Value("${jdbc.driver}")
//    private String driverClass;



    @Bean(value = "dataSource",destroyMethod = "close")
    public ComboPooledDataSource createDateSource(Environment env) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
        dataSource.setUser("ITDATA");
        dataSource.setPassword("HWL712910");
        dataSource.setJdbcUrl("jdbc:oracle:thin:@127.0.0.1:1521/ORCL");
        return dataSource;
    }

    @Bean(value = "sessionFactory")
    public LocalSessionFactoryBean createSessionFactory(ComboPooledDataSource dataSource){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        Properties properties= new Properties();
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.Oracle9Dialect");
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.format_sql","true");
        sessionFactory.setHibernateProperties(properties);
        return sessionFactory;
    }
    @Bean(value = "transactionManager")

    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager hibernateTransactionManager =  new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

}
