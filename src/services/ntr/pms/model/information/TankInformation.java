package services.ntr.pms.model.information;

public class TankInformation implements Comparable<TankInformation> {

	private String nation_i18n;
	private String name;
	private int level;
	private String nation;
	private boolean isPremium;
	private String typeI18n;
	private String nameI18n;
	private String shortNameI18n;
	private String type;
	private int tankId;
	private String image;
	private String imageSmall;
	
	public String getNation_i18n() {
		return nation_i18n;
	}
	public void setNation_i18n(String nation_i18n) {
		this.nation_i18n = nation_i18n;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public boolean isPremium() {
		return isPremium;
	}
	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}
	public String getTypeI18n() {
		return typeI18n;
	}
	public void setTypeI18n(String typeI18n) {
		this.typeI18n = typeI18n;
	}
	public String getNameI18n() {
		return nameI18n;
	}
	public void setNameI18n(String nameI18n) {
		this.nameI18n = nameI18n;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTankId() {
		return tankId;
	}
	public void setTankId(int tankId) {
		this.tankId = tankId;
	}
	public String getImageSmall() {
		return imageSmall;
	}
	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getShortNameI18n() {
		return shortNameI18n;
	}
	public void setShortNameI18n(String shortNameI18n) {
		this.shortNameI18n = shortNameI18n;
	}
	
	@Override
	public int compareTo(TankInformation otherTank) {
		
		String otherTankName = otherTank.getNameI18n();
		String thisTankname = this.getNameI18n();
		
		int compare = String.CASE_INSENSITIVE_ORDER.compare(thisTankname, otherTankName);
		
		return compare;
	}

	@Override
	public String toString() {
		return "TankInformation [nation_i18n=" + nation_i18n + ", name=" + name + ", level=" + level + ", nation="
				+ nation + ", isPremium=" + isPremium + ", typeI18n=" + typeI18n + ", nameI18n=" + nameI18n
				+ ", shortNameI18n=" + shortNameI18n + ", type=" + type + ", tankId=" + tankId + ", image=" + image
				+ ", imageSmall=" + imageSmall + "]";
	}

}
