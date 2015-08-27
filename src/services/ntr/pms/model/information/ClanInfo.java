package services.ntr.pms.model.information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClanInfo {

	private boolean acceptsJoinRequests;
	private long clanId;
	private String color;
	private long createdAt;
	private long creatorId;
	private String description;
	private String descriptionHtml;
	private boolean isClanDisbanded;
	private long leaderId;
	private int membersCount;
	private String motto;
	private String name;
	private String oldName;
	private String oldTag;
	private long renamedAt;
	private String tag;
	private long updatedAt;
	private Emblems emblems;
	private List<ClanMember> members;
	private ClanPrivate personal;

	private List<ClanProvince> clanProvinces;
	private int totalGoldPerDay;
	private int totalProvincesOwned;
	private Map<Long, ClanMember> clanMemberInformationMap = new HashMap<Long, ClanMember>();//TODO feels funny

	public boolean isAcceptsJoinRequests() {
		return acceptsJoinRequests;
	}
	public void setAcceptsJoinRequests(boolean acceptsJoinRequests) {
		this.acceptsJoinRequests = acceptsJoinRequests;
	}
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
	public long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptionHtml() {
		return descriptionHtml;
	}
	public void setDescriptionHtml(String descriptionHtml) {
		this.descriptionHtml = descriptionHtml;
	}
	public boolean isClanDisbanded() {
		return isClanDisbanded;
	}
	public void setClanDisbanded(boolean isClanDisbanded) {
		this.isClanDisbanded = isClanDisbanded;
	}
	public long getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(long leaderId) {
		this.leaderId = leaderId;
	}
	public int getMembersCount() {
		return membersCount;
	}
	public void setMembersCount(int membersCount) {
		this.membersCount = membersCount;
	}
	public String getMotto() {
		return motto;
	}
	public void setMotto(String motto) {
		this.motto = motto;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getOldTag() {
		return oldTag;
	}
	public void setOldTag(String oldTag) {
		this.oldTag = oldTag;
	}
	public long getRenamedAt() {
		return renamedAt;
	}
	public void setRenamedAt(long renamedAt) {
		this.renamedAt = renamedAt;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Emblems getEmblems() {
		return emblems;
	}
	public void setEmblems(Emblems emblems) {
		this.emblems = emblems;
	}
	public List<ClanMember> getMembers() {
		return members;
	}
	public void setMembers(List<ClanMember> members) {
		this.members = members;
		setClanMemberInformationMap();
	}
	public ClanPrivate getPrivate() {
		return personal;
	}
	public void setPrivate(ClanPrivate personal) {
		this.personal = personal;
	}
	public ClanPrivate getPersonal() {
		return personal;
	}
	public void setPersonal(ClanPrivate personal) {
		this.personal = personal;
	}
	public List<ClanProvince> getClanProvinces() {
		return clanProvinces;
	}
	public void setClanProvinces(List<ClanProvince> clanProvinces) {

		for (ClanProvince clanProvince : clanProvinces) {
			totalProvincesOwned += 1;
			totalGoldPerDay += clanProvince.getRevenue();
		}

		this.clanProvinces = clanProvinces;
	}
	public int getTotalProvincesOwned() {
		return totalProvincesOwned;
	}
	public int getTotalGoldPerDay() {
		return totalGoldPerDay;
	}
	public int getTotalGoldPerHour() {
		return totalGoldPerDay / 24;
	}
	public void setClanMemberInformationMap() {

		for (ClanMember clanMember : this.members) {

			long accountId = clanMember.getAccountId();

			this.clanMemberInformationMap.put(accountId, clanMember);
		}

	}
	public Map<Long, ClanMember> getClanMemberInformationMap() {
		return clanMemberInformationMap;
	}


	/*Helper Methods ------------------------------------------------------------------------------------------ */
	public List<Long> getClanMembersAcountIds() {

		List<Long> accountIds = new ArrayList<>();

		for (ClanMember clanMember : members) {

			long accountId = clanMember.getAccountId();

			accountIds.add(accountId);
		}

		return accountIds;

	}

	public ClanMember getClanMember(long accountId) {

		ClanMember clanMember = clanMemberInformationMap.get(accountId);

		return clanMember;
	}

	@Override
	public String toString() {
		return "ClanInfo [acceptsJoinRequests=" + acceptsJoinRequests + ", clanId=" + clanId + ", color=" + color
				+ ", createdAt=" + createdAt + ", creatorId=" + creatorId + ", description=" + description
				+ ", descriptionHtml=" + descriptionHtml + ", isClanDisbanded=" + isClanDisbanded + ", leaderId="
				+ leaderId + ", membersCount=" + membersCount + ", motto=" + motto + ", name=" + name + ", oldName="
				+ oldName + ", oldTag=" + oldTag + ", renamedAt=" + renamedAt + ", tag=" + tag + ", updatedAt="
				+ updatedAt + ", emblems=" + getEmblems() + ", members=" + members + ", personal=" + personal
				+ ", clanProvinces=" + clanProvinces + ", totalGoldPerDay=" + totalGoldPerDay
				+ ", totalProvincesOwned=" + totalProvincesOwned + "]";
	}


}
