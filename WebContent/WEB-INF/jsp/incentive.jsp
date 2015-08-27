<!DOCTYPE html>
<html lang="en-us">
	<head>
		<%@include file="jsp-fragment/standard-head-block.jsp"%>
		
		<link href="${context}/css/incentive.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<div id="header-div">
			<%@include file="jsp-fragment/top-header.jsp"%>
  			<%@include file="jsp-fragment/topNav.jsp"%>
		</div>
		<div class="container">
		
			<div class="row">
			
				<div class="col-md-6">
					<h2>Top 5 Tryhards</h2>
					<div id="top-incentive-players-highcharts-container"></div>
				</div>
				
				<div class="col-md-6">
					<h2>Top 5 Meta Tanks</h2>
					<div id="top-incentive-tanks-highcharts-container"></div>
				</div>
			</div>
			
			<div class="row">
		        <div class="col-xs-12">
		          	<h2>Incentive Summary History</h2>
 					<table id="incentive-summary-table" class="table table-hover table-striped">
   						<thead>
    						<tr>
    							<th class="text-center">Tank Tier</th>
    							<th>Tank Name</th>
    							<th class="text-right">Total Gold Paid</th>
					            <th class="text-left">Players</th>
					       	</tr>
					   </thead>
					   <tbody>
					   		<c:forEach items="${incentiveSummaryTableRows}" var="incentiveSummaryTableRow">
					   			<tr>
					   				<td class="text-center">${incentiveSummaryTableRow.tankInformation.level}</td>
						   	 		<td>
						   	 			<img class="tank-icon" alt="tank icon" src="${incentiveSummaryTableRow.tankInformation.imageSmall}">
						   	 			<span class="tank-name">${incentiveSummaryTableRow.incentiveTankDetail.tankUnlockedName}</span>
						   	 		</td>
						   	 		<td class="text-right"> ${incentiveSummaryTableRow.incentiveTankDetail.totalGoldGiven}</td>
						   	 		<td class="text-left">
							   	 		<c:forEach items="${incentiveSummaryTableRow.incentiveTankDetail.tankUnlockerNicknames}" var="tankUnlockerNickname">
							   	 			<span class="tank-unlocker">${tankUnlockerNickname}</span>
							   	 		</c:forEach>
						   	 		</td>
					   			</tr>
					   		</c:forEach>
					   </tbody>
 					</table>
		        </div>
		    </div>
		    
		    <div class="row">
		    	<div class="col-xs-12">
		    		<h2>Incentive Payout Amounts</h2>
		    		<table id="incentive-payout-amounts-table" class="table table-hover table-striped">
   						<thead>
    						<tr>
    							<th class="text-center">Tank Tier</th>
    							<th>Tank Name</th>
    							<th class="text-right">Gold Amount</th>
					       	</tr>
					   </thead>
					   <tbody>
					   		<c:forEach items="${clanTankIncentiveAmountTableRows}" var="clanTankIncentiveAmountTableRow">
					   			<tr>
						   	 		<td class="text-center">${clanTankIncentiveAmountTableRow.tankInformation.level}</td>
						   	 		<td>
						   	 			<img class="tank-icon" alt="tank icon" src="${clanTankIncentiveAmountTableRow.tankInformation.imageSmall}">
						   	 			<span class="tank-name">${clanTankIncentiveAmountTableRow.tankInformation.nameI18n}</span>
						   	 		</td>
						   	 		<td class="text-right">${clanTankIncentiveAmountTableRow.tankIncentiveDefaultPayout.amount}</td>
					   			</tr>
					   		</c:forEach>
					   </tbody>
 					</table>
		    	</div>
		    </div>
		    
		</div><!-- End of container -->
		<%@include file="jsp-fragment/footer.jsp"%>
		
		<!-- Scripts -->
		<%@include file="jsp-fragment/global-scripts.jsp"%>
		<script src="${context}/js/global/highcharts.js" type="text/javascript"></script>
		<script src="${context}/js/incentive.js" type="text/javascript"></script><!-- Change this to min file -->
		
	</body>
</html>