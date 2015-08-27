<!DOCTYPE html>
<html lang="en-us">
	<head>
		<%@include file="jsp-fragment/standard-head-block.jsp"%>
		
		<link href="${context}/css/global/datepicker3.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/global/table-timeframe-search.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/attendance.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<div id="header-div">
			<%@include file="jsp-fragment/top-header.jsp"%>
  			<%@include file="jsp-fragment/topNav.jsp"%>
		</div>
		<div class="container">
		
			<div class="row">
		        <div class="col-xs-12">
		          	<h2>Attendance History</h2>
		        </div>
		    </div>
		    
		    <div class="row">
		        <div class="col-xs-12">
		          	<div class="timeframe-search-type-container">
		          		<h4>Search Type</h4>
	          			<div class="btn-group" data-toggle="buttons">
					      	<label class="btn btn-default active">
								<input class="timeframe-search-type-radio" type="radio" name="searchType" value="standard" checked> Standard
							</label>
							<label class="btn btn-default">
								<input class="timeframe-search-type-radio" type="radio" name="searchType" value="custom"> Custom
							</label>
					    </div>
	          		</div>
	          	</div>
			</div>
			
			<div class="row">
		        <div class="col-xs-12">
		          	<div class="standard-timeframe-search-form-container">
		          		<div class="btn-radio-container">
					     	<div class="btn-group" data-toggle="buttons">
					     		<label class="btn btn-primary btn-date-toggle">
									<input class="date-radios" type="radio" name="searchType" value="all"> All Time
								</label>
								<label class="btn btn-primary btn-date-toggle">
									<input class="date-radios" type="radio" name="searchType" value="years"> By Year
								</label>
								<label class="btn btn-primary btn-date-toggle">
								    <input class="date-radios" type="radio" name="searchType" value="months"> By Month
								</label>
								<label class="btn btn-primary btn-date-toggle active">
								    <input class="date-radios" type="radio" name="searchType" value="days" checked> By Week
								</label>
								<label class="btn btn-primary btn-date-toggle">
								    <input class="date-radios" type="radio" name="searchType" value="day"> By Day
								</label>
							</div>
						</div>
						<div class="standard-date-picker-container">
							<div class="input-group date">
								<span class="input-group-addon">
	  								<i class="fa fa-calendar"></i>
	  							</span>
	  							<input type="text" class="form-control date-picker-input">
							</div>
						</div>
						<div class="search-button-container">
							<button class="btn btn-primary submit-standard-attendance-form" type="button">Search</button>
						</div>
					</div>
					
					<div class="custom-timeframe-search-form-container hidden">
						<div class="custom-date-picker-container">
							<div class="input-daterange input-group">
							    <input type="text" class="form-control" name="start"/>
							    <span class="input-group-addon">to</span>
							    <input type="text" class="form-control" name="end"/>
							    <span class="input-group-btn">
							    	<button class="btn btn-primary submit-custom-attendance-form" type="button">Search</button>
							    </span>
							</div>
						</div>
					</div>
					
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-12">
		          	<div class="payout-table-container">
  						<table id="attendance-history-table" class="table table-hover table-striped" data-attendance-form-submit="retrieve-attendance-history">
    						<thead>
	    						<tr>
	    							<th class="text-center">Index</th>
	    							<th class="text-center">Player</th>
						            <th class="text-center">Attendance Points</th>
						       	</tr>
						   	</thead>
						   	<tbody><!-- Auto select week by default maybe -->
						   	</tbody>
  						</table>
					</div>
		        </div>
		    </div>
		
		</div><!-- End of container -->
		<%@include file="jsp-fragment/footer.jsp"%>
		
		<!-- Scripts -->
		<%@include file="jsp-fragment/global-scripts.jsp"%>
		<script src="${context}/js/global/bootstrap-datepicker.js" type="text/javascript"></script>
		<script src="${context}/js/attendance.js" type="text/javascript"></script>
	</body>
</html>