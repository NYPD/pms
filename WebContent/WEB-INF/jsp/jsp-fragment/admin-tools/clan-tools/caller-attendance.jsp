<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<link href="${context}/css/global/table-timeframe-search.css" rel="stylesheet" type="text/css"/>

<div class="row">
	<div class="col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
		<div class="panel panel-default">
		  	<div class="panel-heading">
		    	<h3 class="panel-title bold">Caller Check-in</h3>
		  	</div>
		  	<div class="panel-body">
		    	<form class="form-horizontal" role="form" data-form-submit-url="caller-check-in" data-success-text="Successfully checked in">
					<div class="form-group">
						<label for="caller-playername" class="col-sm-4 col-lg-3 control-label">Player</label>
					    <div class="col-sm-8 col-lg-9">
					    	<select id="caller-playername" name="accountId" class="form-control">
					    		<c:forEach items="${clanMemberList}" var="clanMember">
									<option value="${clanMember.accountId}">${clanMember.accountName}</option>
								</c:forEach>
							</select>
					    </div>
					</div>
					<div class="form-group">
					    <label for="caller-map" class="col-sm-4 col-lg-3 control-label">Map</label>
					    <div class="col-sm-8 col-lg-9">
					    	<select id="caller-map" name="mapName" class="form-control">
					    		<c:forEach items="${mapDetails}" var="mapDetail">
									<option value="${mapDetail.nameI18n}">${mapDetail.nameI18n}</option>
								</c:forEach>
							</select>
					   	</div>
					 </div>
				 	<div class="col-sm-12 col-payout text-center">
				    	<button type="button" class="btn btn-primary btn-tool-form-submit">Check In</button>
				    </div>
				</form>
		  	</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12">
		<h2>Caller Attendance History</h2>
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
				<button id="attendance-timeframe-search" class="btn btn-primary" type="button">Search</button>
			</div>
		</div>
		<table id="caller-attendance-table" class="table table-hover table-striped" data-attendance-form-submit="retrieve-caller-attendance-history">
			<thead>
				<tr>
					<th class="text-center">Index</th>
					<th class="text-center">Caller</th>
	           		<th class="text-center">Battles Called</th>
	      		</tr>
			</thead>
			<tbody>
			</tbody>
	  	</table>
  	</div>
</div>

<script>
	initializeDatepicker($('.standard-date-picker-container > .input-group.date'));
</script>

<div class="row">
	<div class="col-xs-12">
		<h2>Caller Map Frequency</h2>
		<table id="caller-map-table" class="table table-hover table-striped">
			<thead>
				<tr>
					<th class="text-center">Caller</th>
					<th class="text-center">Map</th>
	           		<th class="text-center">Times Called</th>
	      		</tr>
			</thead>
			<tbody>
				<c:forEach items="${callerMapFrequencies}" var="callerMapFrequency">
					<tr>
						<td class="text-center">${callerMapFrequency.nickname}</td>
						<td class="text-center">${callerMapFrequency.mapName}</td>
						<td class="text-center">${callerMapFrequency.timesCalled}</td>
					</tr>
				</c:forEach>
			</tbody>
	  	</table>
  	</div>
</div>
<script>
	initializeCallerAttendanceDataTable($("#caller-attendance-table"), [ 2, "desc" ], false, true);
	initializeCallerAttendanceDataTable($("#caller-map-table"), [ 1, "asc" ], true, false);
</script>