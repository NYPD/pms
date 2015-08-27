package services.ntr.pms.configuration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.dao.Mapper;
import services.ntr.pms.model.TypeAliases;

/**
* This class is use to instantiate 
* and configure beans defined
* for MyBatis.
*
* @author Tomas Santos
*/
@Configuration
@Import({JndiDataSourceConfiguration.class})
@EnableTransactionManagement
@MapperScan(basePackageClasses=Mapper.class, annotationClass=DefaultDatabase.class )
public class MyBatisConfiguration
{
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager () throws NamingException {
		DataSource dataSource = getDataSource();

		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);

		return dataSourceTransactionManager;
	}

	@Bean
	public SqlSessionFactory sessionFactory() throws Exception {
		DataSource dataSource = getDataSource();
		String typeAliasesPackage = TypeAliases.class.getPackage().getName();

		SqlSessionFactoryBean sessionFactory 					= new SqlSessionFactoryBean();
		PathMatchingResourcePatternResolver patternResolver 	= new PathMatchingResourcePatternResolver();		 
		Resource[] mapperLocations 								= patternResolver.getResources("classpath:resource/mybatis/mapper/pms/*-mapper.xml");
		sessionFactory.setTypeAliasesPackage(typeAliasesPackage);

		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(mapperLocations);

		return sessionFactory.getObject();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
