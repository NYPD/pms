$(document).ready(function() { 
	
	highlightNavButtons(['#clan-info','#attendance']);
	
	//Cached Selectors
	var $attendannceHistoryTable = $('#attendance-history-table');
	
	var $timeFrameSearchTypeRadios = $('.timeframe-search-type-container .timeframe-search-type-radio');
	
	var $standardTimeFrameSearchFormContainer = $('.standard-timeframe-search-form-container');
	var $standardDateRadios = $standardTimeFrameSearchFormContainer.find(".date-radios");
	var $standardDatepicker = $standardTimeFrameSearchFormContainer.find('.standard-date-picker-container > .input-group.date');
	var $standardDatepickerInput =  $standardTimeFrameSearchFormContainer.find('.standard-date-picker-container .date-picker-input');
	
	var $customTimeFrameSearchFormContainer = $('.custom-timeframe-search-form-container');
	var $customDatepicker = $customTimeFrameSearchFormContainer.find('.custom-date-picker-container > .input-daterange');
	
	var $submitStandardAttendanceFormButton = $(".submit-standard-attendance-form");//TODO change this class to something for global for time frame search
	var $submitCustomAttendanceFormButton = $(".submit-custom-attendance-form");//TODO change this class to something for global for time frame search
	
	//Initialization
	initializeDatepicker($standardDatepicker);
	initializeRangeDatepicker($customDatepicker);
	initializeAttendanceDataTable($attendannceHistoryTable);
	
	//Listeners
	$timeFrameSearchTypeRadios.on("change", function(){
		var radioValue = $(this).val();
		
		var isStandardSearchType = radioValue == "standard";
		
		if(isStandardSearchType){
			$customTimeFrameSearchFormContainer.addClass('hidden');
			$standardTimeFrameSearchFormContainer.removeClass('hidden');
		}else{
			$standardTimeFrameSearchFormContainer.addClass('hidden');
			$customTimeFrameSearchFormContainer.removeClass('hidden');
		}
		
	});
	
	$standardDateRadios.on("change", function(){
		var radioValue = $(this).val();
		
		var userSelectedAllTime = radioValue == "all";
		
		if(userSelectedAllTime){
			$standardDatepicker.datepicker('remove');
			$standardDatepicker.find("input").val("Birth of Christ");
		}else{
			reinitializeDatepicker($standardDatepicker, radioValue);
		}
		
	});
	$submitCustomAttendanceFormButton.on("click", function(){
		var customDateSelections = $customDatepicker.find('input[type="text"]');
		
		var invalidDateSelections = false;
		
		$.each(customDateSelections, function(){
			
			var $this = $(this);
			
			var dateTextValue = $this.val();
			
			var validInputText =  validDateRegex.test(dateTextValue);
			
			if(validInputText){
				$this.removeClass('has-error');
			}else{
				invalidDateSelections = true;
				$this.addClass('has-error');
			}
			
		});
		
		if(invalidDateSelections) return false;
		
		var startTime = $customDatepicker.find('input[name="start"]').datepicker('getDate').getTime();
		var endTime = $customDatepicker.find('input[name="end"]').datepicker('getDate').getTime();;
		
		submitAttendanceForm(startTime, endTime, $attendannceHistoryTable);
		
		
	});
	
	$submitStandardAttendanceFormButton.on("click", function(){
		
		var standardDatePickerFilterType = $standardDateRadios.filter(":checked").val();
		
		var inputText = $standardDatepickerInput.val();
		
		var validInputText =  validDateRegex.test(inputText) || inputText == "Birth of Christ";
		
		if(validInputText){
			
			$standardDatepicker.removeClass('has-error');
			
			var dateSelected = $standardDatepicker.datepicker('getDate');
			var startTime;
			var endTime;
			
			switch(standardDatePickerFilterType){
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
				case "day":
					startTime = new DateUtil(dateSelected).getStartOfDay().getTime();
				    endTime = new DateUtil(dateSelected).getEndOfDay().getTime();
				    break;
				default:
					$standardDatepicker.datepicker('remove');
					startTime = new DateUtil().getStartOfTime().getTime();
			    	endTime = new DateUtil().getToday().getTime();
			}
			
			submitAttendanceForm(startTime, endTime, $attendannceHistoryTable);
		}else{
			$standardDatepicker.addClass('has-error');
		}
		
	});
	
});

/************* Functions *************/
function initializeAttendanceDataTable($dataTable){
	$dataTable.dataTable({
    	"paging":   true,
    	"pageLength": 100,
    	"pagingType": "simple",
		"order": [[ 2, "desc" ]],
		"searching": true,
		"columnDefs": [ {
		      "targets": [0],
		      "orderable": false
		    } 
		],
		"columns": [
		            {"class": "text-center"},
		            {"class": "text-center"},
		            {"class": "text-center"}
		],
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
function initializeDatepicker($datepicker){
	$datepicker.datepicker({
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

function initializeRangeDatepicker($datepicker){
	$datepicker.datepicker({
		todayHighlight: true,
	    autoclose: true
	});
}

function reinitializeDatepicker($datepicker, minViewMode){
	$datepicker.datepicker('remove');
	
	$datepicker.find("input").val("");
	
	var isMonthSearch = minViewMode == "months";
	var isYearSearch = minViewMode == "years";
	var isWeekSearch = minViewMode == "days";
	var isSingleDaySearch = minViewMode == "day";
	
	//Default day search format
	var format = "mm/dd/yyyy";
	
	if(isMonthSearch) format = "mm/yyyy";
	if(isYearSearch) format = "yyyy";
	
	$datepicker.datepicker({
		minViewMode: isSingleDaySearch ? minViewMode + "s" : minViewMode,//We need to add the s as per plugin requirements
		format: format,
	    weekStart: 0,
	    daysOfWeekDisabled: isSingleDaySearch ? "" : "1,2,3,4,5,6", //Only allows user to select the day that begins the week
	    todayHighlight: true,
	    autoclose: true
	});
	
	var dateUtilObject = new DateUtil();
	
	var date = isWeekSearch ? dateUtilObject.getStartOfWeek() : dateUtilObject.date;
	
	$datepicker.datepicker('update', date);
	
	
}
function submitAttendanceForm(startTime, endTime, $attendannceHistoryTable){
	
	var formSubmitUrl = $attendannceHistoryTable.data("attendance-form-submit");
	var $attendanceDataTable = $attendannceHistoryTable.DataTable();
	
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