package services.ntr.pms.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * A utility class that contains helper method regarding handling javax cookies
 * in PMS
 * 
 * @author NYPD
 *
 */
public class CookieJarUtil{

	public static Map<String,String> createKeyValueCookieMap(HttpServletRequest request){
		
		Cookie [] cookies = request.getCookies();
		cookies = CollectionUtil.getEmptyArrayIfNull(cookies);
		
		Map<String,String> cookieMap = new HashMap<>();
		
		for(Cookie cookie: cookies)
		{
			String key = cookie.getName();
			String value = cookie.getValue();
			cookieMap.put(key, value);
		}
		
		return cookieMap;
	}
	
	public static Map<String, Cookie> createKeyCookieMap(HttpServletRequest request){
		
		Cookie [] cookies = request.getCookies();
		cookies = CollectionUtil.getEmptyArrayIfNull(cookies);
		
		Map<String, Cookie> cookieMap = new HashMap<>();
		
		for(Cookie cookie: cookies)
		{
			String key = cookie.getName();
			cookieMap.put(key, cookie);
		}
		
		return cookieMap;
	}
	

}
