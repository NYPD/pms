package services.ntr.pms.model.information;

public class Arena {

	private String nameI18n;
	private String arenaId;

	public String getNameI18n() {
		return nameI18n;
	}
	public void setNameI18n(String nameI18n) {
		this.nameI18n = nameI18n;
	}
	public String getArenaId() {
		return arenaId;
	}
	public void setArenaId(String arenaId) {
		this.arenaId = arenaId;
	}
	
	@Override
	public String toString() {
		return "Arena [nameI18n=" + nameI18n + ", arenaId=" + arenaId + "]";
	}

}
