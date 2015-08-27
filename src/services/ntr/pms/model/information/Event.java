package services.ntr.pms.model.information;

import java.sql.Timestamp;
import java.util.Arrays;

public class Event {

	private int id;
	private long clanId;
	private String type;
	private long startTime;
	private String imageUrl;
	
	//Repeat Options
	private boolean repeat;
	private int repeatType;
	private int[] weeklyRepeatDays;
	private int eventEndType;
	private long endTime;
	
	//TODO these are diabled in the js and HTML
	//private int repeatInterval;
	//private int monthlyRepeatBy;
	//private int occurenceEndFrequency;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEventId() {
		return id;
	}
	public void setEventId(int id) {
		this.id = id;
	}
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long time) {
		this.startTime = time;
	}
	public Timestamp getStartTimeAsTimestamp() {
		return new Timestamp(startTime);
	}
	public void setStartTimeAsTimestamp(Timestamp timestamp) {
		long timestampAsMilis = timestamp.getTime();
		this.startTime = timestampAsMilis;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isUpcomingEvent(){
		long currentTimeMillis = System.currentTimeMillis();
		return startTime >= currentTimeMillis ;		
	}
	public boolean isRepeat() {
		return repeat;
	}
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	public int getRepeatType() {
		return repeatType;
	}
	public void setRepeatType(int repeatType) {
		this.repeatType = repeatType;
	}
	public int[] getWeeklyRepeatDays() {
		return weeklyRepeatDays;
	}
	public void setWeeklyRepeatDays(int[] weeklyRepeatDays) {
		this.weeklyRepeatDays = weeklyRepeatDays;
	}
	public int getEventEndType() {
		return eventEndType;
	}
	public void setEventEndType(int eventEndType) {
		this.eventEndType = eventEndType;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", clanId=" + clanId + ", type=" + type + ", startTime=" + startTime + ", imageUrl="
				+ imageUrl + ", repeat=" + repeat + ", repeatType=" + repeatType + ", weeklyRepeatDays="
				+ Arrays.toString(weeklyRepeatDays) + ", eventEndType=" + eventEndType + ", endTime=" + endTime + "]";
	}
	
}
