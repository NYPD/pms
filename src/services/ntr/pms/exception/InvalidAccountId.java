package services.ntr.pms.exception;

public class InvalidAccountId extends RuntimeException {

	private static final long serialVersionUID = 7771038960914485086L;
	
	private long accountId;

	public InvalidAccountId(long accountId){
		super();
		this.accountId = accountId;
	}
	
	public long getAccountId(){
		return this.accountId;
	}
}
