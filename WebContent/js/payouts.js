highlightNavButton('#payments');

/*************** Cached Selectors ***************/

/* Tables / Charts / Tooltips */
var $transactionHistoryTable = $('#transaction-history-table');
var $transactionBarGraphContainer = $('#transaction-history-highchart');
var $payoutToolTips = $(".payout-tooltip");
var $transactionHistoryTableTimes = $transactionHistoryTable.find(".transaction-payout-time");

/* Panel Stuff */
var $loadingPanel = $('.panel-loading');

/* Off Canvas Stuff */
var $offCanvasRow = $('.row-offcanvas');
var $offConvasButton = $(".btn-offcanvas");
var $offCanvasArrowSymbol = $offConvasButton.find(".fa-offcanvas-arrow");
var $colClanInfo = $('.col-clan-info');

/* Payout Stuff */
var $payoutButton = $(".btn-payout");
var $payoutGroupsModal = $("#payout-groups-modal");
var $payoutSuccessModal = $("#payout-success-modal");
var $unpaidSharesSpan = $(".unpaid-shares-span");

/* Caller Bonus Stuff*/
var $callerBonusBattleAmount = $("#caller-bonus-battle-amount");
var $callerBonusUnpaidBattlesBadge = $("#caller-bonus-unpaid-battles");
var $callerBonusTotalAmountBadge = $("#caller-bonus-total-amount");

/* Tank Incentive Stuff */
var $tankIncentivePanel = $('.panel-tank-incentive');
var $usePresetValuesCheckbox = $tankIncentivePanel.find('.checkbox input[type="checkbox"]');
var $filterIncentiveTiersDropdown = $tankIncentivePanel.find('.input-group-tier-filter > select');
var $filterIncentiveTiersBtn = $tankIncentivePanel.find('.btn-filter-incentive-tiers');
var $incentiveTankDropdown = $('#incentive-tank');
var $incentiveAmountInput = $('#incentive-amount');

/* Datepicker Stuff */
var $inputDateRange = $(".input-daterange");


/*************** Initializers ***************/
formatMilisToLocalizedtime($transactionHistoryTableTimes);
initializeTransactionDataTable($transactionHistoryTable);
initializeTransactionBarGraphChart($transactionBarGraphContainer);
updateBarGraph($transactionBarGraphContainer);
var jsonOptions = {weekStart: 0, format: "mm/dd/yyyy", autoclose: true}
$inputDateRange = initializeDateRange($inputDateRange, jsonOptions);
colorizeUnpaidShareCountLabel($unpaidSharesSpan);
$payoutToolTips.tooltip();
$callerBonusBattleAmount.trigger("change"); //Updates the caller bonus total if there is any


/*************** Listeners ***************/
$offConvasButton.on("click", function() {
	$colClanInfo.toggleClass("active");
	
//	var colClanInfoHasActive = $colClanInfo.hasClass('active');
//	var isNotAffixTopOrBottom = !$colClanInfo.hasClass('affix-top') && !$colClanInfo.hasClass('affix-bottom');
//	
//	if(isNotAffixTopOrBottom) {
//		if(colClanInfoHasActive) {
//			$colClanInfo.animate({
//				right: '15%'
//			}, 250);
//		} else {
//			$colClanInfo.animate({
//				right: '-35%'
//			}, 250);
//		}
//	}
	
	
	
	$offCanvasRow.toggleClass("active");
	$offCanvasArrowSymbol.toggleClass("fa-arrow-right");
});

$payoutButton.on("click", function(){
	var $closestForm = $(this).closest('form');
	var formSubmitURL = $closestForm.data("form-submit-url");
	
	var notValidForm = !validateInputs($closestForm);
	
	if(notValidForm) return;
	
	var $formSubmitPromise = $.post(formSubmitURL, $closestForm.serialize());
	
	var isStandardPayout = $(this).hasClass("btn-payout-standard");
	
	$formSubmitPromise.done(function(data){
		
		if(isStandardPayout){
			$payoutSuccessModal.modal('show');
		}else{
			$payoutGroupsModal.find(".modal-body").empty().html(data);
			$payoutGroupsModal.modal('show')
		}
		
	});
	
});

