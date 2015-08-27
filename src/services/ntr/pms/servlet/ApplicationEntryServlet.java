package services.ntr.pms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ntr.pms.util.ValidationUtil;

/**
 * This class is where all requests to the PMS system gets routed when the user tries to access the system
 * thanks to the welcome file tag in web.xml. This checks to see if the user is already authenticated in the system
 * via checking if the user contains appropriate objects in the session. If they are authenticated, users are sent
 * directly to the home page, if they are not, users get sent to the login page. 
 * 
 * Users can avoid hitting this class by accessing a different uri, but the session authentication filter will intercept that.
 * 
 * @author NYPD
 *
 */
public class ApplicationEntryServlet extends HttpServlet{

	private static final long serialVersionUID = -6091186592943433815L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean isSessionAuthenticated = ValidationUtil.checkIfValidSession(request);
		
		if(isSessionAuthenticated){
			response.sendRedirect("/pms/app/home");
			return;
		}
		
		boolean isCookieAuthenticated = ValidationUtil.checkIfValidCookies(request);
		
		if(isCookieAuthenticated){
			response.sendRedirect("/pms/entry/setUserSessionInfo");
			return;
		}
		
		response.sendRedirect("/pms/entry/login");
		
	}

}
