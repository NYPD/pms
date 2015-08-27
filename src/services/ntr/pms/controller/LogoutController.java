package services.ntr.pms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ntr.pms.annotation.DefaultController;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.service.access.LogoutService;
import services.ntr.pms.util.CookieJarUtil;

@DefaultController
public class LogoutController{

	@Autowired
	private LogoutService logoutService;
	
	@RequestMapping(value = "/logout")
	public void logout(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
		
		Player player = (Player) session.getAttribute("player");
		String accessToken = player.getAccessToken();
		logoutService.logoutPlayer(accessToken);
		
		response = clearCookies(request, response);
		
		session.invalidate();
		
		response.sendRedirect("/pms/entry/login");
		
	}
	
	private HttpServletResponse clearCookies(HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Cookie> keyCookieMap = CookieJarUtil.createKeyCookieMap(request);
		
		List<Cookie> cookieList = new ArrayList<Cookie>();
		
		Cookie accessTokenCookie = keyCookieMap.get("accessToken");
		Cookie accountIdCookie = keyCookieMap.get("accountId");
		
		boolean emptyCookies = accessTokenCookie == null && accountIdCookie == null;
		
		if(emptyCookies) return response;
		
		cookieList.add(accessTokenCookie);
		cookieList.add(accountIdCookie);
		
		for(Cookie cookie: cookieList){
			
			cookie.setValue(null);
			cookie.setMaxAge(0);
			cookie.setPath("/pms/");
			
			response.addCookie(cookie);
		}
	
		return response;
		
	}
}
