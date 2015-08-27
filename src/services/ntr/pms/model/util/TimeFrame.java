package services.ntr.pms.model.util;

import java.sql.Timestamp;

public class TimeFrame {

	// Assume these are always milliseconds
	private long startTime;
	private long endTime;

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Timestamp getStartTimeAsTimestamp() {
		return new Timestamp(startTime);
	}

	public Timestamp getEndTimeAsTimestamp() {
		return new Timestamp(endTime);
	}

	public long getStartTimeAsSeconds() {
		return (this.startTime / 1000);
	}

	public long getEndTimeAsSeconds() {
		return (this.endTime / 1000);
	}

	@Override
	public String toString() {
		return "TimeFrame [startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
