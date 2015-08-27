package services.ntr.pms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Profile;


/**
* @ActiveProfiles
* @author Tomas Santos 600132830
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile("UNITTEST")
public @interface UnitProfile
{}
