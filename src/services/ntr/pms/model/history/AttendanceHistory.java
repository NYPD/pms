package services.ntr.pms.model.history;

import services.ntr.pms.service.information.Nameable;

//TODO this might have to be an interface or changed to ClanAttendanceHistory
public class AttendanceHistory implements Nameable {

	private long accountId;
	private int points;
	private String nickname; //This does not come from SQL

	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "AttendanceHistory [accountId=" + accountId + ", nickname=" + nickname + ", points="
				+ points + "]";
	}
}
