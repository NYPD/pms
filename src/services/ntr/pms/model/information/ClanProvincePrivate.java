package services.ntr.pms.model.information;

public class ClanProvincePrivate {

	private boolean capital;

	public boolean isCapital() {
		return capital;
	}

	public void setCapital(boolean capital) {
		this.capital = capital;
	}

	@Override
	public String toString() {
		return "ClanProvincePrivate [capital=" + capital + "]";
	}

}
