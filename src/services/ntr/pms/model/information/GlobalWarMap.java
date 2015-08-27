package services.ntr.pms.model.information;

public class GlobalWarMap {

	private String mapId;
	private String mapUrl;
	private String state;
	private String type;

	public String getMapId() {
		return mapId;
	}
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isActive() {

		boolean isActive = "active".equals(this.state);

		return isActive;
	}

	@Override
	public String toString() {
		return "GlobalWarMap [mapId=" + mapId + ", mapUrl=" + mapUrl + ", state=" + state + ", type=" + type + "]";
	}



}
