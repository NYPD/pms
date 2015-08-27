package services.ntr.pms.model.information;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClanBattle implements Comparable<ClanBattle> {

	private boolean started;
	private long time;
	private String type;
	private BattlePrivate personal;
	@JsonProperty("arenas")
	private List<Arena> arenas;

	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	public long getTime() {
		return time;
	}
	public long getTimeAsMilis() {
		
		long timeAsMilis = TimeUnit.MILLISECONDS.convert(time, TimeUnit.SECONDS);
		
		return timeAsMilis;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BattlePrivate getPersonal() {
		return personal;
	}
	public void setPrivate(BattlePrivate personal) {
		this.personal = personal;
	}
	public BattlePrivate getPrivate() {
		return personal;
	}
	public void setPersonal(BattlePrivate personal) {
		this.personal = personal;
	}
	public List<Arena> getArenas() {
		return arenas;
	}
	public void setArenas(List<Arena> arenas) {
		this.arenas = arenas;
	}
	public String getFormattedBattleType() {

		switch (this.type) {
		case "for_province":
			return "Normal";
		case "meeting_engagement":
			return "Encounter";
		case "landing":
			return "Landing";
		default:
			return "Unknown";
		}
	}
	public Arena getFirstArena() {
		List<Arena> arenas = getArenas();
		Arena arena = arenas.get(0);

		return arena;
	}

	@Override
	public String toString() {
		return "ClanBattle [started=" + started + ", time=" + time + ", type=" + type + ", personal=" + personal
				+ ", arenas=" + arenas + "]";
	}
	@Override
	public int compareTo(ClanBattle otherClanBattle) {
		
		long otherTimeAsMilis = otherClanBattle.getTimeAsMilis();
		long thisTimeAsMilis = this.getTimeAsMilis();
		
		int compareTo = Long.compare(thisTimeAsMilis, otherTimeAsMilis);
		
		return compareTo;
	}
}
