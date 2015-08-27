package services.ntr.pms.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Controller;

/**
* @ControllerAdvice
*/
@Target(value=ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface DefaultController
{}
