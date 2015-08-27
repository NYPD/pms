highlightNavButtons(['#admin','#clan-settings']);

//Cached Selectors
var $adminLargeModal = $('#admin-large-modal');
var $adminSmallModal = $('#admin-small-modal');
var $adminToolContainer = $("#admin-tool-container");
var $adminLargeModalContent = $adminLargeModal.find(".modal-content");
var $adminSmallModalContent = $adminSmallModal.find(".modal-content");
var $adminSidebarLinks = $(".admin-sidebar-link");

/* For Caller Attendance Tool*/
$adminToolContainer.on("change", ".date-radios", function(){
	
	var $datepicker = $('.standard-date-picker-container > .input-group.date');
	
	var radioValue = $(this).val();
	
	var userSelectedAllTime = radioValue == "all";
	
	if(userSelectedAllTime){
		$datepicker.datepicker('remove');
		$datepicker.find("input").val("Birth of Christ");
	}else{
		reinitializeDatepicker($datepicker, radioValue);
	}
		
});
$adminToolContainer.on("click", "#attendance-timeframe-search", function(){
	
	var $callerAttendanceTable = $('#caller-attendance-table');
	var $dateRadios = $(".date-radios");
	var $datepicker = $('.standard-date-picker-container > .input-group.date');
	var $datepickerInput = $(".date-picker-input");
	
	var searchType = $dateRadios.filter(":checked").val();
	
	var inputText = $datepickerInput.val();
	
	var validInputText =  validDateRegex.test(inputText) || inputText == "Birth of Christ";
	
	if(validInputText){
		
		$datepicker.removeClass('has-error');
		
		var dateSelected = $datepicker.datepicker('getDate');
		var startTime;
		var endTime;
		
		switch(searchType){
			case "years":
			    startTime = new DateUtil(dateSelected).getStartOfYear().getTime();
			    endTime = new DateUtil(dateSelected).getEndOfYear().getTime();
			    break;
			case "months":
				startTime = new DateUtil(dateSelected).getStartOfMonth().getTime();
			    endTime = new DateUtil(dateSelected).getEndOfMonth().getTime();
			    break;
			case "days"://Actually the "week" search
				startTime = new DateUtil(dateSelected).getStartOfWeek().getTime();
			    endTime = new DateUtil(dateSelected).getEndOfWeek().getTime();
			    break;
			default:
				$datepicker.datepicker('remove');
				startTime = new DateUtil().getStartOfTime().getTime();
		    	endTime = new DateUtil().getToday().getTime();
		}
		
		submitAttendanceForm(startTime, endTime, $callerAttendanceTable);
	}else{
		$datepicker.addClass('has-error');
	}
		
});

/* For Player Check-in Tool*/
$adminToolContainer.on("click", ".btn-player-check-in", function(){
	
	var $this = $(this);
	var $closestForm = $this.closest("form");
	
	var invalidForm = !validateInputs($closestForm);
	
	if(invalidForm) return;
	
	var accountId = $closestForm.find("#incentive-playername").val();
	var checkInTime = $closestForm.find(".input-group.date").datepicker('getDate').getTime();
	
	var manualPlayerCheckInPromise = $.post("manual-player-check-in", {accountId:accountId, checkInTime:checkInTime});
	
	manualPlayerCheckInPromise.done(function(){
		
		var successText = $closestForm.data("success-text");
		
		displayNewAdminToolSuccess(successText);
	});
});

/* For Custom Event Tool*/
$adminToolContainer.on("click", ".event-crud", function(){
	
	var $this = $(this);
	
	var eventId = $this.closest("tr").find(".event-id").text();
	var isEditCrud = $this.hasClass("edit-event");
	var isDeleteCrud = $this.hasClass("delete-event");
	
	var ajaxUrl = $this.data("ajax-url");
	
	if(isEditCrud){
		var $getEditModalContentPromise = $.get(ajaxUrl, {eventId: eventId});
		
		$getEditModalContentPromise.done(function(data){
			$adminSmallModalContent.empty().html(data);
			$adminSmallModal.modal();
		});
		
	}else if(isDeleteCrud){
		
		var $getDeleteModalContentPromise = $.get(ajaxUrl, {eventId: eventId});
		
		$getDeleteModalContentPromise.done(function(data){
			$adminSmallModalContent.empty().html(data);
			$adminSmallModal.modal();
		});
		
	}
});

