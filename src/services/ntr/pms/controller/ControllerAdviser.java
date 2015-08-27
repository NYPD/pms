package services.ntr.pms.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.annotation.DefaultController;
import services.ntr.pms.exception.InvalidAccessTokenException;
import services.ntr.pms.exception.WargamingAuthenticationError;

@ControllerAdvice(annotations = DefaultController.class)
public class ControllerAdviser {
    private static Logger logger = Logger.getLogger(ControllerAdviser.class);
    
    @ExceptionHandler(value = InvalidAccessTokenException.class)
    public ModelAndView handleInvalidAccessTokenException(InvalidAccessTokenException ex){
    	logger.info("hit handleInvalidAccessTokenException", ex);
        return new ModelAndView("redirect:/entry/login");
    }
    
    @ExceptionHandler(value = WargamingAuthenticationError.class)
    public ModelAndView handleWargamingAuthenticationError(WargamingAuthenticationError ex){
    	logger.info("hit handleWargamingAuthenticationError", ex);
    	
    	boolean loginError = true;
    	
    	ModelAndView mav = new ModelAndView("login");
    	mav.addObject("loginError", loginError);
        return mav;
    }
    
    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleGeneralException(Exception ex){
        logger.info("hit handleGeneralException", ex);
        return new ModelAndView("redirect:/error/general-error");
    }
}