$payoutGroupsModal.on("click", "#btn-group-payout-finalize", function(){
	var $newCalculatedPayoutGroupTotalForm = $payoutGroupsModal.find("#new-calculated-payout-group-total-form");
	var formSubmitURL = $newCalculatedPayoutGroupTotalForm.data("form-submit-url");
	
	$payoutGroupsModal.modal('hide');
	
	var $payoutGroupFormSubmitPromise = $.post(formSubmitURL, $newCalculatedPayoutGroupTotalForm.serialize());
	
	$payoutGroupFormSubmitPromise.done(function(){
		$payoutSuccessModal.modal('show');
	});
	
});

$payoutGroupsModal.on("click", ".btn-script-generation", function(){
	
	var $this = $(this);
	var $payoutGroupPlayerNicknameH4s = $this.closest(".panel").find(".panel-body .player-nickname");
	
	var payoutAmount = $this.data("gold-amount");
	var playerNicknames = [];
	
	$payoutGroupPlayerNicknameH4s.each(function(){
		playerNicknames.push($(this).text())
	});
	
	
	var locationUrl = "../app/payouts/get-payout-script.user.js?payoutAmount=" + payoutAmount;
	
	$.each(playerNicknames, function(){
		locationUrl += "&playerNicknames=" +  this;
	});
	
	window.location =  locationUrl;
	
});

$inputDateRange.datepicker().on("changeDate", function(e){
	
	var startDate = $("#campaign-start-date").datepicker("getDate");
	var endDate = $("#campaign-end-date").datepicker("getDate");
	
	var startDateInSec = startDate.getTime();
	var endDateInSec = endDate.getTime();
	
	$("#hidden-campaign-start-date").val(startDateInSec);
	$("#hidden-campaign-end-date").val(endDateInSec);

});

$callerBonusBattleAmount.on("keyup change", function(){
	
	var amountEntered = $(this).val();
	var unpaidBattles = $callerBonusUnpaidBattlesBadge.text();
	
	var callerBonusTotalAmount = (Number(amountEntered) * Number(unpaidBattles));
	
	//TODO think if we want to actually show NaN or not
	$callerBonusTotalAmountBadge.text(callerBonusTotalAmount);
	
});$callerBonusBattleAmount.trigger('change');

$usePresetValuesCheckbox.on('change', function () {
	
	var isNotChecked = !$(this).is(':checked');
	
	if(isNotChecked) {
		$filterIncentiveTiersDropdown.prop("disabled", false);
		$filterIncentiveTiersBtn.removeAttr("disabled");
		return false;
	}else {
		$filterIncentiveTiersDropdown.prop("disabled", true);
		$filterIncentiveTiersBtn.attr("disabled", "disabled");
	}
	
	var dropdownAlreadyHasPresetValues = $incentiveTankDropdown.data('is-preset-values') == true;
	
	if(dropdownAlreadyHasPresetValues) return false;
	
	$loadingPanel.addClass('loading');
	
	var $getTankIncentivePresetValuesPromise = $.get('payouts/get-tank-incentive-preset-values');
	
	$getTankIncentivePresetValuesPromise.always(function() {
		$loadingPanel.removeClass('loading');
	}).done(function(data) {
		
		$incentiveTankDropdown.empty();
		
		var incentivePayoutAmountsTableRows = data;
		
		$.each(incentivePayoutAmountsTableRows, function() {
			
			var incentivePayoutAmountsTableRow = this;
			
			var tankId = incentivePayoutAmountsTableRow.tankInformation.tankId;
			var tankName = incentivePayoutAmountsTableRow.tankInformation.nameI18n;
			var tankIncentiveAmount = incentivePayoutAmountsTableRow.tankIncentiveDefaultPayout.amount;
			
			var dropdownOption = '<option value="' + tankId + '"' + ' data-tank-incentive-amount="' + tankIncentiveAmount + '" >' + tankName + '</option>';
			
			$incentiveTankDropdown.append(dropdownOption).data('is-preset-values', 'true');
			
		});
		
		$incentiveTankDropdown.trigger('change');
		
	});
	
});

