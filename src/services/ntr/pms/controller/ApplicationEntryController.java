package services.ntr.pms.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.annotation.DefaultController;
import services.ntr.pms.exception.InvalidAccessTokenException;
import services.ntr.pms.exception.WargamingAuthenticationError;
import services.ntr.pms.model.access.User;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.service.access.LoginService;
import services.ntr.pms.service.access.LogoutService;
import services.ntr.pms.service.access.SecurityService;
import services.ntr.pms.service.access.UserService;
import services.ntr.pms.util.CookieJarUtil;

@DefaultController
public class ApplicationEntryController {
	
	private static final int COOKIE_EXPIRATION_TIME = 604800;//604800 is 7 days

	@Autowired
	private LoginService loginService;
	@Autowired
	private LogoutService logoutService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login")
	public ModelAndView getLoginPage() {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@RequestMapping(value = "/not-valid-clan")
	public ModelAndView getNotNTRPage() {
		ModelAndView mav = new ModelAndView("error/not-valid-clan");
		return mav;
	}
	
	@RequestMapping(value = "/openIDAuthentication")
	public void openIDAuthentication(HttpServletResponse response, HttpSession session, @RequestParam boolean rememberMe) throws IOException {
		String loginRedirectURL = loginService.getLoginRedirectURL(rememberMe);
		response.sendRedirect(loginRedirectURL);
	}

	@RequestMapping(value = "/verifyLogin")
	public void verifyLogin(HttpServletResponse response, HttpServletRequest request, HttpSession session, @RequestParam boolean rememberMe) throws IOException{
		
		String accountIdAsString = request.getParameter("account_id");
		String accessToken = request.getParameter("access_token");
		String requestURI = request.getParameter("requestURI");
		String status = request.getParameter("status");
		String message = request.getParameter("message");
		String errorCode = request.getParameter("code");
		
		boolean statusIsError = "error".equals(status);
		
		if(statusIsError) throw new WargamingAuthenticationError(message, errorCode);

		Player player = loginService.getPlayerInformation(accountIdAsString, accessToken);
		
		boolean playerHasPrivates = player.getPrivate() != null;
		boolean playerDoesNotHaveAccess = !playerHasPrivates;

		if(playerDoesNotHaveAccess) throw new InvalidAccessTokenException();
		
		MembersInfo membersInfo = loginService.getMemberInformation(accountIdAsString, accessToken);
		long clanId = membersInfo.getClanId();
		
		ClanInfo clanInfo = loginService.getClanInformation(clanId, accessToken);
		
		long accountId = player.getAccountId();
		User user = userService.getUser(accountId);
		
		boolean isValidClan = securityService.checkIfValidClan(clanId);
		boolean shouldRemeberPlayer = rememberMe && isValidClan;
		boolean hasRequestURI = requestURI != null;
		
		String redirectURI = hasRequestURI ? requestURI : "../app/home";
		
		if(shouldRemeberPlayer) rememberPlayer(response, player);
		
		if(isValidClan){
			
			session.setAttribute("player", player);
			session.setAttribute("memberInfo", membersInfo);
			session.setAttribute("clanInfo", clanInfo);
			session.setAttribute("user", user);
			
			response.sendRedirect(redirectURI);
		}
		else{
			response.sendRedirect("/pms/error/not-valid-clan");
		}
	}
	
	@RequestMapping(value = "/setUserSessionInfo")
	public ModelAndView setUserSessionInfo(HttpServletRequest request, HttpSession session) throws IOException {

		Map<String,String> cookieMap = CookieJarUtil.createKeyValueCookieMap(request);
		
		String accountId = cookieMap.get("accountId");
		String accessToken = cookieMap.get("accessToken");
		String requestURI = (String) request.getParameter("attemptedURI");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/entry/verifyLogin");
		modelAndView.addObject("status", "ok");
		modelAndView.addObject("account_id", accountId);
		modelAndView.addObject("access_token", accessToken);
		modelAndView.addObject("rememberMe", true);
		modelAndView.addObject("requestURI", requestURI);
		return modelAndView;

	}
	
	private void rememberPlayer(HttpServletResponse response, Player player) {
		
		Cookie[] userCookies = createUserCookies(player);

		for(Cookie userCookie: userCookies){
			response.addCookie(userCookie);
		}
	}
	
	private Cookie[] createUserCookies (Player player) {	
		
		String accessToken = player.getAccessToken();
		String accountIdAsString = player.getAccountIdAsString();

		Cookie accountIdCookie = new Cookie("accountId", accountIdAsString);
		Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
		
		Cookie[] userCookies = {accountIdCookie, accessTokenCookie};
		
		for(Cookie cookie: userCookies){
			cookie.setPath("/pms/");
			cookie.setMaxAge(COOKIE_EXPIRATION_TIME);
		}	
			
		return userCookies;
	}
	
}
