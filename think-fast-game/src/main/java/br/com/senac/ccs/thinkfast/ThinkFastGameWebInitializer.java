package br.com.senac.ccs.thinkfast;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import static javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author ortolanph
 */
public class ThinkFastGameWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        
        sc.addListener(new ContextLoaderListener(context));
        sc.setInitParameter("defaultHtmlEscape", "true");
        
        Dynamic dispatcher = sc.addServlet("dispatcherServlet", new DispatcherServlet(context));
        
        dispatcher.setLoadOnStartup(1);
        dispatcher.setAsyncSupported(true);
        dispatcher.addMapping("/");
    }

}