package services.ntr.pms.model.information;

import java.util.Comparator;

public class ClanMember {
	
	private static class AccountNameComparator implements Comparator<ClanMember> {

		@Override
		public int compare(ClanMember clanMember, ClanMember otherClanMember) {

			String accountName = clanMember.getAccountName();
			String otherAccountName = otherClanMember.getAccountName();
			
			int compare = String.CASE_INSENSITIVE_ORDER.compare(accountName, otherAccountName);
			
			return compare;
		}
		
	}

	public static final Comparator<ClanMember> ACCOUNT_NAME_ORDER = new AccountNameComparator();
	private long accountId;
	private String accountName;
	private long joinedAt;
	private String role;
	private String roleI18n;

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
	public String getAccountIdAsString() {
		
		String accountIdAsString = String.valueOf(accountId);
		
		return accountIdAsString;
	}
	
	@Override
	public String toString() {
		return "ClanMember [accountId=" + accountId + ", accountName=" + accountName + ", joinedAt=" + joinedAt
				+ ", role=" + role + ", roleI18n=" + roleI18n + "]";
	}

}
