package services.ntr.pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.model.information.Player;
import services.ntr.pms.service.access.LoginService;

@RestController
public class HeaderRestController{

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/update-user-info")
	public void updateUserInfo(HttpSession session){
		
		Player player = (Player) session.getAttribute("player");
		
		String accessToken = player.getAccessToken();
		long accountId = player.getAccountId();
		
		player = loginService.getPlayerInformation(Long.toString(accountId), accessToken);
		
		session.setAttribute("player", player);
	}
	
	@RequestMapping(value = "/get-top-header")
	public ModelAndView getTopHeader(HttpServletRequest request) {

		//FIXME a jsp in jsp-fragment folder doesn't make sense. move outside folder.
		ModelAndView mav = new ModelAndView("jsp-fragment/top-header");
		return mav;

	}
	
}
