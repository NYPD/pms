package services.ntr.pms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import services.ntr.pms.annotation.UnitProfile;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
* This class is use to instantiates 
* and configures beans defined for 
* DataSources. Typically used 
* for unit testing
*
* @author Tomas Santos
*/
@Configuration
@EnableTransactionManagement
@UnitProfile
public class EmbeddedDataSourceConfiguration {
	
	@Bean
	public DataSource getPmsDataSource() throws NamingException {	
		
		EmbeddedDatabase datasource = new EmbeddedDatabaseBuilder()
		.setType(EmbeddedDatabaseType.HSQL)
		.addScript("setup/create-pms-database.sql")
		.addScript("setup/insert-pms-data.sql")
		.build();
		
		return datasource;
	}
}
