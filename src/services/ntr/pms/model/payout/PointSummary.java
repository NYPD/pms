package services.ntr.pms.model.payout;

import services.ntr.pms.service.information.Nameable;

public class PointSummary implements Nameable{
	
	private long accountId;
	private int points;
	private String Nickname;//Not from the database

	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getNickname() {
		return Nickname;
	}
	public void setNickname(String nickname) {
		Nickname = nickname;
	}
	
	@Override
	public String toString() {
		return "PointSummary [accountId=" + accountId + ", points=" + points + ", Nickname=" + Nickname + "]";
	}

}
