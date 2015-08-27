package services.ntr.pms.model.access;

import services.ntr.pms.util.UserRole;

public class Role {

	private int roleId = UserRole.USER.getId();
	private String roleName = "User";
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + "]";
	}
	
}
