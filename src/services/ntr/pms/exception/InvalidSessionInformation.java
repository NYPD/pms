package services.ntr.pms.exception;

public class InvalidSessionInformation extends RuntimeException{

	private static final long serialVersionUID = -4850469912818059361L;

	public InvalidSessionInformation(String message){
		super(message);
	}
}
