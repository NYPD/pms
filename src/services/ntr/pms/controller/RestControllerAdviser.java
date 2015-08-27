package services.ntr.pms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import services.ntr.pms.exception.AlreadyCheckedInException;
import services.ntr.pms.exception.CheckInNotWithinBattleTimeFrame;
import services.ntr.pms.exception.ClanAlreadyIsAllowedException;
import services.ntr.pms.exception.NoAttendencesToPayoutException;
import services.ntr.pms.exception.PlayerHasAlreadyBeenCheckedInException;
import services.ntr.pms.exception.PlayerIsBannedException;
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.information.LocalClanSettings;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.service.information.LocalClanSettingsService;
import services.ntr.pms.service.information.PlayerInformationService;

/**
*
*
* Created On: February 18, 2013
* @author Tomas Santos
*/
@ControllerAdvice(annotations = RestController.class)
public class RestControllerAdviser {
	
	@Autowired
	private LocalClanSettingsService localClanSettingsService;
	@Autowired
	private PlayerInformationService playerInformationService;
	
    private static Logger logger = Logger.getLogger(RestControllerAdviser.class);
    
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = PlayerIsBannedException.class)
    @ResponseBody
    public String handlePlayerIsBannedException(PlayerIsBannedException playerIsBanned, HttpSession session) throws IOException{
    	MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
    	long clanId = membersInfo.getClanId();
    	
    	Player player = playerIsBanned.getPlayer();
    	
    	String nickName = player.getNickname();
    	long banTime = player.getPersonal().getBanTime();
    	
    	Date date = new Date(banTime * 1000);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z", Locale.US);
    	String formattedDate = sdf.format(date);
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("Player: ");
    	sb.append(nickName);
    	sb.append(" is attepting to check in even though his ban time is expires on: ");
    	sb.append(formattedDate);
    	
    	String errorMessage = sb.toString();
    	
        logger.info(errorMessage);
        
        LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
        
        String playerIsBannedError = localClanSettings.getPlayerIsBannedError();
        
        return playerIsBannedError;
    }
    
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = AlreadyCheckedInException.class)
    @ResponseBody
    public String handleAlreadyCheckedInException(AlreadyCheckedInException alreadyCheckedInException, HttpSession session){
    	MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
    	long clanId = membersInfo.getClanId();
		
    	Player player = alreadyCheckedInException.getPlayer();
    	String nickName = player.getNickname();

    	StringBuilder sb = new StringBuilder();
    	sb.append("Player: ");
    	sb.append(nickName);
    	sb.append(" has attempted to check in within 24 hours of his previous check in");
    	
    	String errorMessage = sb.toString();
    	logger.info(errorMessage);
    	
    	LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
        
        String playerAlreadyCheckedInError = localClanSettings.getPlayerAlreadyCheckedInError();
        
        return playerAlreadyCheckedInError;
    }
    
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = PlayerHasAlreadyBeenCheckedInException.class)
    @ResponseBody
    public String handlePlayerHasAlreadyBeenCheckedInException(PlayerHasAlreadyBeenCheckedInException playerHasAlreadyBeenCheckedInException, HttpSession session){
    	
    	Attendance attendance = playerHasAlreadyBeenCheckedInException.getAttendance();
    	
    	long accountId = attendance.getAccountId();
    	
    	Player playerInformation = playerInformationService.getPlayerInformation(accountId);
    	
    	String nickname = playerInformation.getNickname();
		
    	StringBuilder sb = new StringBuilder();
    	sb.append("Player: ");
    	sb.append(nickname);
    	sb.append(" (accountId: " + accountId + ")");
    	sb.append(" has already been checked in for the chosen day");
    	
    	String errorMessage = sb.toString();
    	logger.info(errorMessage);
    	
        return errorMessage;
    }
    
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = CheckInNotWithinBattleTimeFrame.class)
    @ResponseBody
    public String handleCheckInNotWithinBatttleTimeFrame(CheckInNotWithinBattleTimeFrame checkInNotWithinTimeFrame, HttpSession session){
    	
    	MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
    	long clanId = membersInfo.getClanId();
		
    	Player player = checkInNotWithinTimeFrame.getPlayer();
    	String nickName = player.getNickname();

    	StringBuilder sb = new StringBuilder();
    	sb.append("Player: ");
    	sb.append(nickName);
    	sb.append(" has attempted to check in outside of the allowable check in time");
    	
    	String errorMessage = sb.toString();
    	logger.info(errorMessage);
    	
    	LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
        
        String playerNotWithinTimeFrameError = localClanSettings.getPlayerNotWithinTimeFrameError();
        
        return playerNotWithinTimeFrameError;
    }
    
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ClanAlreadyIsAllowedException.class)
    @ResponseBody
    public String handleClanAlreadyIsAllowedException(ClanAlreadyIsAllowedException clanAlreadyIsAllowedException){
    	
    	long clanId = clanAlreadyIsAllowedException.getClanId();
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("Clan with ID: ");
    	sb.append(clanId);
    	sb.append(" is already an allowed clan");
    	
    	String errorMessage = sb.toString();
    	
    	logger.info(errorMessage);
        return errorMessage;
    }
    
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = NoAttendencesToPayoutException.class)
    @ResponseBody
    public String handleNoAttendencesToPayoutException(){
    	
    	String errorMessage = "There are no attendances to payout";
    	
        return errorMessage;
    }
    
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String handleException(Exception exception, HttpSession session){
    	
    	MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
    	long clanId = membersInfo.getClanId();
		
    	logger.error("hit rest adviser", exception);
    	
    	LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
        
        String defaultError = localClanSettings.getDefaultError();
    	
        return defaultError;
    }
}