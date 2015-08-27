$(document).ready(function() { 
	
	//Cached Selectors
	var $incentiveSummaryTable = $('#incentive-summary-table');
	var $incentivePayoutsTable = $('#incentive-payout-amounts-table');
	var $topIncentivePlayersHighchartsContainer = $('#top-incentive-players-highcharts-container');
	var $topIncentiveTanksHighchartsContainer = $('#top-incentive-tanks-highcharts-container');
	
	//Initializers
	highlightNavButtons(['#clan-info','#incentive']);
	initializeIncetiveSummaryDataTable($incentiveSummaryTable);
	initializeIncetivePayoutsDataTable($incentivePayoutsTable);
	initializeIncentivePlayerPieChart($topIncentivePlayersHighchartsContainer);
	initializeIncentiveTankPieChart($topIncentiveTanksHighchartsContainer);
	
	updateIncentivePieCharts($topIncentivePlayersHighchartsContainer, $topIncentiveTanksHighchartsContainer);
	
});


/************* Functions *************/
function initializeIncetiveSummaryDataTable($dataTable){
	
	$dataTable.dataTable({
    	"paging":   true,
    	"pageLength": 10,
    	"pagingType": "simple",
		"order": [[ 1, "asc" ]],
		"searching": true
    });
	
}

function initializeIncetivePayoutsDataTable($dataTable){
	
	$dataTable.dataTable({
    	"paging":   true,
    	"pageLength": 10,
    	"pagingType": "simple",
		"order": [[ 1, "asc" ]],
		"searching": true,
		"columns": [
           { "width": "65px"},
           { "width": "350px"},
           null
         ]
    });
	
}

function initializeIncentiveTankPieChart($pieChartContainer){
	
	$pieChartContainer.highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null
        },
        title: {
            text: null
        },
        credits: {
            enabled: false
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.y}</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Times Unlocked',
            data: [
                
            ]
        }]
    });
}

function initializeIncentivePlayerPieChart($pieChartContainer){
	
	$pieChartContainer.highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null
        },
        title: {
            text: null
        },
        credits: {
            enabled: false
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b> <br/>Total Gold Recieved: <b>{point.gold_total}</b>'
        },
        plotOptions: {
            pie: {
                dataLabels: {
                    enabled: true,
                    distance: -50,
                    style: {
                        fontWeight: 'bold',
                        color: 'white',
                        textShadow: '0px 1px 2px black'
                    }
                },
                startAngle: -90,
                endAngle: 90,
                center: ['50%', '75%']
            }
        },
        series: [{
            type: 'pie',
            name: 'Tanks Unlocked',
            innerSize: '50%',
            data: []
        }]
    });
}

function updateIncentivePieCharts($topIncentivePlayersHighchartsContainer, $topIncentiveTanksHighchartsContainer){
	
	var $getTopIncentivePlayersPromise = $.get("top-incentive-players");
	var $getTopIncentiveTanksPromise = $.get("top-incentive-tanks");
	
	$getTopIncentivePlayersPromise.done(function(data){
		
		var topIncentivePlayers = data;
		var pieSlices = [];
		
		$.each(topIncentivePlayers, function(){
			
			var nickname = this.nickname;
			var tanksUnlocked = this.tanksUnlocked;
			var totalGoldAmount = this.totalGoldAmount;
			
			pieSlices.push({name: nickname, y:tanksUnlocked, gold_total:totalGoldAmount});
		});
		
		var chart = $topIncentivePlayersHighchartsContainer.highcharts();
		chart.series[0].setData(pieSlices, true);

	});
		
	$getTopIncentiveTanksPromise.done(function(data){
		var topTanksUnlocked  = data;
		var pieSlices = [];
		
		$.each(topTanksUnlocked, function(){
			
			var tankName = this.tankUnlockedName;
			var timesUnlocked = this.timesUnlocked;
			
			pieSlices.push([tankName,timesUnlocked]);
		});
		
		var chart = $topIncentiveTanksHighchartsContainer.highcharts();
		chart.series[0].setData(pieSlices, true);

	});
}