$adminLargeModal.on("click", ".btn-event-put", function(){
	
	var $this = $(this);
	var $closestForm = $this.closest('.modal-content').find("form");
	var formSubmitURL = $closestForm.data("form-submit-url");
	
	var invalidForm = !validateInputs($closestForm);
	
	if(invalidForm) return;
	
	var eventDateChosen = $closestForm.find("#event-start-date").datepicker('getDate');
	var eventTimeChosen = $("#event-start-time").val();
	
	var currentDateString = getStringDateForTimePicker();
	
	var dateTimeChosen = new Date(currentDateString + eventTimeChosen);
	
	var hourChosen = dateTimeChosen.getHours();
	var minutesChosen = dateTimeChosen.getMinutes();
	
	var eventUtcTime = eventDateChosen.setHours(hourChosen,minutesChosen,0,0);
	
	var serializedEventForm = $closestForm.serialize() + "&startTime=" + eventUtcTime;
	
	var $putEventFormPromise = $.post("put-event", serializedEventForm);
	
	$putEventFormPromise.done(function(){
		
		var shouldRefreshToolColumn = $this.data("page-refresh") != undefined;
		var successText = $closestForm.data("success-text");
		
		if(shouldRefreshToolColumn){
			reloadCurrentAdminTool($adminSidebarLinks, $adminToolContainer, successText);
		}else{
			displayNewAdminToolSuccess(successText);
		}
		
	}).always(function(){
		$adminSmallModal.modal('hide');
	});
	
});

/* For Clan Settings Tool*/
$adminToolContainer.on("click", ".btn-clan-settings-form-submit", function(){
	
	var $this = $(this);
	var $clanSettingsForms = $("form");
	var $serializedClanSettingsForms = $(".clan-settings-form").serialize();

	var validForm = true;
	
	$.each($clanSettingsForms, function(){
		validForm = validateInputs($clanSettingsForms);
	});
	
	if(!validForm) return;
	
	//TODO think about making this to global js object
	var timeChosen = $("#clan-war-day-start-time").val();
	
	var dateString = getStringDateForTimePicker();
	
	var dateTimeChosen = new Date(dateString + timeChosen);
	
	var hourChosen = dateTimeChosen.getHours();
	var minutesChosen = dateTimeChosen.getMinutes();

	var isMinutesChosenZero = minutesChosen == 0;

	var minutesChosenAsFraction = isMinutesChosenZero ? 0 : minutesChosen / 60;

	var hourChosenIsGreaterThanTwelve = hourChosen >= 12;

	var hoursAndMinutedCombinedAsFraction = (hourChosen + minutesChosenAsFraction);

	var hourDiffrenceFromMidnight = hourChosenIsGreaterThanTwelve ? 24 - hoursAndMinutedCombinedAsFraction  : hoursAndMinutedCombinedAsFraction * -1;

	var userTimezoneOffsetInMinutes = new Date().getTimezoneOffset();
	var userHourTimezoneOffset = (userTimezoneOffsetInMinutes / 60) * -1;

	var minutesChosenAsFraction;

	var isZeroMinutes = minutesChosen == "0";

	isZeroMinutes ? minutesChosenAsFraction = 0 : minutesChosenAsFraction = ((minutesChosen / 60) * -1)

	var gmtOffsetChosen = hourDiffrenceFromMidnight + userHourTimezoneOffset;
	
	$serializedClanSettingsForms += "&battleDayGmtOffset=" + gmtOffsetChosen;
	
	var $saveClanSettingsFormPromise = $.post("put-clan-settings", $serializedClanSettingsForms);
	
	$saveClanSettingsFormPromise.done(function(){
		
		var successText = $this.data("success-text");
		
		displayNewAdminToolSuccess(successText);
	});

	
});

