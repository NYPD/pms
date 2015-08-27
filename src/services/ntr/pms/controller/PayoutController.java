package services.ntr.pms.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.annotation.DefaultController;
import services.ntr.pms.model.history.TransactionHistory;
import services.ntr.pms.model.incentive.IncentivePayoutAmountsTableRow;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.ClanMember;
import services.ntr.pms.model.information.LocalClanSettings;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.payout.ClanAccount;
import services.ntr.pms.service.clanInformation.IncentiveService;
import services.ntr.pms.service.history.TransactionHistoryService;
import services.ntr.pms.service.information.LocalClanSettingsService;
import services.ntr.pms.service.information.TankInformationService;
import services.ntr.pms.service.payout.AttendancePayoutService;

@DefaultController
public class PayoutController{

	@Autowired
	private AttendancePayoutService<ClanAccount> payoutService;
	@Autowired
	private TankInformationService tankInformationService;
	@Autowired 
	private TransactionHistoryService transactionHistoryService;
	@Autowired
	@Qualifier("CallerAttendancePayoutService")
	private AttendancePayoutService<ClanAccount> callerPayoutService;
	@Autowired
	private LocalClanSettingsService localClanSettingsService;
	@Autowired
	private IncentiveService incentiveService;
	
	@RequestMapping(value = "/payouts")
	public ModelAndView getPayoutPage(HttpSession session) {
		//TODO check user for admin'ness
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		ClanInfo clanInfo = (ClanInfo) session.getAttribute("clanInfo");
		
		long clanId = membersInfo.getClanId();
		List<ClanMember> clanMemberList = clanInfo.getMembers();
		
		ClanAccount clanAccount = new ClanAccount();
		clanAccount.setClanId(clanId);
		
		int unpaidShareCount = payoutService.getShares(clanAccount);
		
		List<IncentivePayoutAmountsTableRow> clanTankIncentiveAmountTableRows = incentiveService.getClanTankIncentiveAmountTableRows(clanId);
		List<TransactionHistory> transactionHistoryList = transactionHistoryService.getTransactionHistory(clanId);
		int unpaidCallerBattles = callerPayoutService.getShares(clanAccount);
		
		LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		int callerAmountPerBattle = localClanSettings.getCallerAmountPerBattle();
		
		Collections.sort(clanMemberList, ClanMember.ACCOUNT_NAME_ORDER);
		Collections.sort(clanTankIncentiveAmountTableRows, IncentivePayoutAmountsTableRow.TANK_NAME_ORDER);
		
		ModelAndView mav = new ModelAndView("payouts");
		
		mav.addObject("unpaidShareCount", unpaidShareCount);
		mav.addObject("clanTankIncentiveAmountTableRows", clanTankIncentiveAmountTableRows);
		mav.addObject("clanMemberList", clanMemberList);
		mav.addObject("transactionHistoryList", transactionHistoryList);
		mav.addObject("unpaidCallerBattles", unpaidCallerBattles);
		mav.addObject("callerAmountPerBattle", callerAmountPerBattle);

		return mav;

	}
}
