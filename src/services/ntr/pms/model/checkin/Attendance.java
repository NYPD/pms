package services.ntr.pms.model.checkin;

import java.sql.Timestamp;

public class Attendance {

	private int id;
	private long checkInTime;
	private long accountId;
	private long clanId;
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(long checkInTime) {
		this.checkInTime = checkInTime;
	}
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAttendanceId() {
		return id;
	}
	public void setAttendanceId(int id) {
		this.id = id;
	}
	
	//Helper methods
	public Timestamp getCheckInTimeAsTimeStamp() {
		return new Timestamp(checkInTime);
	}
	public void setCheckInTimeUsingTimestamp(Timestamp timestamp) {
		long time = timestamp.getTime();
		long checkInTime = time;
		this.checkInTime = checkInTime;
	}
	
	@Override
	public String toString() {
		return "Attendance [id=" + id + ", checkInTime=" + checkInTime + ", accountId=" + accountId + ", clanId="
				+ clanId + "]";
	}

}
