package services.ntr.pms.exception;

import services.ntr.pms.model.checkin.Attendance;

public class PlayerHasAlreadyBeenCheckedInException extends RuntimeException{
	
private static final long serialVersionUID = -8622187862350401581L;
	
	private Attendance attendance;

	public PlayerHasAlreadyBeenCheckedInException(Attendance attendance){
		super();
		this.attendance = attendance;
	}
	
	public Attendance getAttendance(){
		return attendance;
	}

}
