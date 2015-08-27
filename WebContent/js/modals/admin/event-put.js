$(document).ready(function() { 
	
	/** Cached Selectors **/
	var $btnGroupEventRepeat = 	$('.btn-group-event-repeat');
	var $repeatTypeDropdown = $('#repeat-type');
	var $repeatIntervalGroupAddon = $('#repeat-interval-input-group-addon');
	var $repeatsOnFormGroup = $('.form-group-repeats-on');
	var $repeatsWeeklyCheckboxContainer = $repeatsOnFormGroup.find('.weekly-checkbox-container');
	var $repeatsMonthlyRadioContainer = $repeatsOnFormGroup.find('.monthly-radio-container');
		
	/** Initializers **/
	$('.input-group.date').datepicker({
	    todayHighlight: true,
	    startDate: new Date(),
	    autoclose: true
	});
	$("#event-start-time").timepicker({
		minuteStep: 1
	});
	
	var eventTimeInMilis = $('#event-time').val();
	var thereIsEventTime = eventTimeInMilis;
	var repeatIsNo = $('input[name="repeat"]:checked').val() == '0';
	
	if(thereIsEventTime) setTimeInEventPutForm(eventTimeInMilis);
	if(repeatIsNo) disableRepeatOptions();
	
	$repeatTypeDropdown.trigger('change');
	
	/** Listeners **/
	$btnGroupEventRepeat.on('change', function(){
		
		var repeatValue = $btnGroupEventRepeat.find('input[name="repeat"]:checked').val();
		
		var isRepeatNo = repeatValue == 0;
		var isRepeatYes = repeatValue == 1;
		
		if(isRepeatNo) disableRepeatOptions();
		if(isRepeatYes) enableRepeatOptions();
		
	});
	$repeatTypeDropdown.on('change', function(){
		
		var repeatTypeValue = $(this).val();
		
		var isDaily = repeatTypeValue === "0";
		var isWeekly = repeatTypeValue === "1";
		var isMonthly = repeatTypeValue === "2";
		var isYearly = repeatTypeValue === "3";
		
		if(isDaily){
			$repeatIntervalGroupAddon.text("Day(s)")
			$repeatsOnFormGroup.removeClass('active');
			$repeatsWeeklyCheckboxContainer.removeClass('active');
			$repeatsMonthlyRadioContainer.removeClass('active');
		}else if(isWeekly){
			$repeatIntervalGroupAddon.text("Week(s)")
			$repeatsOnFormGroup.addClass('active');
			$repeatsWeeklyCheckboxContainer.addClass('active');
			$repeatsMonthlyRadioContainer.removeClass('active');
		}
		
		
		//TODO just in case we need it i'll leave it here
		
//		else if(isMonthly){
//			$repeatIntervalGroupAddon.text("Month(s)")
//			$repeatsOnFormGroup.addClass('active');
//			$repeatsWeeklyCheckboxContainer.removeClass('active');
//			$repeatsMonthlyRadioContainer.addClass('active');
//		}else if(isYearly){
//			$repeatIntervalGroupAddon.text("Year(s)")
//			$repeatsOnFormGroup.removeClass('active');
//			$repeatsWeeklyCheckboxContainer.removeClass('active');
//			$repeatsMonthlyRadioContainer.removeClass('active');
//		}
		
	});

});

function disableRepeatOptions(){
	$('#repeat-options-container :input').each(function(){
		$(this).attr('disabled', true);
	});
	$('#repeat-options-container .radio, #repeat-options-container .radio-inline, #repeat-options-container .checkbox-inline').each(function(){
		$(this).addClass('disabled');
	});
}
function enableRepeatOptions(){
	$('#repeat-options-container :input').each(function(){
		$(this).attr('disabled', false);
	});
	$('#repeat-options-container .radio, #repeat-options-container .radio-inline, #repeat-options-container .checkbox-inline').each(function(){
		$(this).removeClass('disabled');
	});
}

//# sourceURL=event-put.js