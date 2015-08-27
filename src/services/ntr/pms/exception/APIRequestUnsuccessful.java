package services.ntr.pms.exception;

/**
 * Custom Exception class that defines when a API request came back with a status of "error".
 * It includes the field the api "didn't like" and the error message.
 * 
 * @author NYPD
 *
 */
public class APIRequestUnsuccessful extends RuntimeException{

	private static final long serialVersionUID = -4436580768129473086L;
	
	private String field;
	private String value;

	public APIRequestUnsuccessful(String message, String field, String value){
		super(message);
		this.field = field;
		this.value = value;
	}
	
	public String getField(){
		return field;
	}

	public String getValue(){
		return value;
	}

}
