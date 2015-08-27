package services.ntr.pms.model.information;

import java.sql.Timestamp;

public class EventMeta {

	private int eventMetaId;
	private int eventId;
	private Timestamp repeatStart;
	private int repeatInterval;
	
	public int getEventMetaId() {
		return eventMetaId;
	}
	public void setEventMetaId(int eventMetaId) {
		this.eventMetaId = eventMetaId;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public Timestamp getRepeatStart() {
		return repeatStart;
	}
	public void setRepeatStart(Timestamp repeatStart) {
		this.repeatStart = repeatStart;
	}
	public int getRepeatInterval() {
		return repeatInterval;
	}
	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	
	@Override
	public String toString() {
		return "EventMeta [eventMetaId=" + eventMetaId + ", eventId=" + eventId + ", repeatStart=" + repeatStart
				+ ", repeatInterval=" + repeatInterval + "]";
	}
	
}
