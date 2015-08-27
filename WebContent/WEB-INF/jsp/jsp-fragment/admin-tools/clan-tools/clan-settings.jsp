<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${context}/js/global/bootstrap-timepicker.min.js"></script>
<link href="${context}/css/global/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css"/>

<div class="row">
	<div class="col-lg-7">
		<div class="panel panel-default">
			<div class="panel-heading"><strong>Error Messages</strong></div>
		  	<div class="panel-body">
		    	<form class="clan-settings-form" role="form">
					<div class="form-group">
					    <label for="player-is-banned">Player Is Banned</label>
					    <textarea id="player-is-banned" class="form-control error-message alpha alpha-numeric" name="playerIsBannedError" rows="2" maxlength="200">${localClanSettings.playerIsBannedError}</textarea>
					</div>
					<div class="form-group">
						<label for="player-is-banned-error-message">Already Checked In</label>
					    <textarea id="already-checked-in" class="form-control error-message alpha alpha-numeric" name="playerAlreadyCheckedInError" rows="2" maxlength="200">${localClanSettings.playerAlreadyCheckedInError}</textarea>
					</div>
					<div class="form-group">
						<label for="not-within-time-frame">Not Within Battle Time Frame</label>
					    <textarea id="not-within-time-frame" class="form-control error-message alpha alpha-numeric" name="playerNotWithinTimeFrameError" rows="2" maxlength="200">${localClanSettings.playerNotWithinTimeFrameError}</textarea>
					</div>
					<div class="form-group">
						<label for="default-error">Default Error</label>
					    <textarea id="default-error" class="form-control error-message alpha alpha-numeric" name="defaultError" rows="2" maxlength="200">${localClanSettings.defaultError}</textarea>
					</div>
				</form>
		  	</div>
		</div>
	</div>
	<div class="col-sm-6 col-lg-5 col-panel-mini-settings">
		<div class="panel panel-default">
			<div class="panel-heading"><strong>Clan War Day Start Time</strong></div>
		  	<div class="panel-body">
		   		<form class="form-horizontal clan-war-start-day-form" role="form">
					<div class="form-group">
					    <label for="clan-war-day-start-time" class="col-sm-3 col-md-4 col-lg-5 control-label">Time</label>
					    <div class="col-sm-9 col-md-8 col-lg-7">
					    	<div class="input-group">
			                    <input type="text" id="clan-war-day-start-time" class="form-control numeric numeric-merica-time" />
			                    <span class="input-group-addon"><i class="fa fa-clock-o fa-quarter-lg"></i></span>
			                </div>
					    </div>
					</div>
				</form>
		  	</div>
		</div>
	</div>
	<div class="col-sm-6 col-lg-5 col-panel-mini-settings">
		<div class="panel panel-default">
			<div class="panel-heading"><strong>Default Caller Battle Amount</strong></div>
		  	<div class="panel-body">
		   		<form class="form-horizontal clan-settings-form" role="form">
					<div class="form-group">
					    <label for="default-caller-battle-amount" class="col-sm-6 col-md-5 control-label">Gold Amount</label>
					    <div class="col-sm-6 col-md-7">
			            	<input type='text' id="default-caller-battle-amount" class="form-control numeric numeric-gold" name="callerAmountPerBattle" value="${localClanSettings.callerAmountPerBattle}" maxlength="7"/>
					    </div>
					</div>
				</form>
		  	</div>
		</div>
	</div>
	<div class="col-xs-12 col-lg-5 col-panel-mini-settings">
		<div class="panel panel-default">
			<div class="panel-heading"><strong>Check-In Time Range</strong></div>
		  	<div class="panel-body">
		   		<form class="form-horizontal clan-settings-form" role="form">
					<div class="form-group">
					    <label for="check-in-before-time" class="col-sm-4 col-md-3 col-lg-5 control-label">Minute(s) Before</label>
					    <div class="col-sm-4 col-md-4 col-lg-7">
			            	<input type='text' id="check-in-before-time" class="form-control numeric numeric-whole" name="battleTimeRangeBeforeMinutes" value="${localClanSettings.battleTimeRangeBeforeMinutes}" maxlength="3"/>
					    </div>
					</div>
					<div class="form-group">
						<label for="check-in-after-time" class="col-sm-4 col-md-3 col-lg-5 control-label">Minute(s) After</label>
					    <div class="col-sm-4 col-md-4 col-lg-7">
			            	<input type='text' id="check-in-after-time" class="form-control numeric numeric-whole" name="battleTimeRangeAfterMinutes" value="${localClanSettings.battleTimeRangeAfterMinutes}" maxlength="3"/>
					    </div>
					</div>
				</form>
		  	</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="clan-setting-save-button-container">
			<button type="button" class="btn btn-primary btn-lg btn-clan-settings-form-submit" data-success-text="Successfully saved settings">Save</button>
		</div>
	</div>
</div>
<script>

	var HOURS_IN_A_DAY = 24;

	var battleTimeOffsetFromUtc = Number(${localClanSettings.battleDayGmtOffset}) * -1;
	var battleTimeOffsetFromUtcHours = Math.floor(battleTimeOffsetFromUtc);
	var battleTimeOffsetFromUtcMinutes = battleTimeOffsetFromUtc % 1;

	var battleTimeOffsetFromUtcIsNegative = battleTimeOffsetFromUtcHours < 0;

	var utcHours = battleTimeOffsetFromUtcIsNegative ? HOURS_IN_A_DAY + battleTimeOffsetFromUtcHours : battleTimeOffsetFromUtcHours;
	var utcMinutes = battleTimeOffsetFromUtcMinutes * 60;

	var currentDate = new Date();
	currentDate.setUTCHours(utcHours);
	currentDate.setUTCMinutes(utcMinutes);
	currentDate.setUTCSeconds(0);

	var hours = currentDate.getHours();
	var minutes = currentDate.getMinutes();
	var isMorning = hours < 12;
	var meridian = isMorning? " AM" : " PM";

	var formattedHours = hours >= 13 ? hours % 12 : hours === 0 ? 12 : hours;

	var isZeroMinutes = minutes == 0;

	if(isZeroMinutes) minutes = "00";

	var formattedTime = formattedHours + ":" + minutes + meridian;
	
	$("#clan-war-day-start-time").timepicker({
		minuteStep: 30,
		defaultTime: formattedTime
	});
	
</script>