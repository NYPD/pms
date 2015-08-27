package services.ntr.pms.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import services.ntr.pms.controller.Controller;
import services.ntr.pms.interceptor.Interceptor;
import services.ntr.pms.interceptor.SecurityInterceptor;
import services.ntr.pms.util.UserRole;

/**
* This class is use to 
* configure Spring MVC.
*
* @author Tomas Santos
*/
@Configuration
@Import(value={ApplicationConfiguration.class})
@ComponentScan(basePackageClasses={Controller.class, Interceptor.class})
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter
{	
	@Bean
	public ViewResolver getViewResolver()
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	SecurityInterceptor systemAdminSecurityInterceptor = getSystemAdminSecurityInterceptor();
    	SecurityInterceptor clanAdminSecurityInterceptor = getClanAdminSecurityInterceptor();
    	
    	registry.addInterceptor(systemAdminSecurityInterceptor).addPathPatterns("/admin/system/**");
    	registry.addInterceptor(clanAdminSecurityInterceptor).addPathPatterns("/payouts/**").addPathPatterns("/admin/**");
    }

	private SecurityInterceptor getSystemAdminSecurityInterceptor(){
		SecurityInterceptor securityInterceptor = new SecurityInterceptor();
		securityInterceptor.addAllowedRole(UserRole.SYSTEM_ADMIN.getId());
		return securityInterceptor;
	}
	private SecurityInterceptor getClanAdminSecurityInterceptor(){
		SecurityInterceptor securityInterceptor = new SecurityInterceptor();
		securityInterceptor.addAllowedRole(UserRole.CLAN_ADMIN.getId());
		securityInterceptor.addAllowedRole(UserRole.SYSTEM_ADMIN.getId());
		return securityInterceptor;
	}
}
