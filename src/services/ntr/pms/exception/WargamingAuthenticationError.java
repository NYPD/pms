package services.ntr.pms.exception;

public class WargamingAuthenticationError extends RuntimeException{

	private static final long serialVersionUID = -5745001451261019481L;
	
	private String errorCode;

	public WargamingAuthenticationError(String message, String errorCode){
		super(message);
		this.errorCode = errorCode;
	}
	
	public String getErrorCode(){
		return errorCode;
	}
}