/* For News Carousel Tool*/
$adminToolContainer.on("click", ".news-crud", function(){
	
	var $this = $(this);
	
	var newsCarouselId = $this.closest("tr").find(".news-banner-id").text();
	var isEditCrud = $this.hasClass("edit-clan-news");
	var isCopyCrud = $this.hasClass("copy-clan-news");
	var isDeleteCrud = $this.hasClass("delete-clan-news");
	
	var ajaxUrl = $this.data("ajax-url");
	
	if(isEditCrud || isCopyCrud){
		var $getEditModalContentPromise = $.get(ajaxUrl, {newsCarouselId: newsCarouselId});
		
		$getEditModalContentPromise.done(function(data){
			$adminLargeModalContent.empty().html(data);
			$adminLargeModal.modal();
		});
		
	}else if(isDeleteCrud){
		
		var $getDeleteModalContentPromise = $.get(ajaxUrl, {newsCarouselId: newsCarouselId});
		
		$getDeleteModalContentPromise.done(function(data){
			$adminSmallModalContent.empty().html(data);
			$adminSmallModal.modal();
		});
		
	}
});

/* For News Carousel Tool*/
$adminToolContainer.on("click", ".filter-incentive-tiers", function(){
	
	var tierSelected = $(this).closest('.input-group-tier-filter').find('#tier-select').val();
	
	var selectedAllTiers = tierSelected == "all"
		
	if(selectedAllTiers) tierSelected = [0,1,2,3,4,5,6,7,8,9,10].join();
	
	$adminToolContainer.addClass('loading');
	$adminToolLoadingContainer.show();
	
	var $getIncentiveModalContentPromise = $.get('get-incentive-payout-amounts-table-rows-by-tier', {tankTiers : tierSelected});
	
	$getIncentiveModalContentPromise.always(function() {
		
		$adminToolContainer.removeClass('loading');
		$adminToolLoadingContainer.hide();
		
	}).done(function(data){
		
		var tankIncentiveAmountTableRows = data;
		
		var $tankIncentiveAmountsTable = $('#tank-incentive-amounts-table').DataTable().clear();
		
		var saveLink = '<a class="save-incetive-payout-amount">Save</a>';
		
		$.each(tankIncentiveAmountTableRows, function(){
			var tankIncentiveAmountTableRow = this;
			
			var tankIncentiveDefaultPayoutId = tankIncentiveAmountTableRow.tankIncentiveDefaultPayout.tankIncentiveDefaultPayoutId;
			var tankId = tankIncentiveAmountTableRow.tankInformation.tankId;
			var tankTier = tankIncentiveAmountTableRow.tankInformation.level;
			var tankName = tankIncentiveAmountTableRow.tankInformation.nameI18n;
			var goldAmount = tankIncentiveAmountTableRow.tankIncentiveDefaultPayout.amount;
			
			var goldAmountInput = '<div class="form-group"><input type="text" class="form-control input-sm input-payout text-right numeric numeric-gold" value="'+ goldAmount +'"/></div>';
			
			$tankIncentiveAmountsTable.row.add({
				  "DT_RowClass": "tank-incentive-default-payout",
				  "DT_RowData" : {"tank-id" : tankId, "tank-incentive-default-payout-id" : tankIncentiveDefaultPayoutId},
			      "tankTier": tankTier,
			      "tankName": tankName,
			      "goldAmountInput": goldAmountInput,
			      "saveLink": saveLink
			});
			
		});
		
		$tankIncentiveAmountsTable.draw();
	});
	

});

