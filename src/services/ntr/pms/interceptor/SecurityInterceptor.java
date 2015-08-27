package services.ntr.pms.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.model.access.Role;
import services.ntr.pms.model.access.User;

public class SecurityInterceptor implements HandlerInterceptor 
{
	private List<Integer> allowedRoles;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception
	{}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception
	{}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception{
		
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();
		User user = (User) session.getAttribute("user");
		Role role = user.getRole();
		int roleId = role.getRoleId();
		boolean hasAllowedRoles =  allowedRoles.contains(roleId);
		boolean shouldAllowAccess = hasAllowedRoles;
		
		if(!shouldAllowAccess){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.sendRedirect(contextPath + "/app/error/404");
		}	
		
		return shouldAllowAccess; 
	}
	public List<Integer> getAllowedRoles()
	{
		return allowedRoles;
	}
	public void setAllowedRoles(List<Integer> allowedRoles)
	{
		this.allowedRoles = allowedRoles;
	}
	public void addAllowedRole(int roleId){
		setEmptyListIfApplicable();
		this.allowedRoles.add(roleId);
	}
	private void setEmptyListIfApplicable(){
		boolean allowedRolesIsNull = allowedRoles == null;
		if (allowedRolesIsNull) allowedRoles = new ArrayList<Integer>();
	}

}
