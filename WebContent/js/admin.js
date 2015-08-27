//Cached Selectors
var $adminLargeModal = $('#admin-large-modal');
var $adminSmallModal = $('#admin-small-modal');
var $adminModals = $(".modal-admin");
var $adminLargeModalContent = $adminLargeModal.find(".modal-content");
var $adminSmallModalContent = $adminSmallModal.find(".modal-content");
var $adminSidebarLinks = $(".admin-sidebar-link");
var $adminToolContainer = $("#admin-tool-container");
var $adminToolLoadingContainer = $('.col-admin-tool > .admin-tool-loading-container');

//Initializers
loadLastToolIfAny($adminToolContainer, $adminSidebarLinks);//sorry tomas


//Listeners

/* Global Tool Listeners*/
$adminToolContainer.on("click", ".btn-large-modal", function(){
	
	var ajaxUrl = $(this).data("modal-url");
	
	var $getModalContentPromise = $.get(ajaxUrl);
	
	$getModalContentPromise.done(function(data){
		$adminLargeModalContent.empty().html(data);
		$adminLargeModal.modal();
	});
	
});

$adminToolContainer.on("click", ".btn-small-modal", function(){
	
	var ajaxUrl = $(this).data("modal-url");
	
	var $getModalContentPromise = $.get(ajaxUrl);
	
	$getModalContentPromise.done(function(data){
		$adminSmallModalContent.empty().html(data);
		$adminSmallModal.modal();
	});
	
});

$adminToolContainer.on("click", ".btn-tool-form-submit", function(){
	
	var $this = $(this);
	var $closestForm = $this.closest("form");
	var formSubmitURL = $closestForm.data("form-submit-url");
	
	var invalidForm = !validateInputs($closestForm);
	
	if(invalidForm) return;
	
	var $toolFormSubmitPromise = $.post(formSubmitURL, $closestForm.serialize())
	
	$toolFormSubmitPromise.done(function(){
		
		var successText = $closestForm.data("success-text");
		
		displayNewAdminToolSuccess(successText);
		
	});
		
});
$adminModals.on("click", ".btn-modal-form-submit", function(){
	
	//TODO ask tomas why closest does not work for $(this) in finding the form
	var $this = $(this);
	var $closestForm = $this.closest('.modal-content').find("form");
	var formSubmitURL = $closestForm.data("form-submit-url");
	
	var invalidForm = !validateInputs($closestForm);
	
	if(invalidForm) return;
	
	var $formSubmitPromise = $.post(formSubmitURL, $closestForm.serialize());
	
	/**
	 * If the button has a page refresh data attribute, the tool column get refreshed. And when the 
	 * ajax call finishes, it always closes any modal that is open.
	 */
	$formSubmitPromise.done(function(){
		
			var shouldRefreshToolColumn = $this.data("page-refresh") != undefined;
			var successText = $closestForm.data("success-text");
			
			if(shouldRefreshToolColumn){
				reloadCurrentAdminTool($adminSidebarLinks, $adminToolContainer, successText);
			}else{
				displayNewAdminToolSuccess(successText);
			}
			
		}).always(function(){
			$adminLargeModal.modal('hide');
			$adminSmallModal.modal('hide');
		});
		
});
$adminSidebarLinks.on("click", function(event){
	
	var $link = $(this);
	var ajaxURI = $link.data("admin-tool-url");
	
	sessionStorage.setItem("lastToolURI", ajaxURI);
	sessionStorage.setItem("link", $link)
	
	$adminToolContainer.empty();
	$adminToolLoadingContainer.show();
	
	var $getAdminToolPromise = $.get(ajaxURI);
	
	$getAdminToolPromise.always(function() {
		$adminToolLoadingContainer.hide();
	}).done(function(data){
		$adminSidebarLinks.removeClass('active');
		$link.addClass('active');
		$adminToolContainer.html(data);
	});
	
});
	

/************* Functions *************/
function loadLastToolIfAny($adminToolContainer, $adminSidebarLinks){
	
	var lastToolURI = sessionStorage.getItem("lastToolURI");
	
	var nullToolURI = lastToolURI == null;
	var notCorrectURIForPage = $adminSidebarLinks.filter("[data-admin-tool-url='" + lastToolURI + "']").length == 0;
	
	if(nullToolURI || notCorrectURIForPage) lastToolURI = $adminSidebarLinks.filter(":first").data("admin-tool-url");
	
	$adminToolContainer.empty();
	$adminToolLoadingContainer.show();
		
	var $getAdminToolPromise = $.get(lastToolURI);
	
	$getAdminToolPromise.always(function() {
		$adminToolLoadingContainer.hide();
	}).done(function(data){
		$adminSidebarLinks.removeClass('active');
		$adminSidebarLinks.filter("[data-admin-tool-url='" + lastToolURI + "']").addClass('active');
		$adminToolContainer.html(data);
	});
	
}

