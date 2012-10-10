/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.ccs.thinkfast;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;



/**
 *
 * @author yara
 */
@Configuration
@ComponentScan(basePackages = { "br.com.senac.ccs.thikfast"})
@Profile("mocks")

public class AppConfig extends WebMvcConfigurationSupport {
    
    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName( "index" );
    }
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix( "/" );
        resolver.setSuffix( ".jsp" );
        return resolver;
    }
    @Override
    protected void addViewResourceHandlers( ResourceHandlerRegistry: registry.addResourceHandler( "/resources/*") ) {
    
    
    
}
