package services.ntr.pms.service.payout;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.PayoutDAO;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.IncentiveCompletion;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutInformation;
import services.ntr.pms.model.payout.PlayerPayoutSummary;
import services.ntr.pms.model.payout.Transaction;

@Service
public class DefaultIncentivePayoutService implements IncentivePayoutService
{
	private static final String TYPE = "Incentive";
	
	@Autowired
	private PayoutDAO payoutDAO;
	
	@Override
	public List<PayoutGroup> getPayoutGroups(PayoutInformation<IncentiveCompletion> payoutInformation){
		IncentiveCompletion incentiveCompletion = payoutInformation.getPayoutInformation();
		
		int amount = payoutInformation.getAmount();
		long accountId = incentiveCompletion.getAccountId();
		
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
	public void payout(PayoutInformation<IncentiveCompletion> incentivePayout){
		IncentiveCompletion incentiveCompletion = incentivePayout.getPayoutInformation();
		Payout payout = extractPayout(incentivePayout);
		
		long clanId = incentiveCompletion.getClanId();
		
		Transaction transaction = new Transaction();
		transaction.setClanId(clanId);
		
		addTransaction(transaction);
		
		int transactionId = transaction.getTransactionId();
		
		payout.setTransactionId(transactionId);
		
		addPayout(payout);
		addIncentiveCompletion(incentiveCompletion);
		
		int incentiveId = incentiveCompletion.getIncentiveId();
		int payoutId = payout.getPayoutId();

		addIncentivePayout(incentiveId, payoutId);
	}
	
	private Payout extractPayout(PayoutInformation<IncentiveCompletion> incentivePayout){
		int amount = incentivePayout.getAmount();
		long payerAccountId = incentivePayout.getPayerAccountId();
		long payoutTime = System.currentTimeMillis();
		
		Payout payout = new Payout();
		payout.setPayerAccountId(payerAccountId);
		payout.setType(TYPE);
		payout.setAmount(amount);
		payout.setPayerAccountId(payerAccountId);
		payout.setPayoutTime(payoutTime);
		
		return payout;
	}
	private void addTransaction(Transaction transaction){
		payoutDAO.addTransaction(transaction);
	}
	public void addPayout(Payout payout){
		payoutDAO.addPayout(payout);
	}
	private void addIncentiveCompletion(IncentiveCompletion incentiveCompletion){
		payoutDAO.addIncentiveCompletion(incentiveCompletion);
	}
	private void addIncentivePayout(int incentiveId, int payoutId){
		payoutDAO.addIncentivePayout(incentiveId,payoutId);
	}
	
	@Override
	public List<Payout> getPayoutByPlayer(Player player){
		return payoutDAO.getPayoutsByPlayer(player);
	}
	
	public PayoutDAO getPayoutDAO(){
		return payoutDAO;
	}
	public void setPayoutDAO(PayoutDAO payoutDAO){
		this.payoutDAO = payoutDAO;
	}
	
}
