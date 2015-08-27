package services.ntr.pms.model.access;

import services.ntr.pms.service.information.Nameable;

public class User implements Nameable {

	private long accountId;
	private Role role;
	private String nickname; //Not From SQL
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "User [accountId=" + accountId + ", role=" + role + "]";
	}
	
}
