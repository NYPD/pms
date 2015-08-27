package services.ntr.pms.model.payout;

public class ClanAccount {

	private long clanId;

	public long getClanId() {
		return clanId;
	}

	public void setClanId(long clanId) {
		this.clanId = clanId;
	}

	@Override
	public String toString() {
		return "ClanPayout [clanId=" + clanId + "]";
	}

}
