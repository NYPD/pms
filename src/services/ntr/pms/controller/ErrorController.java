package services.ntr.pms.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.annotation.DefaultController;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;


@DefaultController
public class ErrorController{
	
	@RequestMapping("/404")
	public ModelAndView load404ErrorPage(HttpSession session){
		
		boolean hasSessionInfo = doesUserHaveSessionInfo(session);
		
		Random random = new Random();
		int randomNumber = random.nextInt((2 - 0) + 1) + 0;
		
		ModelAndView modelAndView = new ModelAndView("error/404");
		modelAndView.addObject("randomNumber", randomNumber);
		modelAndView.addObject("hasSessionInfo", hasSessionInfo);
		return modelAndView;
	
	}
	
	@RequestMapping("/general-error")
	public ModelAndView loadGeneralErrorPage(HttpSession session){
	
		boolean hasNoSessionInformation = !doesUserHaveSessionInfo(session);
		
		String mavPage = hasNoSessionInformation ? "redirect:/entry/login" : "error/general-error";
		
		return new ModelAndView(mavPage);
	}
	
	private boolean doesUserHaveSessionInfo(HttpSession session){
		
		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		boolean nullPlayer = player == null;
		boolean playerHasNoPrivates = nullPlayer ? true : player.getPrivate() == null;
		
		boolean emptyPlayer = nullPlayer || playerHasNoPrivates;
		boolean emptyMemberInfo = membersInfo == null;
		
		boolean hasSessionInformation = !(emptyPlayer || emptyMemberInfo);
		
		return hasSessionInformation;
	}
}
