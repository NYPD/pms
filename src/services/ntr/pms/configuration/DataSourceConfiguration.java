package services.ntr.pms.configuration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;

@Configuration
public interface DataSourceConfiguration {
    public DataSource getPmsDataSource() throws NamingException;
}
