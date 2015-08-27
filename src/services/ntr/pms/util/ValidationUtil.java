package services.ntr.pms.util;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility Class containing various static methods used to validate different sections of PMS
 * 
 * @author NYPD
 *
 */
public class ValidationUtil{

	public static boolean checkIfValidSession(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Object player = session.getAttribute("player");
		Object memberInfo = session.getAttribute("memberInfo");
		Object clanInfo = session.getAttribute("clanInfo");
		
		boolean doesPmsAuthenticationExist = memberInfo != null;
		boolean doesPlayerExist = player != null;
		boolean doesClanInfoExist = clanInfo != null;
		
		boolean sessionAttributesAreValid = (doesPmsAuthenticationExist && doesPlayerExist && doesClanInfoExist);

		return sessionAttributesAreValid;
		
	}
	
	public static boolean checkIfValidCookies(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		
		boolean thereAreNoCookies = cookies == null;
		
		if(thereAreNoCookies) return false;
		
		Map<String, String> cookieMap = CookieJarUtil.createKeyValueCookieMap(request);
		
		String accountId = cookieMap.get("accountId");
		String accessToken = cookieMap.get("accessToken");
		
		boolean hasAccountIDCookie = accountId != null;
		boolean hasAccessTokenCookie = accessToken != null;
		
		boolean areAllCookiesValid = hasAccountIDCookie && hasAccessTokenCookie;
		
		return areAllCookiesValid;
		
	}
	
	public static boolean checkIfValidAccountId(long accountId) {
		
		String accountIdAsString = Long.toString(accountId);
		
		long accountIdLength = accountIdAsString.length();
		
		boolean isValidAccountId = accountIdLength == 10;
		
		return isValidAccountId;
		
	}
	
	public static boolean checkIfValidClanId(long clanId) {
		
		String clanIdAsString = Long.toString(clanId);
		
		long clanIdLength = clanIdAsString.length();

		boolean isValidClanId = clanIdLength == 10;
		
		return isValidClanId;
		
	}
}
