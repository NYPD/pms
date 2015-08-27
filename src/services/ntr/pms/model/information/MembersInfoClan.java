package services.ntr.pms.model.information;

/**
 * Short Clan Information
 * @author NYPD
 *
 */
public class MembersInfoClan {

	private long clanId;	
	private String color;
	private long createdAt;
	private int membersCount;
	private String name;
	private String tag;
	
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	public int getMembersCount() {
		return membersCount;
	}
	public void setMembersCount(int membersCount) {
		this.membersCount = membersCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@Override
	public String toString() {
		return "MembersInfoClan [clanId=" + clanId + ", color=" + color + ", createdAt=" + createdAt
				+ ", membersCount=" + membersCount + ", name=" + name + ", tag=" + tag + "]";
	}
	
}
