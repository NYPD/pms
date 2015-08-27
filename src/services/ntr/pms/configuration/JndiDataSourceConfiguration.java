package services.ntr.pms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import services.ntr.pms.annotation.ProductionProfile;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
* This class is use to instantiate
* and configure beans defined for 
* DataSources.
*
* @author Tomas Santos
*/
@Configuration
@ProductionProfile
public class JndiDataSourceConfiguration implements DataSourceConfiguration{

  @Bean
  public DataSource getPmsDataSource() throws NamingException {	
	Context context = new InitialContext();
  	return (DataSource) context.lookup("java:comp/env/jdbc/ds_pms");
  }
}
