package services.ntr.pms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.model.history.TransactionHistoryChartPoint;
import services.ntr.pms.model.incentive.IncentivePayoutAmountsTableRow;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.information.TankInformation;
import services.ntr.pms.model.payout.CampaignReward;
import services.ntr.pms.model.payout.ClanAccount;
import services.ntr.pms.model.payout.IncentiveCompletion;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutScriptInfo;
import services.ntr.pms.model.payout.PlayerBonus;
import services.ntr.pms.model.payout.StandardPayoutInformation;
import services.ntr.pms.service.clanInformation.IncentiveService;
import services.ntr.pms.service.history.TransactionHistoryService;
import services.ntr.pms.service.information.TankInformationService;
import services.ntr.pms.service.payout.AttendancePayoutService;
import services.ntr.pms.service.payout.IncentivePayoutService;
import services.ntr.pms.service.payout.PlayerBonusPayoutService;
import services.ntr.pms.userscript.payout.PayoutScriptGenerator;
import services.ntr.pms.util.PayoutGroupUtil;

@RestController
@RequestMapping("/payouts")
public class PayoutRestController {
	
	@Autowired
	private AttendancePayoutService<ClanAccount> attendancePayoutService;
	@Autowired
	@Qualifier("CallerAttendancePayoutService")
	private AttendancePayoutService<ClanAccount> callerPayoutService;
	@Autowired
	private IncentivePayoutService incentivePayoutService;
	@Autowired
	private IncentiveService incentiveService;
	@Autowired
	private TankInformationService tankInformationService;
	@Autowired
	private PlayerBonusPayoutService playerBonusPayoutService;
	@Autowired
	private AttendancePayoutService<CampaignReward> payoutService;
	@Autowired
	private TransactionHistoryService transactionHistoryService;
	
	private static final String PAYOUT_ACCORDIAN = "jsp-fragment/payout-group-accordian";
	private static final String CLAN_ATTENDANCE_FINALIZE_URL = "../app/payouts/clan-payout";
	private static final String CAMPAIGN_REWARD_FINALIZE_URL = "../app/payouts/campaign-payout";
	private static final String CALLER_BONUS_FINALIZE_URL = "../app/payouts/caller-bonus-payout";

	@RequestMapping(value = "/get-clan-payout-groups")
	public ModelAndView getClanPayoutGroups(StandardPayoutInformation<ClanAccount> payoutInformation, HttpSession session) {
		
		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long payerAccountId = player.getAccountId();
		
		long clanId = membersInfo.getClanId();
		
		ClanAccount clanAccount = new ClanAccount();
		clanAccount.setClanId(clanId);
		
		payoutInformation.setPayerAccountId(payerAccountId);
		payoutInformation.setPayoutInformation(clanAccount);
		
		List<PayoutGroup> payoutGroups = attendancePayoutService.getPayoutGroups(payoutInformation);
		
		int totalAfterCalculations = PayoutGroupUtil.getTotalPayoutAmount(payoutGroups);
		int payoutAmount = PayoutGroupUtil.getTotalPayoutAmount(payoutGroups);
		
		ModelAndView modelAndView = new ModelAndView(PAYOUT_ACCORDIAN);
		modelAndView.addObject("payoutGroups", payoutGroups);
		modelAndView.addObject("totalAfterCalculations", totalAfterCalculations);
		modelAndView.addObject("payoutAmount", payoutAmount);
		modelAndView.addObject("finalizeUrl", CLAN_ATTENDANCE_FINALIZE_URL);
		
		return modelAndView;	
	}
	
	@RequestMapping(value = "/get-payout-script.user.js")
	public void getPayoutGroupScript(PayoutScriptInfo payoutScriptInfo, HttpServletResponse response, HttpSession session) throws IOException {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		payoutScriptInfo.setClanId(clanId);
		
		InputStream payoutUserScriptStream = getPayoutUserScriptStream(payoutScriptInfo);
		
		IOUtils.copy(payoutUserScriptStream, response.getOutputStream());
        response.setContentType("text/javascript");      
        response.setHeader("Content-Disposition", "attachment; filename=payout.user.js");
        response.flushBuffer();
	  
	}

