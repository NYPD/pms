package services.ntr.pms.exception;

public class InvalidClanId extends RuntimeException {

	private static final long serialVersionUID = -7174250560247012444L;
	
	private long clanId;

	public InvalidClanId(long clanId){
		super();
		this.clanId = clanId;
	}
	
	public long getClanId(){
		return this.clanId;
	}
}
