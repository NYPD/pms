package services.ntr.pms.model.information;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class LocalClanSettings {

	private int id;
	private long clanId;
	private int battleTimeRangeBeforeMinutes;
	private int battleTimeRangeAfterMinutes;
	private double battleDayGmtOffset;
	private int callerAmountPerBattle;
	private String playerIsBannedError;
	private String playerAlreadyCheckedInError;
	private String playerNotWithinTimeFrameError;
	private String defaultError;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLocalClanSettingId(int localClanSettingId) {
		this.id = localClanSettingId;
	}
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	public int getBattleTimeRangeBeforeMinutes() {
		return battleTimeRangeBeforeMinutes;
	}
	public long getBattleTimeRangeBeforeMinutesInMilis() {
		
		long battleTimeRangeMinutesAsLong = battleTimeRangeBeforeMinutes;
		
		long miliMinutes = TimeUnit.MILLISECONDS.convert(battleTimeRangeMinutesAsLong, TimeUnit.MINUTES);
		
		return miliMinutes;
	}
	public void setBattleTimeRangeBeforeMinutes(int battleTimeRangeBeforeMinutes) {
		this.battleTimeRangeBeforeMinutes = battleTimeRangeBeforeMinutes;
	}
	public int getBattleTimeRangeAfterMinutes() {
		return battleTimeRangeAfterMinutes;
	}
	public long getBattleTimeRangeAfterMinutesInMilis() {
		
		long battleTimeRangeMinutesAsLong = battleTimeRangeAfterMinutes;
		
		long miliMinutes = TimeUnit.MILLISECONDS.convert(battleTimeRangeMinutesAsLong, TimeUnit.MINUTES);
		
		return miliMinutes;
	}
	public void setBattleTimeRangeAfterMinutes(int battleTimeRangeAfterMinutes) {
		this.battleTimeRangeAfterMinutes = battleTimeRangeAfterMinutes;
	}
	public double getBattleDayGmtOffset() {
		return battleDayGmtOffset;
	}
	public TimeZone getBattleDayGmtOffsetAsTimeZone() {
		
		boolean offsetIsPositiveOrZero = this.battleDayGmtOffset >= 0;
		
		String battleDayTimezone = "GMT";
		
		if(offsetIsPositiveOrZero) battleDayTimezone += "+";
		
		int hoursChosen = (int)this.battleDayGmtOffset;
		
		boolean hourChosenIsTwoDigits = hoursChosen > 9;
		
		battleDayTimezone += hourChosenIsTwoDigits ? hoursChosen : ("0" + Integer.toString(hoursChosen));
		
		double minutesChosenAsFraction = this.battleDayGmtOffset - hoursChosen;
		
		int minutesChosenAsRealMinutes = (int)(minutesChosenAsFraction * 60);
		
		boolean minutesChosenAsRealMinutesIsTwoDigits = minutesChosenAsRealMinutes > 9;
		
		battleDayTimezone += minutesChosenAsRealMinutesIsTwoDigits ? minutesChosenAsRealMinutes : ("0" + Integer.toString(minutesChosenAsRealMinutes));

		TimeZone timeZone = TimeZone.getTimeZone(battleDayTimezone);
		
		return timeZone;
	}
	public void setBattleDayGmtOffset(double battleDayGmtOffset) {
		this.battleDayGmtOffset = battleDayGmtOffset;
	}
	public int getCallerAmountPerBattle() {
		return callerAmountPerBattle;
	}
	public void setCallerAmountPerBattle(int callerAmountPerBattle) {
		this.callerAmountPerBattle = callerAmountPerBattle;
	}
	public String getPlayerIsBannedError() {
		return playerIsBannedError;
	}
	public void setPlayerIsBannedError(String playerIsBannedError) {
		this.playerIsBannedError = playerIsBannedError;
	}
	public String getPlayerAlreadyCheckedInError() {
		return playerAlreadyCheckedInError;
	}
	public void setPlayerAlreadyCheckedInError(String playerAlreadyCheckedInError) {
		this.playerAlreadyCheckedInError = playerAlreadyCheckedInError;
	}
	public String getPlayerNotWithinTimeFrameError() {
		return playerNotWithinTimeFrameError;
	}
	public void setPlayerNotWithinTimeFrameError(String playerNotWithinTimeFrameError) {
		this.playerNotWithinTimeFrameError = playerNotWithinTimeFrameError;
	}
	public String getDefaultError() {
		return defaultError;
	}
	public void setDefaultError(String defaultError) {
		this.defaultError = defaultError;
	}
	
	@Override
	public String toString() {
		return "LocalClanSettings [id=" + id + ", clanId=" + clanId + ", battleTimeRangeBeforeMinutes="
				+ battleTimeRangeBeforeMinutes + ", battleTimeRangeAfterMinutes=" + battleTimeRangeAfterMinutes
				+ ", battleDayGmtOffset=" + battleDayGmtOffset + ", callerAmountPerBattle=" + callerAmountPerBattle
				+ ", playerIsBannedError=" + playerIsBannedError + ", playerAlreadyCheckedInError="
				+ playerAlreadyCheckedInError + ", playerNotWithinTimeFrameError=" + playerNotWithinTimeFrameError
				+ ", defaultError=" + defaultError + "]";
	}
	
}