	private InputStream getPayoutUserScriptStream(PayoutScriptInfo payoutScriptInfo)
	{ 
		PayoutScriptGenerator payoutScriptGenerator = new PayoutScriptGenerator();
		InputStream payoutUserScriptStream = payoutScriptGenerator.getPayoutUserScriptStream(payoutScriptInfo);
		return payoutUserScriptStream;
	}

	@RequestMapping(value = "/clan-payout")
	public void clanPayout(StandardPayoutInformation<ClanAccount> payoutInformation, HttpSession session) {
		
		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long payerAccountId = player.getAccountId();
		
		long clanId = membersInfo.getClanId();
		
		ClanAccount clanAccount = new ClanAccount();
		clanAccount.setClanId(clanId);
		
		payoutInformation.setPayerAccountId(payerAccountId);
		payoutInformation.setPayoutInformation(clanAccount);
		
		attendancePayoutService.payout(payoutInformation);
		
	}

	@RequestMapping(value = "/incentive-payout")
	public void incentivePayout(StandardPayoutInformation<IncentiveCompletion> payoutInformation, IncentiveCompletion incentiveCompletion, HttpSession session) {
		
		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		int tankId = incentiveCompletion.getTankUnlockedId();
		
		long payerAccountId = player.getAccountId();
		
		long clanId = membersInfo.getClanId();
		
		TankInformation tankInfo = tankInformationService.getTankInfo(tankId);
		
		String tankUnlockedName = tankInfo.getNameI18n();
		
		incentiveCompletion.setClanId(clanId);
		incentiveCompletion.setTankUnlockedName(tankUnlockedName);
		
		payoutInformation.setPayoutInformation(incentiveCompletion);
		payoutInformation.setPayerAccountId(payerAccountId);
		
		incentivePayoutService.payout(payoutInformation);
      
	}

	@RequestMapping(value = "/player-bonus-payout")
	public void playerBonusPayout(StandardPayoutInformation<PlayerBonus> payoutInformation, PlayerBonus playerBonus, HttpSession session) {
		
		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long payerAccountId = player.getAccountId();
		
		long clanId = membersInfo.getClanId();
		
		playerBonus.setClanId(clanId);
		
		payoutInformation.setPayoutInformation(playerBonus);
		payoutInformation.setPayerAccountId(payerAccountId);
		
		playerBonusPayoutService.payout(payoutInformation);
      
	}

	@RequestMapping(value = "/get-caller-bonus-payout-groups")
	public ModelAndView getCallerBonusPayoutGroups(StandardPayoutInformation<ClanAccount> payoutInformation, HttpSession session) {
		
		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long payerAccountId = player.getAccountId();
		
		long clanId = membersInfo.getClanId();
		
		ClanAccount clanAccount = new ClanAccount();
		clanAccount.setClanId(clanId);
		
		payoutInformation.setPayerAccountId(payerAccountId);
		payoutInformation.setPayoutInformation(clanAccount);
		
		List<PayoutGroup> payoutGroups = callerPayoutService.getPayoutGroups(payoutInformation);
		
		int totalAfterCalculations = PayoutGroupUtil.getTotalPayoutAmount(payoutGroups);
		int payoutAmount = payoutInformation.getAmount();
		
		ModelAndView modelAndView = new ModelAndView(PAYOUT_ACCORDIAN);
		modelAndView.addObject("payoutGroups", payoutGroups);
		modelAndView.addObject("totalAfterCalculations", totalAfterCalculations);
		modelAndView.addObject("payoutAmount", payoutAmount);
		modelAndView.addObject("finalizeUrl", CALLER_BONUS_FINALIZE_URL);
		
		return modelAndView;
      
	}

