package services.ntr.pms.model.access;

public class AllowedClan {

	private int allowedClanId;
	private long clanId;
	private String clanName;
	
	public int getAllowedClanId() {
		return allowedClanId;
	}
	public void setAllowedClanId(int allowedClanId) {
		this.allowedClanId = allowedClanId;
	}
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	public String getClanName() {
		return clanName;
	}
	public void setClanName(String clanName) {
		this.clanName = clanName;
	}
	
	@Override
	public String toString() {
		return "AllowedClan [allowedClanId=" + allowedClanId + ", clanId=" + clanId + ", clanName=" + clanName + "]";
	}
	
}
