package services.ntr.pms.model.information;

public class MembersInfo {

	private long accountId;
	private String accountName;
	private long joinedAt;
	private String role;
	private String roleI18n;
	private MembersInfoClan clan;
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public long getJoinedAt() {
		return joinedAt;
	}
	public void setJoinedAt(long joinedAt) {
		this.joinedAt = joinedAt;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRoleI18n() {
		return roleI18n;
	}
	public void setRoleI18n(String roleI18n) {
		this.roleI18n = roleI18n;
	}
	public MembersInfoClan getClan() {
		return clan;
	}
	public void setClan(MembersInfoClan clan) {
		this.clan = clan;
	}
	public long getClanId() {
		return clan.getClanId();
	}
	public String getClanIdAsString() {
		return String.valueOf(clan.getClanId());
	}
	
	@Override
	public String toString() {
		return "MembersInfo [accountId=" + accountId + ", accountName=" + accountName + ", joinedAt=" + joinedAt
				+ ", role=" + role + ", roleI18n=" + roleI18n + ", clan=" + clan + "]";
	}

}
