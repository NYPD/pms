package services.ntr.pms.model.information;

public class MapDetail implements Comparable<MapDetail>{

	private String arenaId;
	private String description;
	private String nameI18n;
	
	public String getArenaId() {
		return arenaId;
	}
	public void setArenaId(String arenaId) {
		this.arenaId = arenaId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNameI18n() {
		return nameI18n;
	}
	public void setNameI18n(String nameI18n) {
		this.nameI18n = nameI18n;
	}
	
	@Override
	public String toString() {
		return "MapName [arenaId=" + arenaId + ", description=" + description + ", nameI18n=" + nameI18n + "]";
	}
	@Override
	public int compareTo(MapDetail mapDetail) {
		
		String otherMapDetail = mapDetail.getNameI18n();
		String thisMapDetail= this.getNameI18n();
		
		int compareTo = String.CASE_INSENSITIVE_ORDER.compare(thisMapDetail, otherMapDetail);
		
		return compareTo;
	}
	
}
