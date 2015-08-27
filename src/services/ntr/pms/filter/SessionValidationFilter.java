package services.ntr.pms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ntr.pms.util.ValidationUtil;

/**
 * Main filter class that gets called in every request. This makes sure users in the system are always authenticated and verified, and
 * can't bypass the login/application entry methods.
 * 
 * @author NYPD
 *
 */
public class SessionValidationFilter implements Filter {

	@Override
	public void destroy()
	{}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		boolean notValidSession = !ValidationUtil.checkIfValidSession(request);

		if(notValidSession){
			
			boolean isCookieAuthenticated = ValidationUtil.checkIfValidCookies(request);
			
			if(isCookieAuthenticated){
				String attemptedURI = request.getRequestURI();
				response.sendRedirect("/pms/entry/setUserSessionInfo?attemptedURI=" + attemptedURI);
			}else{
				response.sendRedirect("/pms/entry/login");
			}
			
			return;
		}
		
		chain.doFilter(req, resp);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{}

}
