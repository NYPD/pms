package services.ntr.pms.factory;

import java.io.Serializable;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import services.ntr.pms.annotation.Factory;
import services.ntr.pms.model.information.ClanMember;

@Factory
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PlayerFactory implements Serializable {

	private static final long serialVersionUID = -7329843731628351569L;
	
	private Map<Long, ClanMember> clanMemberInformationMap;

	public Map<Long, ClanMember> getPlayerInformationMap() {
		return clanMemberInformationMap;
	}
	public void setPlayerInformationMap(Map<Long, ClanMember> clanMemberInformationMap) {
		this.clanMemberInformationMap = clanMemberInformationMap;
	}
	
	//Helper Methods
	public ClanMember getPlayer(long accountId) {
		
		ClanMember clanMember = this.clanMemberInformationMap.get(accountId);

		return clanMember;
	}
	
}
