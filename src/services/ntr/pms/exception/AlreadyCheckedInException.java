package services.ntr.pms.exception;

import services.ntr.pms.model.information.Player;

/**
 * Custom runtime exception class that is thrown if a player is attempting to check in and
 * he has already checked in the the past 23.99999999999999999 hours
 * @author NYPD
 *
 */
public class AlreadyCheckedInException extends RuntimeException{

	private static final long serialVersionUID = -8622187862350401581L;
	
	private Player player;

	public AlreadyCheckedInException(Player player){
		super();
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}

}
