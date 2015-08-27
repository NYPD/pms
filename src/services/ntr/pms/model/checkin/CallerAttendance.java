package services.ntr.pms.model.checkin;

import java.sql.Timestamp;

public class CallerAttendance {

	private int id;
	private long accountId;
	private long clanId;
	private long battleTime;
	private String mapName;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCallerAttendanceId() {
		return id;
	}
	public void setCallerAttendanceId(int id) {
		this.id = id;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	public long getBattleTime() {
		return battleTime;
	}
	public void setBattleTime(long battleTime) {
		this.battleTime = battleTime;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	//Helper Methods
	public void setBattleTimeUsingTimestamp(Timestamp timestamp) {
		long time = timestamp.getTime();
		long battleTime = time;
		this.battleTime = battleTime;
	}
	
	@Override
	public String toString() {
		return "CallerAttendance [id=" + id + ", accountId=" + accountId + ", clanId=" + clanId + ", battleTime="
				+ battleTime + ", mapName=" + mapName + "]";
	}
	
}
