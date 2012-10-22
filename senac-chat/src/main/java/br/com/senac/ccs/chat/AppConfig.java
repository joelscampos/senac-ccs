/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.ccs.chat;


import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableJpaRepositories( basePackages = { "br.com.senac.ccs.chat"})
@ComponentScan(basePackages = "br.com.senac.ccs.chat")
public class AppConfig extends WebMvcConfigurationSupport {
    
    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName( "index" );
    }
    @Override
    protected void addResourceHandlers( ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "/resources/**").addResourceLocations("resources/");
        
    }
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix( "/" );
        resolver.setSuffix( ".jsp" );
        return resolver;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
        LocalContainerEntityManagerFactoryBean b = new LocalContainerEntityManagerFactoryBean();
        b.setDataSource( dataSource() );
        b.setPackagesToScan( "br.com.senac.ccs.chat" );
        b.setPersistenceProviderClass( org.hibernate.ejb.HibernatePersistence.class );
        b.setJpaDialect( new org.springframework.orm.jpa.vendor.HibernateJpaDialect() );
        Properties props = new Properties();
        props.setProperty( "hibernate.format_sql", "true" );
        props.setProperty( "hibernate.show_sql", "true" );
        props.setProperty( "hibernate.hbm2ddl.auto", "update" );
        props.setProperty( "hibernate.query.substitutions", "true 1, false 0" );
        b.setJpaProperties( props );
        return b;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( entityManagerFactory().getObject() );
        transactionManager.setDataSource( dataSource() );
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl( "jdbc:derby:memory:chatgamedb;create=true" );
        dataSource.setUsername( "survey" );
        dataSource.setPassword( "survey" );
        dataSource.setMaxActive( 10 );
        dataSource.setMinIdle( 10 );
        return dataSource;
    }    
    
    
}
