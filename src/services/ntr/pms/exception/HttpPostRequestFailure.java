package services.ntr.pms.exception;

public class HttpPostRequestFailure extends RuntimeException{
	
	private static final long serialVersionUID = -4535705570802714787L;
	
	String location;
	
	public HttpPostRequestFailure(String message, String location){
		super(message);
		this.location = location;
	}

	public String getLocation(){
		return location;
	}
}
