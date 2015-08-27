package services.ntr.pms.util;

public enum UserRole {

	USER(1),
	CLAN_ADMIN(2),
	SYSTEM_ADMIN(3);
	
	private int id;
	
	UserRole(int id){
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}
}
