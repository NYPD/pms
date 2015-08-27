package services.ntr.pms.model.information;


public class ClanProvince {

	private int arenaId;
	private String arenaI18n;
	private boolean attacked;
	private boolean combatsRunning;
	private String name;
	private int occupancyTime;
	private long primeTime;
	private String provinceId;
	private int revenue;
	private String type;
	private ClanProvincePrivate clanProvincePrivate;

	public int getArenaId() {
		return arenaId;
	}
	public void setArenaId(int arenaId) {
		this.arenaId = arenaId;
	}
	public String getArenaI18n() {
		return arenaI18n;
	}

	public void setArenaI18n(String arenaI18n) {
		this.arenaI18n = arenaI18n;
	}
	public boolean isAttacked() {
		return attacked;
	}
	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
	public boolean isCombatsRunning() {
		return combatsRunning;
	}
	public void setCombatsRunning(boolean combatsRunning) {
		this.combatsRunning = combatsRunning;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOccupancyTime() {
		return occupancyTime;
	}
	public void setOccupancyTime(int occupancyTime) {
		this.occupancyTime = occupancyTime;
	}
	public long getPrimeTime() {
		return primeTime;
	}
	public void setPrimeTime(long primeTime) {
		this.primeTime = primeTime;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public int getRevenue() {
		return revenue;
	}
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ClanProvincePrivate getClanProvincePrivate() {
		return clanProvincePrivate;
	}
	public void setPrivate(ClanProvincePrivate clanProvincePrivate) {
		this.clanProvincePrivate = clanProvincePrivate;
	}
	
	@Override
	public String toString() {
		return "ClanProvince [arenaId=" + arenaId + ", arenaI18n=" + arenaI18n + ", attacked=" + attacked
				+ ", combatsRunning=" + combatsRunning + ", name=" + name + ", occupancyTime=" + occupancyTime
				+ ", primeTime=" + primeTime + ", provinceId=" + provinceId + ", revenue=" + revenue + ", type=" + type
				+ ", clanProvincePrivate=" + clanProvincePrivate + "]";
	}


}