function reloadCurrentAdminTool($sidebarLinks, $htmlContainer, successText){
	
	var ajaxUrl = $sidebarLinks.filter(".active").data("admin-tool-url");
	
	var $getAdminToolPromise = $.get(ajaxUrl);
	
	$getAdminToolPromise.done(function(data){
		$htmlContainer.empty().html(data);
		
		var isSuccessTankDefined = successText != undefined;
		if(isSuccessTankDefined) displayNewAdminToolSuccess(successText);
	});
	
	
}
//TODO SAME EXACT THING as in the attendance.js
function initializeDatepicker($datepicker){
	$datepicker = $('.standard-date-picker-container > .input-group.date').datepicker({
	    weekStart: 0,
	    format: "mm/dd/yyyy",
	    daysOfWeekDisabled: "1,2,3,4,5,6",
	    todayHighlight: true,
	    autoclose: true
	   
	});
	
	var todaysDate = new Date();
	var startOfCurrentWeek = new DateUtil(todaysDate).getStartOfWeek();
	
	$datepicker.datepicker('update', startOfCurrentWeek);
}
//TODO SAME EXACT THING as in the attendance.js
function reinitializeDatepicker($datepicker, minViewMode){
	$datepicker.datepicker('remove');
	
	$datepicker.find("input").val("");
	
	var isMonthSearch = minViewMode == "months";
	var isYearSearch = minViewMode == "years";
	
	//Default day search format
	var format = "mm/dd/yyyy";
	
	if(isMonthSearch) format = "mm/yyyy";
	if(isYearSearch) format = "yyyy";
	
	$datepicker.datepicker({
		minViewMode: minViewMode,
		format: format,
	    weekStart: 0,
	    daysOfWeekDisabled: "1,2,3,4,5,6", //Only allows user to select the day that begins the week
	    todayHighlight: true,
	    autoclose: true
	});
	
	$datepicker.datepicker('update', new Date());
}

//TODO perhaps make a global table initializer AND MAKE IT BETTER
function initializeAdminToolDataTable($dataTable, orderOption, searching){
	$dataTable.dataTable({
    	"paging":   true,
    	"pagingType": "simple",
		"order": [orderOption],
		"searching": searching
    });
	
}
//TODO perhaps make a global table initializer AND MAKE IT BETTER
function initializePrivlegesDataTable($dataTable){
	$dataTable.dataTable({
    	"paging":   true,
    	"pagingType": "simple",
		"drawCallback": function (oSettings) {
			/* Need to redo the counters if filtered or sorted */
			if ( oSettings.bSorted || oSettings.bFiltered )
			{
				for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
				{
					$('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( i+1 );
				}
			}
		}
    });
	
}

//TODO perhaps make a global table initializer AND MAKE IT BETTER
function initializeCallerAttendanceDataTable($dataTable, orderOption, searching, index){
	$dataTable.dataTable({
    	"paging":   true,
    	"pagingType": "simple",
		"order": [orderOption],
		"searching": searching,
		"columns": [
		            {"class": "text-center"},
		            {"class": "text-center"},
		            {"class": "text-center"}
		],
		"drawCallback": function (oSettings) {
			
			//If the table wants an index, god this is so bad
			if(index){
			
				/* Need to redo the counters if filtered or sorted */
				if ( oSettings.bSorted || oSettings.bFiltered )
				{
					for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
					{
						$('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( i+1 );
					}
				}
			
			}
		}
		
    });
	
}

//TODO same stupid function as attendance js
function submitAttendanceForm(startTime, endTime, $callerAttendanceTable){
	
	var formSubmitUrl = $callerAttendanceTable.data("attendance-form-submit");
	var $attendanceDataTable = $callerAttendanceTable.DataTable();
	
	var $attendanceFormPromise = $.get(formSubmitUrl, {startTime: startTime , endTime:endTime});
	
	$attendanceFormPromise.done(function(data){
		$attendanceDataTable.clear();
		
		$.each(data, function(index, element){
			var nickname = element.nickname;
			var attendancePoints = element.points;
			$attendanceDataTable.row.add(["", nickname, attendancePoints]);
		});
		
		$attendanceDataTable.draw();
		
	});
}

function displayNewAdminToolSuccess(successText){
	var $rowAlert = $("#row-alert");
	
	//TODO ask tomas about this, this is found in the admin-modals
	var $successAlert = $("#success-alert");
	
	var $successAlertClone = $successAlert.clone();
	
	$successAlertClone.find(".alert-text").html(successText + " ");

	$rowAlert.append($successAlertClone.fadeIn().delay(3000).fadeOut());
	
}
function displayNewAdminToolDanger(dangerText){
	var $rowAlert = $("#row-alert");
	
	//TODO ask tomas about this, this is found in the admin-modals
	var $dangerAlert = $("#danger-alert");
	
	var $dangerAlertClone = $dangerAlert.clone();
	
	$dangerAlertClone.find(".alert-text").html(dangerText + " ");

	$rowAlert.append($dangerAlertClone.fadeIn().delay(3000).fadeOut());
	
}