$filterIncentiveTiersBtn.on('click', function () {
	
	var tierSelected = $filterIncentiveTiersDropdown.val();
	
	$loadingPanel.addClass('loading');
	
	var $getTankInformationByTierPromise = $.get('payouts/get-tank-information-by-tier', {tankTiers : tierSelected});
	
	$getTankInformationByTierPromise.always(function(){
		$loadingPanel.removeClass('loading');
	}).done(function(data) {
		
		$incentiveTankDropdown.empty();
		
		var tankInformationList = data;
		
		$.each(tankInformationList, function() {
			
			var tankInformation = this;
			
			var tankId = tankInformation.tankId;
			var tankName = tankInformation.nameI18n;
			
			var dropdownOption = '<option value="' + tankId + '">' + tankName + '</option>';
			
			$incentiveTankDropdown.append(dropdownOption).data('is-preset-values', 'false');;
			
		});
		
		$incentiveAmountInput.val('');
		
	});
	
});

$incentiveTankDropdown.on('change', function() {
	
	var isNotChecked = !$usePresetValuesCheckbox.is(':checked');
	
	if(isNotChecked) return false;
	
	var incentiveTankAmount = $incentiveTankDropdown.find('option:selected').data('tank-incentive-amount');
	
	$incentiveAmountInput.val(incentiveTankAmount);
	
});$incentiveTankDropdown.trigger('change');
	

/************* Functions *************/
function updateBarGraph($transactionBarGraphContainer){
	var $getTransactionBarGraphDataPromise = $.get("payouts/get-transaction-pie");
		
	$getTransactionBarGraphDataPromise.done(function(data){
		var barData  = data;
		var seriesData = [];
		var categoryNames = [];
		
		$.each(barData, function(){
			
			var name = this.type;
			var amount = this.amount;
			
			seriesData.push(amount);
			categoryNames.push(name);
		});
		var chart = $transactionBarGraphContainer.highcharts();
		chart.xAxis[0].setCategories(categoryNames);
		chart.series[0].setData(seriesData);
	});
}

function colorizeUnpaidShareCountLabel($unpaidSharesSpan){
	
	var unpaidShareAmount = parseInt($unpaidSharesSpan.html());
	
	var isNaN = unpaidShareAmount == NaN;
	
	if(isNaN) return;
	switch (true){
		case unpaidShareAmount < 100:
			removeClassByWildCard($unpaidSharesSpan, "label-");
			$unpaidSharesSpan.addClass("label-success");
	        break;
		case unpaidShareAmount < 300:
			removeClassByWildCard($unpaidSharesSpan, "label-");
			$unpaidSharesSpan.addClass("label-warning");
	        break;
	    default:
	    	removeClassByWildCard($unpaidSharesSpan, "label-");
			$unpaidSharesSpan.addClass("label-danger");
	}
	
}

function initializeTransactionBarGraphChart($barGraphContainer){
	
	$barGraphContainer.highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: null
        },
        colors: ['#FFCC00'],
        yAxis: {
            min: 0,
            title: {
                text: 'Total Gold Amount',
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            }
        },
        xAxis:{
        	labels: {
                style: {
                    fontSize:'14px',
                    fontWeight: 'bold'
                }
            }
        },
        tooltip: {
        	pointFormat: '{series.name}: <b>{point.y}</b><br/>',
            valueSuffix: ' gold'
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },        
        credits: {
            enabled: false
        },
        series: [{
            name: 'Gold Amount',
            data: []
        }]
    });
}

function initializeTransactionDataTable($dataTable){
	$dataTable.dataTable({
    	"paging":   true,
    	"pageLength": 10,
    	"pagingType": "simple",
		"order": [[ 1, "desc" ]],
		"searching": true,
		"columnDefs": [ {
		      "targets": [0],
		      "orderable": false
		    } 
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