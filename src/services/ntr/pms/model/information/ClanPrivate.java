package services.ntr.pms.model.information;

public class ClanPrivate {
	private int treasury;

	public int getTreasury() {
		return treasury;
	}
	public void setTreasury(int treasury) {
		this.treasury = treasury;
	}
	
	@Override
	public String toString() {
		return "ClanPrivate [treasury=" + treasury + "]";
	}

}
