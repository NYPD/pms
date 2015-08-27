package services.ntr.pms.model.information;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Private implements Serializable {
	
	private static final long serialVersionUID = -7736715842178440155L;
	
	private int gold;
	private int credits;
	private int freeXp;
	private long banTime;

	public int getGold() {
		return gold;
	}
	public String getGoldFormatted() {
		return NumberFormat.getIntegerInstance(Locale.US).format(gold);
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getCredits() {
		return credits;
	}
	public String getCreditsFormatted() {
		return NumberFormat.getIntegerInstance(Locale.US).format(credits);
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public int getFreeXp() {
		return freeXp;
	}
	public String getFreeXpFormatted() {
		return NumberFormat.getIntegerInstance(Locale.US).format(freeXp);
	}
	public void setFreeXp(int freeXp) {
		this.freeXp = freeXp;
	}
	public long getBanTime() {
		return banTime;
	}
	public void setBanTime(long banTime) {
		this.banTime = banTime;
	}

	public boolean isBanned() {
		// TODO change this perhaps to calender
		long currentTime = System.currentTimeMillis() / 1000;

		// TODO clean this up
		return currentTime < this.banTime && this.banTime != 0;
	}

	@Override
	public String toString() {
		return "Private [gold=" + gold + ", credits=" + credits + ", freeXp=" + freeXp + ", banTime=" + banTime + "]";
	}

}
