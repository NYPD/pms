package services.ntr.pms.service.payout;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.PayoutDAO;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutInformation;
import services.ntr.pms.model.payout.PlayerBonus;
import services.ntr.pms.model.payout.PlayerPayoutSummary;
import services.ntr.pms.model.payout.Transaction;

@Service
public class DefaultPlayerBonusPayoutService implements PlayerBonusPayoutService
{
	private static final String TYPE = "Player Bonus";
	
	@Autowired
	private PayoutDAO payoutDAO;
	
	@Override
	public List<PayoutGroup> getPayoutGroups(PayoutInformation<PlayerBonus> payoutInformation) {
		PlayerBonus playerBonus = payoutInformation.getPayoutInformation();
		
		int amount = payoutInformation.getAmount();
		long accountId = playerBonus.getAccountId();
		
		PlayerPayoutSummary payoutSummary = new PlayerPayoutSummary();
		payoutSummary.setAmount(amount);
		payoutSummary.setAccountId(accountId);
		
		PayoutGroup payoutGroup = new PayoutGroup();
		payoutGroup.addPayoutSummary(payoutSummary);
		
		List<PayoutGroup> payoutGroups = new ArrayList<>();
		payoutGroups.add(payoutGroup);
		
		return payoutGroups;
	}
	
	@Override
	public void payout(PayoutInformation<PlayerBonus> payoutInformation) {
		
		PlayerBonus playerBonus = payoutInformation.getPayoutInformation();
		Payout payout = extractPayout(payoutInformation);
		
		long clanId = playerBonus.getClanId();
		
		Transaction transaction = new Transaction();
		transaction.setClanId(clanId);
		
		addTransaction(transaction);
		
		int transactionId = transaction.getTransactionId();
		
		payout.setTransactionId(transactionId);
		
		addPayout(payout);
		addPlayerBonus(playerBonus);
		
		int playerBonusId = playerBonus.getPlayerBonusId();
		int payoutId = payout.getPayoutId();

		addPlayerBonusPayout(playerBonusId, payoutId);
	}

	private Payout extractPayout(PayoutInformation<PlayerBonus> payoutInformation) {
		int amount = payoutInformation.getAmount();
		long payerAccountId = payoutInformation.getPayerAccountId();
		long payoutTime = System.currentTimeMillis();
		
		Payout payout = new Payout();
		payout.setPayerAccountId(payerAccountId);
		payout.setType(TYPE);
		payout.setAmount(amount);
		payout.setPayerAccountId(payerAccountId);
		payout.setPayoutTime(payoutTime);
		
		return payout;
	}
	private void addTransaction(Transaction transaction) {
		payoutDAO.addTransaction(transaction);
	}
	public void addPayout(Payout payout) {
		payoutDAO.addPayout(payout);
	}
	private void addPlayerBonus(PlayerBonus playerBonus) {
		payoutDAO.addPlayerBonus(playerBonus);
	}
	private void addPlayerBonusPayout(int playerBonusId, int payoutId) {
		payoutDAO.addPlayerBonusPayout(playerBonusId,payoutId);
	}
	@Override
	public List<Payout> getPayoutByPlayer(Player player)
	{
		return payoutDAO.getPayoutsByPlayer(player);
	}

	public PayoutDAO getPayoutDAO()
	{
		return payoutDAO;
	}
	public void setPayoutDAO(PayoutDAO payoutDAO)
	{
		this.payoutDAO = payoutDAO;
	}
}
