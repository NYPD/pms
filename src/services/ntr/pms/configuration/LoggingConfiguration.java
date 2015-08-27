package services.ntr.pms.configuration;

import java.io.FileNotFoundException;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.Log4jConfigurer;

/**
* This class is use to initialize log4j.
* @author Tomas Santos
*/
@Configuration
public class LoggingConfiguration
{
    @PostConstruct
    public void initLog4j() throws FileNotFoundException 
    {
        Log4jConfigurer.initLogging("classpath:resource/logging/log4j.xml");
    }
}
