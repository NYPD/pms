package services.ntr.pms.exception;

public class ClanAlreadyIsAllowedException extends RuntimeException {
	
	private static final long serialVersionUID = 819125884960125993L;
	
	private long clanId;

	public ClanAlreadyIsAllowedException(long clanId){
		super();
		this.clanId = clanId;
	}
	
	public long getClanId(){
		return this.clanId;
	}

}
