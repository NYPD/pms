$(document).ready(function() { 
	
	/* Setting up the page */
	highlightNavButton('#home');
	formatPayoutHistoryTable();
	formatBattleAndEventTimes();
	
	/* Initializers */
	initializePayoutHistoryTable();
	initializePayoutChart();
	
	/*************** Cached Selectors ***************/
	var $checkInButton = $(".check-in-button");
	var $replayUploadButton = $(".btn-replay-upload");
	var $replayInput = $("#replay-input-file");
	var $replayUploadContainer = $(".replay-upload");
	var $highchartYearSelection = $(".highchart-year-selection");
	var $checkInErrorModal = $('#check-in-error-modal');
	
	/*************** Listeners ***************/
	$checkInButton.on("click", function(){
		
		$checkInButton.button('loading');
		
		//Timeout set for now since the action might be too fast
		setTimeout(function(){
			var $checkInPromise = $.ajax({
				url: "../app/check-in", 
				global: false, 
				error: function(xhr, ajaxOptions, thrownError){
					var responseText = (xhr.responseText);
					$checkInErrorModal.find(".modal-body p").html(responseText);
					$checkInErrorModal.modal('show');
					$checkInButton.button('reset');
				}});
			
			$checkInPromise.done(function(data){
				$checkInButton.removeClass("btn-primary");
				$checkInButton.addClass("btn-success");
				$checkInButton.button('complete');
			});
		
		}, 500);
		
	});
	
	$replayInput.on("change", function(){
		
		 var fileName = $(this).val();
		 
		 var isNotEmptyFile = fileName;
		 
		 if(isNotEmptyFile){
			 
			 var validFileSelected = fileName.lastIndexOf("wotreplay") === fileName.length-9;
			  
			 if(validFileSelected){
				 $replayUploadButton.removeClass('disabled');
				 $replayUploadContainer.removeClass('replay-file-error');
				 $replayInput.tooltip('destroy');
			 }else{
				 $replayUploadButton.addClass('disabled');
				 $replayUploadContainer.addClass('replay-file-error');
				 $replayInput.tooltip('show');
			 }
		 }
		 
	});
	
	
	$highchartYearSelection.on("change", function(){
		
		var yearSelected = $(this).val();
		
		var year = new Date();
		year.setFullYear(yearSelected);
		
		var dateUtilObject = new DateUtil(year);
		
		var startTime = dateUtilObject.getStartOfYear().getTime();
		var endTime = dateUtilObject.getEndOfYear().getTime();
		
		var $highchartYearSelectionPromise =  $.get("get-updated-highchart-information", {startTime: startTime, endTime: endTime});
		
		$highchartYearSelectionPromise.done(function(data){
			
			var chartTitle = yearSelected + " Monthly Gold Payouts";
			var chartData = data;
			var chart = $('#payout-history-highchart').highcharts();
			
			chart.setTitle({ text: chartTitle });
			chart.series[0].remove();
			chart.addSeries({name:"Amount", data:chartData});
			
		});
		
	});
	
	//TODO ask tomas wut to do
	$highchartYearSelection.trigger('change');
	
});// End of document ready
function initializePayoutHistoryTable(){
	$('#payout-history-table').DataTable({
    	"paging":   true,
    	"pagingType": "simple_numbers",
    	"columnDefs": [
    	    {"searchable": false, "targets": 1}
    	],
		"order": [[ 3, "desc" ]],
		"drawCallback": function (oSettings) {
			/* Need to redo the counters if filtered or sorted */
			if (oSettings.bSorted || oSettings.bFiltered){
				for (var i = 0, iLen = oSettings.aiDisplay.length ; i < iLen ; i++){
					$('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr).html(i+1);
				}
			}
		}
		
    });
}

function initializePayoutChart(){
	
	$('#payout-history-highchart').highcharts({
        chart: {
            type: 'column',
            height: 300
        },
        title: {
            //text: "2013 Montly Gold Payout",
            align: 'center'
        },
        xAxis: {
            categories: ["Jan.","Feb.","Mar.","Apr.","May","June","July","Aug.","Sept.","Oct.","Nov.","Dec."]
        },
        yAxis: {
            allowDecimals: false,
            title: {
                text: "Payout Amount"
            }
        },
        credits: {
            enabled: false
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b><br/>',
            valueSuffix: ' gold'
        },
        colors: [
            'rgba(252, 222, 108, 1)'
        ],
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
            	allowPointSelect: false,
                borderWidth: 2,
                borderColor: 'rgba(225, 184, 79, 1)' 
            }
        },
        series: [{
        	name: 'Amount',
            data: []   
        }]

	});
	
	
}

function formatBattleAndEventTimes(){
	
	$(".battle-info > .map-info .battle-time").each(function(){
		
		var $this = $(this);
		
		var unixBattleTime = $this.html();
		var localizedBattleTime = covertUnixEpochToLocalizedTime(unixBattleTime);
		
		$this.html(localizedBattleTime);
		
	});
	
	$(".battle-info > .map-info .event-time").each(function(){
		
		var $this = $(this);
		
		var eventTimeMillis = $this.html();
		var localizedBattleTime = covertMillisToLocalizedTime(eventTimeMillis);
		
		$this.html(localizedBattleTime);
		
	});
}
function formatPayoutHistoryTable(){
	
	$("#payout-history-table .payout-date").each(function(){
		
		var $this = $(this);
		
		var unixPaymentTime = $this.text();
		var localizedDate = covertUnixEpochToLocalizedDate(unixPaymentTime);
		
		$this.html(localizedDate);
	});
	
	$("#payout-history-table .payout-amount").each(function(){
		
		var $this = $(this);
		
		var payoutAmount = Number($this.text());
		var localizedPayoutAmount = payoutAmount.toLocaleString();
		
		$this.html(localizedPayoutAmount);
	});
}
