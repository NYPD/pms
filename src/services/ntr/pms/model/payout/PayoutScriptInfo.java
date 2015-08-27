package services.ntr.pms.model.payout;

import java.util.List;

public class PayoutScriptInfo {

	private List<String> playerNicknames;
	private int payoutAmount;
	private long clanId;
	
	public List<String> getPlayerNicknames() {
		return playerNicknames;
	}
	public void setPlayerNicknames(List<String> playerNicknames) {
		this.playerNicknames = playerNicknames;
	}
	public int getPayoutAmount() {
		return payoutAmount;
	}
	public void setPayoutAmount(int payoutAmount) {
		this.payoutAmount = payoutAmount;
	}
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	
	@Override
	public String toString() {
		return "PayoutScriptInfo [playerNicknames=" + playerNicknames + ", payoutAmount=" + payoutAmount + ", clanId="
				+ clanId + "]";
	}
	
}
