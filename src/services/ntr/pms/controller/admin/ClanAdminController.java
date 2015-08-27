package services.ntr.pms.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.annotation.DefaultController;

@DefaultController
@RequestMapping(value = "/admin/clan")
public class ClanAdminController{
	
	@RequestMapping(value = "/administration-tools")
	public ModelAndView getClanAdministrationPage(HttpSession session) {

		ModelAndView mav = new ModelAndView("clan-admin");
		
		return mav;
	}
}
