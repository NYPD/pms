// ==UserScript==
// @name        payout
// @namespace   http://na.wargaming.net/clans/${clanId}/
// @include     http://na.wargaming.net/clans/${clanId}/
// @version     1
// @grant       none
// ==/UserScript==

var playerPayoutList = ${playerPayoutList};
var goldAmount = ${goldAmount};
var scriptId = '${scriptId}';

var lastPayoutScriptIdRanAndCompleted = localStorage.getItem("lastPayoutScriptIdRanAndCompleted");
var scriptHasNotAlreadyRan = scriptId !== lastPayoutScriptIdRanAndCompleted;

var goldPayoutInterval = $.noop();

if(scriptHasNotAlreadyRan) payoutInterval = setInterval(initializePayout,2000);

function initializePayout()
{
	var $clansInfoContentTable = $('.js-members_list');
	var $playerToBePayoutLinks = $clansInfoContentTable.find('.user-link').filter(byPlayersInPlayerPayoutList);

	var $playerToBePayoutRows = $playerToBePayoutLinks.closest('tr');
	var $playerToBePayoutCheckboxs = $playerToBePayoutRows.find('.b-checkbox_checker');
	var $allocateFundsButton = $('.js-action-separate-treasury');

	$playerToBePayoutCheckboxs.click();
	$allocateFundsButton.click();  
	
	goldPayoutInterval = setInterval(setGoldPayout,1000);
};

function setGoldPayout()
{
    var $currencyGoldInput  = $('.gold__input > input');    
    var currencyGoldInputIsOnThePage = $currencyGoldInput.size() > 0;
    $currencyGoldInput.val(goldAmount);
    $currencyGoldInput.change();

    if(currencyGoldInputIsOnThePage)
    {	
        clearGoldPayoutInterval();
        clearPayoutInterval();
        localStorage.setItem("lastPayoutScriptIdRanAndCompleted", scriptId); 
    };
};

function byPlayersInPlayerPayoutList()
{
    var $this = $(this);
    var playerName  = $this.text();
    playerName  = $.trim(playerName);

    return $.inArray(playerName,playerPayoutList) !== -1;   
};

function clearPayoutInterval()
{
	 clearInterval(payoutInterval);
};

function clearGoldPayoutInterval()
{
	 clearInterval(goldPayoutInterval);
};