	@RequestMapping(value = "/caller-bonus-payout")
	public void callerBonusPayout(StandardPayoutInformation<ClanAccount> payoutInformation, HttpSession session) {
		
		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long payerAccountId = player.getAccountId();
		
		long clanId = membersInfo.getClanId();
		
		ClanAccount clanAccount = new ClanAccount();
		clanAccount.setClanId(clanId);
		
		payoutInformation.setPayerAccountId(payerAccountId);
		payoutInformation.setPayoutInformation(clanAccount);

		callerPayoutService.payout(payoutInformation);
      
	}
	
	@RequestMapping(value = "/get-campaign-event-payout-groups")
	public ModelAndView campaignEventPayout(StandardPayoutInformation<CampaignReward> payoutInformation, CampaignReward campaignReward, HttpSession session) {
		
		//TODO look into divide by zero
		
		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long payerAccountId = player.getAccountId();
		
		long clanId = membersInfo.getClanId();
		int campaignRewardAmount = payoutInformation.getAmount();
		
		ClanAccount clanAccount = new ClanAccount();

		clanAccount.setClanId(clanId);
		campaignReward.setClanId(clanId);
		campaignReward.setAmount(campaignRewardAmount);
		
		payoutInformation.setPayerAccountId(payerAccountId);
		payoutInformation.setPayoutInformation(campaignReward);

		List<PayoutGroup> payoutGroups = payoutService.getPayoutGroups(payoutInformation);
		
		int totalAfterCalculations = PayoutGroupUtil.getTotalPayoutAmount(payoutGroups);
		int payoutAmount = PayoutGroupUtil.getTotalPayoutAmount(payoutGroups);
		
		ModelAndView modelAndView = new ModelAndView(PAYOUT_ACCORDIAN);
		modelAndView.addObject("payoutGroups", payoutGroups);
		modelAndView.addObject("totalAfterCalculations", totalAfterCalculations);
		modelAndView.addObject("payoutAmount", payoutAmount);
		
		modelAndView.addObject("finalizeUrl", CAMPAIGN_REWARD_FINALIZE_URL);
		
		session.setAttribute("campaignReward", campaignReward);
		
		return modelAndView;
      
	}
	
	@RequestMapping(value = "/campaign-payout")
	public void campaignPayout(StandardPayoutInformation<CampaignReward> payoutInformation, HttpSession session) {
		
		Player player = (Player) session.getAttribute("player");
		CampaignReward campaignReward = (CampaignReward) session.getAttribute("campaignReward");
		
		long payerAccountId = player.getAccountId();
		
		payoutInformation.setPayerAccountId(payerAccountId);
		payoutInformation.setPayoutInformation(campaignReward);
		
		payoutService.payout(payoutInformation);
		
		session.removeAttribute("campaignReward");
		
	}

	@RequestMapping(value = "/get-transaction-pie")
	public List<TransactionHistoryChartPoint> getTransactionPie(HttpSession session) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		List<TransactionHistoryChartPoint> transactionHistoryPieChart = transactionHistoryService.getTransactionHistoryPieChart(clanId);
		
		return transactionHistoryPieChart;
		
		
	}
	
	@RequestMapping(value = "/get-tank-incentive-preset-values")
	public List<IncentivePayoutAmountsTableRow> getTankIncentivePresetValues(HttpSession session) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		List<IncentivePayoutAmountsTableRow> clanTankIncentiveAmountTableRows = incentiveService.getClanTankIncentiveAmountTableRows(clanId);
		
		return clanTankIncentiveAmountTableRows;
		
	}
	
	@RequestMapping(value = "/get-tank-information-by-tier")
	public List<TankInformation> getTankInformationByTier(Integer[] tankTiers) {
		
		List<TankInformation> specificTiersTankInformationList = tankInformationService.getSpecificTiersTankInformationList(tankTiers);
		
		Collections.sort(specificTiersTankInformationList);
		
		return specificTiersTankInformationList;
		
	}

}