$adminToolContainer.on("click", ".save-incetive-payout-amount", function(){
	
	var $this = $(this);
	
	var $tankIncentiveDefaultPayout = $this.closest('.tank-incentive-default-payout');
	var $inputPayout = $tankIncentiveDefaultPayout.find('.input-payout');
	
	var isNotDirty = !checkForDirtyInputs($inputPayout);
	
	if(isNotDirty) return false;
	
	var validForm = validateInputs($inputPayout);
	
	if(!validForm) {
		displayNewAdminToolDanger("Invalid value amount");
		return false;
	}
	
	var tankIncentiveDefaultPayoutId = $tankIncentiveDefaultPayout.data('tank-incentive-default-payout-id');
	var tankId = $tankIncentiveDefaultPayout.data('tank-id');
	var amount = $tankIncentiveDefaultPayout.find('.input-payout').val();
	
	var tankIncentiveDefaultPayouts = [];
	
	tankIncentiveDefaultPayouts.push({
		tankIncentiveDefaultPayoutId: tankIncentiveDefaultPayoutId,
		tankId: tankId,
        amount: amount
    });
	
	$this.text('Saving...')
	
	var $saveTankIncentiveDefaultPayoutsPromise = $.ajax({
														type : "POST",
														url : "save-tank-incentive-default-payouts",
														contentType : "application/json",
														data : JSON.stringify(tankIncentiveDefaultPayouts)
													});
	
	$saveTankIncentiveDefaultPayoutsPromise.always(function() {  //The always is for when an ajax error occurs, we want to set the text back
		$this.text("Save");
	}).done(function() {
		
		var $closestTd = $this.text("Saved").closest('td');
		
		$closestTd.addClass("save-success").delay(2500).queue(function(){
			
			$closestTd.removeClass("save-success").dequeue().find('a').text("Save");
			
	    });
		
		saveInititalInputValues($inputPayout);
		
		
		//Might remove, essentialy this will let us know that this record exists in the database, so any changes to it should be an UPDATE
		$tankIncentiveDefaultPayout.data('tank-incentive-default-payout-id', '420')
	});

});
$adminToolContainer.on("click", "#save-all-table-incentive-amounts", function(){
	
	var tankIncentiveDefaultTable = $('#tank-incentive-amounts-table').DataTable();
	var tableInputPayouts = tankIncentiveDefaultTable.$('.input-payout');
	
	var isNotDirty = !checkForDirtyInputs(tableInputPayouts);
	
	if(isNotDirty) {
		displayNewAdminToolDanger("Nothing to save...");
		return false;
	}
		
	var validForm = validateInputs(tableInputPayouts);
	
	if(!validForm) {
		displayNewAdminToolDanger("Invalid value(s) amount");
		return false;
	}
	
	var tankIncentiveDefaultPayouts = [];
	var dirtyTableInputs = [];
	
	$.each(tableInputPayouts, function() {
		
		var $this = $(this);
		
		var isNotDirty = $this.val() == $this.data('initial-value');
		
		if(isNotDirty) return true;
		
		dirtyTableInputs.push(this);
		
		var $tankIncentiveDefaultPayout = $this.closest('.tank-incentive-default-payout');
		
		var tankIncentiveDefaultPayoutId = $tankIncentiveDefaultPayout.data('tank-incentive-default-payout-id');
		var tankId = $tankIncentiveDefaultPayout.data('tank-id');
		var amount = $this.val();
		
		tankIncentiveDefaultPayouts.push({
			tankIncentiveDefaultPayoutId: tankIncentiveDefaultPayoutId,
	        tankId: tankId,
	        amount: amount
	    });
		
	});
	
	var $saveTankIncentiveDefaultPayoutsPromise = $.ajax({
															type : "POST",
															url : "save-tank-incentive-default-payouts",
															contentType : "application/json",
															data : JSON.stringify(tankIncentiveDefaultPayouts)
														});
	
	$saveTankIncentiveDefaultPayoutsPromise.done(function() {
		displayNewAdminToolSuccess("Successfully Saved");
		saveInititalInputValues(tableInputPayouts);
		
		//Might remove, essentialy this will let us know that this record exists in the database, so any changes to it should be an UPDATE
		$.each(dirtyTableInputs, function() {
			$(this).closest('.tank-incentive-default-payout').data('tank-incentive-default-payout-id', '420')
		});
		
	});
		
	
});


/***** Functions ******/
function setTimeInEventPutForm(eventTimeInMilis){
	
	var eventDate = new Date()
	eventDate.setTime(eventTimeInMilis);
	
	$('.input-group.date').datepicker('update', eventDate);
	
	var dateUtil = new DateUtil(eventDate);
	
	var unitedStatesTime = dateUtil.getUnitedStatesTime();
	
	$('#event-start-time').timepicker('setTime', unitedStatesTime);
	
	
}
function getStringDateForTimePicker(){

	var date = new Date();
	
	var intMonth = date.getMonth() + 1;
	var intDay = date.getDate();
	var intYear = date.getFullYear();
	
	var intHours = date.getHours();
	
	var formatedDateforTimePicker = "" + intMonth + "/" + intDay + "/" + intYear + " ";

	return formatedDateforTimePicker;
}