package services.ntr.pms.exception;

import services.ntr.pms.model.information.Player;

public class PlayerIsBannedException extends RuntimeException{

	private static final long serialVersionUID = 6211327598533075749L;
	
	private Player player;

	public PlayerIsBannedException(Player player){
		super();
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
}
