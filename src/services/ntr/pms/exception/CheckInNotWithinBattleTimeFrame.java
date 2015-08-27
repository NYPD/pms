package services.ntr.pms.exception;

import services.ntr.pms.model.information.Player;

public class CheckInNotWithinBattleTimeFrame extends RuntimeException{

	private static final long serialVersionUID = 7521351804781312851L;
	
	private Player player;

	public CheckInNotWithinBattleTimeFrame(Player player){
		super();
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
}
