package services.ntr.pms.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import services.ntr.pms.dao.DataAccess;
import services.ntr.pms.factory.Factory;
import services.ntr.pms.proxy.contentprovider.ContentProvider;
import services.ntr.pms.proxy.contentprovider.PostContentProvider;
import services.ntr.pms.service.Service;
import services.ntr.pms.util.ApplicationConstants;

/**
* This class is use to instantiate 
* and configure beans defined
* for application context. Similar 
* to application-context.xml.
* Imports all xml and java
* configuration pertaining to
* application context.
*
* @author Tomas Santos
*/
@Configuration
@Import(value={LoggingConfiguration.class, MyBatisConfiguration.class})
@ComponentScan(basePackageClasses={DataAccess.class, Service.class, Factory.class})
public class ApplicationConfiguration {
	
	@Bean
	public ObjectMapper objectMapper() {
		PropertyNamingStrategy propertyNamingStrategy = PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES;
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}
	
	@Bean
	public ContentProvider contentProvider() {
		
		Map<String, String> globalParameters = new HashMap<>();
		globalParameters.put("application_id", ApplicationConstants.SERVER_APPLICATION_ID);
		
		String resource = ApplicationConstants.WORLD_OF_TANKS_URI;
		
		PostContentProvider postContentProvider = new PostContentProvider();
		postContentProvider.setResource(resource);
		postContentProvider.setGlobalParameters(globalParameters);
		return postContentProvider;
	}

}

