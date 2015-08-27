package services.ntr.pms.model.information;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = -1654254306671061813L;

	private long accountId;

	/**
	 * private is a reserved keyword in java, so we had to use personal as a
	 * variable name, however, we need to name the setter setPrivate so the
	 * objectMapper maps the private json data automatically to the Private
	 * class using the setPrivate method. HOWEVER, we need to name the getter
	 * for the Private object getPersonal because el language flips out if one
	 * uses getPrivate. So in the jsp, we have player.personal.gold which will
	 * call the getPersonal method in this class which will retrieve the Private
	 * object. Confusing, yes.
	 */
	private Private personal;
	private int globalRating;
	private long lastBattleTime;
	private String nickname;
	private String accessToken;
	private PlayerStatistics statistics;

	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getAccountIdAsString() {
		return Long.toString(accountId);
	}
	public Private getPersonal() {
		return personal;
	}
	public Private getPrivate() {
		return personal;
	}
	public void setPrivate(Private personal) {
		this.personal = personal;
	}
	public int getGlobalRating() {
		return globalRating;
	}
	public void setGlobalRating(int globalRating) {
		this.globalRating = globalRating;
	}
	public long getLastBattleTime() {
		return lastBattleTime;
	}
	public void setLastBattleTime(long lastBattleTime) {
		this.lastBattleTime = lastBattleTime;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public PlayerStatistics getStatistics() {
		return statistics;
	}
	public void setStatistics(PlayerStatistics statistics) {
		this.statistics = statistics;
	}
	
	@Override
	public String toString() {
		return "Player [accountId=" + accountId + ", personal=" + personal + ", globalRating=" + globalRating
				+ ", lastBattleTime=" + lastBattleTime + ", nickname=" + nickname + ", accessToken=" + accessToken
				+ ", statistics=" + statistics + "]";
	}

}
