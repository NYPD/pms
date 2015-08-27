package services.ntr.pms.model.history;

import services.ntr.pms.service.information.Nameable;

public class CallerMapFrequency implements Nameable {
	
	private long accountId;
	private String mapName;
	private int timesCalled;
	private String nickname; //This does not come from SQL
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public int getTimesCalled() {
		return timesCalled;
	}
	public void setTimesCalled(int timesCalled) {
		this.timesCalled = timesCalled;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString() {
		return "CallerMapFrequency [accountId=" + accountId + ", nickname=" + nickname + ", mapName=" + mapName
				+ ", timesCalled=" + timesCalled + "]";
	}

}
