package services.ntr.pms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.annotation.DefaultController;
import services.ntr.pms.model.incentive.IncentiveSummaryTableRow;
import services.ntr.pms.model.incentive.IncentivePayoutAmountsTableRow;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.service.clanInformation.IncentiveService;

@DefaultController
public class ClanInfoController{

	@Autowired
	private IncentiveService incentiveService;
	
	@RequestMapping(value = "/attendance")
	public ModelAndView getAttendancePage() {

		ModelAndView mav = new ModelAndView("attendance");
		return mav;

	}
	
	@RequestMapping(value = "/incentive")
	public ModelAndView getIncentivePage(HttpSession session) {

		ClanInfo clanInfo = (ClanInfo) session.getAttribute("clanInfo");
		
		long clanId = clanInfo.getClanId();
		
		List<IncentiveSummaryTableRow> incentiveSummaryTableRows = incentiveService.getIncentiveSummaryTableRows(clanId);
		List<IncentivePayoutAmountsTableRow> clanTankIncentiveAmountTableRows = incentiveService.getClanTankIncentiveAmountTableRows(clanId);
		
		ModelAndView mav = new ModelAndView("incentive");
		mav.addObject("incentiveSummaryTableRows", incentiveSummaryTableRows);
		mav.addObject("clanTankIncentiveAmountTableRows", clanTankIncentiveAmountTableRows);
		
		return mav;

	}
	
}