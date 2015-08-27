package services.ntr.pms.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.annotation.DefaultController;

@DefaultController
@RequestMapping(value = "/admin/system")
public class SystemAdminController{
	
	@RequestMapping(value = "/administration-tools")
	public ModelAndView getSystemAdministrationPage() {

		ModelAndView mav = new ModelAndView("system-admin");
		return mav;

	}
}
