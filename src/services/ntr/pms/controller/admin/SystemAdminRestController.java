package services.ntr.pms.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.exception.ClanAlreadyIsAllowedException;
import services.ntr.pms.exception.InvalidAccountId;
import services.ntr.pms.exception.InvalidClanId;
import services.ntr.pms.model.access.AllowedClan;
import services.ntr.pms.model.access.Role;
import services.ntr.pms.model.access.User;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.service.access.SecurityService;
import services.ntr.pms.service.access.UserService;
import services.ntr.pms.service.information.ClanInformationService;
import services.ntr.pms.service.information.PlayerInformationService;
import services.ntr.pms.util.ValidationUtil;

@RestController
@RequestMapping(value = "/admin/system")
public class SystemAdminRestController {
	
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserService userService;
	@Autowired
	private PlayerInformationService playerInformationService;
	@Autowired
	private ClanInformationService clanInformationService;
	
	private static final String MODIFY_PERMISSIONS_TOOL = "jsp-fragment/admin-tools/system-tools/modify-privileges";
	private static final String ADMIN_ADD_FORM = "jsp-fragment/admin-tools/system-tools/modal-content/put-admin";
	private static final String CLAN_ADD_FORM = "jsp-fragment/admin-tools/system-tools/modal-content/add-clan";
	private static final String ADMIN_DELETE_FORM = "jsp-fragment/admin-tools/system-tools/modal-content/delete-admin";
	private static final String CLAN_DELETE_FORM = "jsp-fragment/admin-tools/system-tools/modal-content/delete-clan";
	private static final String USER_GROUPS_TOOL = "jsp-fragment/admin-tools/system-tools/user-groups";

	@RequestMapping(value = "/get-modify-privileges-tool", method = RequestMethod.GET)
	public ModelAndView getModifyPrivilegesTool(HttpSession session) {
		
		List<User> admins = securityService.getAllAdmins();
		List<AllowedClan> allowedClans = securityService.getAllAllowedClans();

		ModelAndView mav= new ModelAndView(MODIFY_PERMISSIONS_TOOL);
		mav.addObject("admins", admins);
		mav.addObject("allowedClans", allowedClans);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/get-admin-add-modal", method = RequestMethod.GET)
	public ModelAndView getAdminAddModal(HttpSession session) {
		
		List<Role> roles = securityService.getAllRoles();
		
		ModelAndView mav= new ModelAndView(ADMIN_ADD_FORM);
		mav.addObject("roles", roles);
		
		return mav;
	}
	
	@RequestMapping(value = "/get-admin-edit-modal", method = RequestMethod.GET)
	public ModelAndView getAdminEditModal(long accountId, HttpSession session) {
		
		User admin = userService.getUser(accountId);
		
		List<Role> roles = securityService.getAllRoles();
		
		ModelAndView mav= new ModelAndView(ADMIN_ADD_FORM);
		mav.addObject("roles", roles);
		mav.addObject("admin", admin);
		
		return mav;
	}
	
	@RequestMapping(value = "/get-admin-delete-modal", method = RequestMethod.GET)
	public ModelAndView getAdminDeleteModal(@RequestParam(value = "accountId") long accountId, HttpSession session) {
		
		User admin = userService.getUser(accountId);
		
		ModelAndView mav = new ModelAndView(ADMIN_DELETE_FORM);
		mav.addObject("admin", admin);
		
		return mav;
	}
	
	@RequestMapping(value = "/add-admin", method = RequestMethod.POST)
	public void addAdmin(User user, HttpSession session) {
		
		long accountId = user.getAccountId();
		
		boolean invalidAccountId = !ValidationUtil.checkIfValidAccountId(accountId);
		
		if(invalidAccountId) throw new InvalidAccountId(accountId);
		
		securityService.addAdmin(user);
		
	}
	
	@RequestMapping(value = "/edit-admin", method = RequestMethod.POST)
	public void editAdmin(User user, HttpSession session) {
		
		securityService.editAdmin(user);
		
	}
	
	@RequestMapping(value = "/delete-admin", method = RequestMethod.POST)
	public void deleteAdmin(@RequestParam(value = "accountId") long accountId, HttpSession session) {
		
		securityService.deleteAdmin(accountId);
		
	}
	
	
	@RequestMapping(value = "/get-clan-add-modal", method = RequestMethod.GET)
	public ModelAndView getClanAddModal(HttpSession session) {
		
		ModelAndView mav= new ModelAndView(CLAN_ADD_FORM);
		
		return mav;
	}
	
	@RequestMapping(value = "/get-clan-delete-modal", method = RequestMethod.GET)
	public ModelAndView getClanDeleteModal(@RequestParam(value = "clanId") long clanId, HttpSession session) {
		
		AllowedClan allowedClan = securityService.getAllowedClan(clanId);
		
		ModelAndView mav = new ModelAndView(CLAN_DELETE_FORM);
		mav.addObject("allowedClan", allowedClan);
		
		return mav;
	}
	
	@RequestMapping(value = "/add-clan", method = RequestMethod.POST)
	public void addClan(AllowedClan allowedClan, HttpSession session) {
		
		long clanId = allowedClan.getClanId();
		
		boolean invalidClanId = !ValidationUtil.checkIfValidClanId(clanId);
		
		if(invalidClanId) throw new InvalidClanId(clanId);
		
		boolean checkIfClanExists = securityService.checkIfClanExists(clanId);
		
		if(checkIfClanExists) throw new ClanAlreadyIsAllowedException(clanId);
		
		ClanInfo clanInformation = clanInformationService.getClanInformation(clanId);
		
		String clanName = clanInformation.getTag();
		allowedClan.setClanName(clanName);
		
		securityService.addAllowedClan(allowedClan);
		
	}
	
	@RequestMapping(value = "/delete-clan", method = RequestMethod.POST)
	public void deleteClan(@RequestParam(value = "clanId") long clanId, HttpSession session) {
		
		securityService.deleteClan(clanId);
		
	}
	
	@RequestMapping(value = "/get-user-groups-tool", method = RequestMethod.GET)
	public ModelAndView getUserGroupsTool(HttpSession session) {
		
		ModelAndView mav= new ModelAndView(USER_GROUPS_TOOL);
		
		return mav;
		
	}
}
