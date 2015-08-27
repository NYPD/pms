package services.ntr.pms.exception;

public class InvalidCookieInformation extends RuntimeException{

	private static final long serialVersionUID = -4952616481435986256L;
	
	private String cookieName;
	
	public InvalidCookieInformation(String cookieName, String message){
		super(message);
		this.cookieName = cookieName;
	}
	
	public String getcookieName(){
		return cookieName;
	}
}
