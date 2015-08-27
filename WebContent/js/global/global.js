/** Global regex's **/
var numericGoldRegex = /^[0-9]{1,3}(?:,?[0-9]{3})*$/;//Any number with optional commas in the thousands place and no fraction
var numericWholeRegex = /^[0-9]*$/;//Any whole number nothing else
var numericMericaTimeRegex = /^(0?[1-9]|1[012])(:[0-5]\d) [AP][M]$/;
var alphaNumericRegex = /^(.)*$/;//Any character besides new line char
var alphaLinkRegex = /(http|ftp|https):\/\/[\w-]+(\.[\w-]+)+([\w.,@?^=%&amp;:\/~+#-]*[\w@?^=%&amp;\/~+#-])?/;
var alphaHexColorRegex = /^#([0-9a-f]{3}){1,2}$/i;
var validDateRegex = /^(((0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/)|((0[1-9]|1[0-2])\/)?)(19|20)\d{2}$/; //TODO right what this exactly is

	
/*************** Cached Selectors ***************/
var $topHeader = $("#top-head");
var $bugReportButton = $(".submit-bug-report");
var $ajaxErrorModal = $("#error-modal");

/************* Initializers ********************/
formatUserMonetaryInfo();
getLastRefreshedDate();
getCopyrightRow();

// Disable caching of AJAX responses
$.ajaxSetup ({
    cache: false
});
// Set up a global ajax error listener function
$(document).ajaxError(function(event, jqxhr, settings, thrownError){
	
	var httpErrorCode = jqxhr.status;
	
	var is400SeriesError = httpErrorCode < 500 && httpErrorCode >= 400;
	
	$('.modal').modal('hide');
	
	var errorMessage = is400SeriesError ? "Something went wrong, if this keeps happening please submit a HELPFUL report" : jqxhr.responseText;
	
	$ajaxErrorModal.find(".modal-body > .error-message-paragraph").html(errorMessage);
	$ajaxErrorModal.modal('show');
});

/*************** Listeners ***************/
$topHeader.on("click", ".header-refresh",  function(){
	
	var $updateUserPromise = $.get("/pms/app/update-user-info");
	$topHeader.slideUp();
		
	$updateUserPromise.done(function(){
		
		var $getTopHeaderPromise = $.ajax({
								        type: "GET",
								        url: '/pms/app/get-top-header',
								        async: false
								    })
		
		$getTopHeaderPromise.done(function(data) {
			
			$topHeader.empty().html(data);
			
			formatUserMonetaryInfo();
			getLastRefreshedDate();
			
		});
		
	}).always(function(){
		$topHeader.slideDown();
	});
		
});
	
$topHeader.on('mouseover', '.header-refresh', function(){
	$(this).find(".fa-refresh").addClass('fa-spin');
});

$topHeader.on("mouseleave", '.header-refresh', function(){
	$(this).find(".fa-refresh").removeClass('fa-spin');
});

$bugReportButton.on("click", function(){
	popupCenter('https://gitreports.com/issue/NYPD/pms', 'Submit Bug Report', 650, 900);
});


/*************** Functions ***************/
function removeClassByWildCard($selector, wildCard){
	
	var cssClassWildcard = "/\b" + wildCard + "\S+/g";
	
	$selector.removeClass(function (index, css){
	    return (css.match(cssClassWildcard) || []).join(' ');
	});
}

function initializeDateRange($inputDateRange, jsonOptions){
	
	jsonOptions = jsonOptions ? jsonOptions : {};
	
	return $inputDateRange.datepicker(jsonOptions);
	
};

/**
 * Simple function that gathers all the users monetary info in the header, and formats it to the user locale format.
 */
function formatUserMonetaryInfo(){
	
	$("#top-head .user-monetary-info").each(function(){
		
		var $this = $(this);
		var amount = parseInt($this.html());
		
		var isValidAmount = amount ? true: false;
		
		if(isValidAmount){
			$this.html(amount.toLocaleString());
		}
		
	});
}

/**
 * Function that opens a popup in the center of the screen and accounts for users with dual monitors. Credit goes to http://www.xtf.dk/
 * @param url
 * @param title
 * @param w
 * @param h
 */
function popupCenter(url, title, w, h) {
    // Fixes dual-screen position                         Most browsers        Firefox
    var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;

    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 2) - (h / 2)) + dualScreenTop;
    var newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

    // Puts focus on the newWindow
    if (window.focus) {
        newWindow.focus();
    }
}

/**
 * Simple function that takes the the Id off the nav button one wishes to highlight
 * and adds the class 'active' to that element.
 * @param navButtonId
 */
function highlightNavButton(navButtonId){
	$(navButtonId).addClass('active');
}

function highlightNavButtons(navButtonArray){
	$.each(navButtonArray, function(index, value){
		$(value).addClass('active');
	});
}

/**
 * Simple function that retrieves the current year, and writes it to 
 * the left footer to show we mean business and will sue on the spot.
 */
function getCopyrightRow(){
	var date = new Date();
	var currentYear = date.getFullYear();
	$('.copyright-row').html("<h4><span>Copyright &#169&#174&#8471 " + currentYear + " Vidya Gaems &#8480&#8482</span></h4>");
}
/**
 * Function that gets the date in the users locale and writes it to the 
 * "Last Refreshed" header section
 */
function getLastRefreshedDate(){
	var date = new Date();
	var dateLocaleTime = date.toLocaleTimeString();
	
	$(".last-refresh-time").html("Profile Last Refreshed: " + dateLocaleTime);
}

/**
 * Function that takes in a selector and iterates throw each one to format the time.
 * This will go in an object when we visit the javascript
 * @param $selector
 */
function formatMilisToLocalizedtime($selector){
	
	$selector.each(function(){
		
		var $this = $(this);
		
		var unixPaymentTime = $this.html();
		var localizedDate = covertUnixEpochToLocalizedDate(unixPaymentTime);
		
		$this.html(localizedDate);
	});
}


function saveInititalInputValues($selector) {
	
	$selector.each(function() { 
	       
	    var $this = $(this);
	       
	    var isCheckbox = $this.is(':checkbox');
	    var isRadio = $this.is(':radio');
	    
	    if(isCheckbox || isRadio){
	        $this.data('initial-value', $this.is(':checked'));
	    }else{
	    	$this.data('initial-value', $this.val());
	    }
	    
	});
}

function checkForDirtyInputs($selector) {
	
    var isDirty = false; 
    
	$selector.each(function() { 
	       
	    var $this = $(this);
	    var valueChanged = false;
	       
	    var isCheckbox = $this.is(':checkbox');
	    var isRadio = $this.is(':radio');
	    
	    if(isCheckbox || isRadio){
	    	valueChanged = $this.data('initial-value') != $this.is(':checked');
	    }else{
	    	valueChanged = $this.data('initial-value') != $this.val();
	    }
	    
	    if(valueChanged) {
	    	isDirty = true;
	    	return false;
	    }
	    
	});
	
	return isDirty;
	
}
	


/**
 * Helper method that takes in a Unix epoch time and converts it to the current users localized date
 * @param epochTime
 * @returns Localized String date
 */
function covertUnixEpochToLocalizedDate(epochTimeMiliseconds){
	
	var date = new Date(0);
	date.setUTCMilliseconds(parseInt(epochTimeMiliseconds));
	
	var validDate = !isNaN(date.getTime()); 
	var localizedDate;
	
	validDate ? localizedDate = date.toLocaleString() : localizedDate = null;
	
	return localizedDate;
}

/**
 * Helper method that takes in a Unix epoch time and converts it to the current users localized time
 * @param epochTime
 * @returns Localized String time
 */
function covertUnixEpochToLocalizedTime(epochTime){
	
	var date = new Date(0);
	date.setUTCSeconds(parseInt(epochTime));
	
	var localizedTime;
	
	date ? localizedTime = date.toLocaleTimeString() : localizedTime = null;
	
	return localizedTime;
}

function covertMillisToLocalizedTime(timeInMillis){
	
	var date = new Date(0);
	date.setTime(timeInMillis)
	
	var localizedTime;
	
	date ? localizedTime = date.toLocaleTimeString() : localizedTime = null;
	
	return localizedTime;
}

function validateInputs($selector){
	
	var validForm = true;
	
	var isForm = $selector.is('form');
	
	var $inputs;
	
	if(isForm) {
		$inputs = $selector.find("input:text, textarea, select");
	} else {
		$inputs = $selector;
	}
	
	$.each($inputs, function(){
		
		var $input = $(this);
		
		var validFieldValue = true;
		var fieldValue = $input.val();
		
		var isNumericField = $input.hasClass("numeric");
		var isAlphaField = $input.hasClass("alpha");
		var isNotOptional = !$input.hasClass("optional");
		
		var notEmpty = $.trim(fieldValue);
		
		if(notEmpty){//notEmpty takes care of any blank selects
		
			if(isNumericField){
				
				var isNumericGold = $input.hasClass("numeric-gold");
				var isNumericDate = $input.hasClass("numeric-date");
				var isNumericWhole = $input.hasClass("numeric-whole");
				var isNumericMericaTime = $input.hasClass("numeric-merica-time");
				
				if(isNumericGold){
					validFieldValue = numericGoldRegex.test(fieldValue);
				}else if(isNumericDate){
					
					var date = new Date();
					date.setTime(fieldValue);
					
					validFieldValue = date != "Invalid Date";
					
				}else if(isNumericWhole){
					validFieldValue = numericWholeRegex.test(fieldValue);
				}else if(isNumericMericaTime){
					validFieldValue = numericMericaTimeRegex.test(fieldValue);
				}
				
			}else if(isAlphaField){
				var isAlphaNumeric = $input.hasClass("alpha-numeric");
				var isAlphaLink = $input.hasClass("alpha-link");
				var isAlphaHexColor = $input.hasClass("alpha-hex-color");
				var isAlphaFormattedDate = $input.hasClass("alpha-formatted-date");
				
				if(isAlphaNumeric){
					validFieldValue = alphaNumericRegex.test(fieldValue);
				}else if(isAlphaLink){
					validFieldValue = alphaLinkRegex.test(fieldValue);
				}else if(isAlphaHexColor){
					validFieldValue = alphaHexColorRegex.test(fieldValue);
				}else if(isAlphaFormattedDate){
					validFieldValue = alphaHexColorRegex.test(fieldValue);
					
					var date = new Date(fieldValue);
					
					validFieldValue = date != "Invalid Date";
				}
			}
			
		}else{
			if(isNotOptional) validFieldValue = false;
		}
		
		
		if(validFieldValue){
			$input.closest(".form-group").removeClass("has-error");
		}else{
			$input.closest(".form-group").addClass("has-error");
			validForm = false;
		}
		
	});
	
	
	return validForm;
}

//DataTable Defaults
$.extend( $.fn.dataTable.defaults, {
    responsive: true,
    stateSave: true,
	"autoWidth": false,
	"dom": '<"col-sm-6"l><"col-sm-6"f>rt<"col-sm-6"i><"col-sm-6"p>'
} );

/*************** Custom Objects ***************/
function DateUtil(date){
	
	var nullDate = date == null;
	this.date = nullDate ? new Date(): date;
}

DateUtil.prototype =
{
    getEndOfYear : function(){
    					var fullYear = this.date.getFullYear();
    					var endOfYearDate = new Date(fullYear, 11, 31);
    					endOfYearDate.setHours(23,59,59,999);
    					
						return  endOfYearDate;
		           },
	getEndOfMonth : function(){
						var fullYear = this.date.getFullYear();
						var month = this.date.getMonth();
						var endOfMonth = new Date(fullYear, month + 1, 0);
						endOfMonth.setHours(23,59,59,999);
						return  endOfMonth;
					},
    getEndOfWeek : function(){
    	
    					var startOfWeek = this.date.getDate() - this.date.getDay();
    					var endOfWeek = new Date(this.date.setDate(startOfWeek + 7));
    					endOfWeek.setMilliseconds(-1);
    						
    					return  endOfWeek;
				    },
	getEndOfDay : function(){
				    	
						var year = this.date.getFullYear();
						var month = this.date.getMonth();
						var day = this.date.getDay();
						
						var endOfDay = new Date(year, month, day, 23, 59, 59, 999);
		
    						
    					return  endOfDay;
				    },
	getStartOfDay: function(){
					
						var year = this.date.getFullYear();
						var month = this.date.getMonth();
						var day = this.date.getDay();
						
						var startOfDay = new Date(year, month, day, 0, 0, 0, 0);
						
						return startOfDay;
		
				    },
	getStartOfWeek : function(){
		
						var startOfWeek = new Date(this.date.setDate(this.date.getDate() - this.date.getDay()));
						startOfWeek.setHours(0,0,0,0);
						return  startOfWeek;
				    },
    getStartOfMonth : function(){
		
				    	var fullYear = this.date.getFullYear();
						var month = this.date.getMonth();
						var startOfMonth = new Date(fullYear, month, 1);
						startOfMonth.setHours(0,0,0,0);
						
						return  startOfMonth;
				    },
    getStartOfYear : function(){
		
    					var fullYear = this.date.getFullYear();
						var startOfYear = new Date(fullYear, 0, 1);
						startOfYear.setHours(0,0,0,0);
						
						return  startOfYear;
				    },
	getStartOfTime : function(){
						
						var startOfTime = new Date(1970,0,1);
						startOfTime.setHours(0,0,0,0);
						
						return  startOfTime;
				    },
	getToday : function(){
						
						var today = new Date();
					
						return today;
				    },
	getUnitedStatesTime : function(){
						
						var muricaTime = this.date.toLocaleTimeString("en-US");
					
						return muricaTime;
				    }
};