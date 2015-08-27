<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<%@include file="jsp-fragment/standard-head-block.jsp"%>
		<link href="${context}/css/global/datepicker3.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/payout.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<div id="header-div">
			<%@include file="jsp-fragment/top-header.jsp"%>
  			<%@include file="jsp-fragment/topNav.jsp"%>
		</div>
		<div class="container">
			<div class="row row-offcanvas row-offcanvas-right">
		    	<div class="col-xs-10 col-sm-9"><!-- Container For all Forms (Nesting Columns) -->
		    	
		        	<div class="row"><!-- Main Clan Payout Row -->
		        		<div class="col-xs-12">
			        		<div class="panel panel-default">
							  	<div class="panel-heading">
							    	<h3 class="panel-title bold">Clan Payout</h3>
							  	</div>
							  	<div class="panel-body">
							    	<form role="form" data-form-submit-url="${context}/app/payouts/get-clan-payout-groups">
						          		<div class="form-group col-xs-12 col-sm-6 form-group-clan-payout text-center">
										    <h3 class="h3 control-label">Total Unpaid Shares</h3>
										    <h2 class="h2"><span class="label label-default unpaid-shares-span">${unpaidShareCount}</span></h2>
										</div>
							          	<div class="form-group col-xs-12 col-sm-6 form-group-clan-payout text-center">
										    <h3 class="h3 control-label">Amount</h3>
										    <h2 class="h2"><input type="text" name="amount" class="form-control input-lg clan-payout-amount-input numeric numeric-gold" placeholder="Payout amount"></h2>
										</div>
										<div class="col-xs-12 col-payout col-clan-button-container">
						    				<button type="button" class="btn btn-primary btn-payout btn-payout-grp">Payout</button>
						    	 		</div>
							 		</form>
							  	</div>
							</div><!-- End of Panel -->
						</div>
				 	</div>
				 	<div class="row">
		        		<div class="col-xs-12 col-sm-12 col-md-6">
		        			<div class="panel panel-default panel-tank-incentive panel-loading">
							  	<div class="panel-heading">
							    	<h3 class="panel-title bold">Tank Incentive</h3>
							  	</div>
							  	<div class="row row-loading">
						  			<div class="loading-ball bounce1"></div>
									<div class="loading-ball bounce2"></div>
									<div class="loading-ball bounce3"></div>
						  		</div>
							  	<div class="panel-body">
							  		<div class="row form-group">
						  				<div class="col-xs-6 text-right">
						  					<div class="checkbox checkbox-use-preset-values">
											    <label>
											      	<input type="checkbox" checked>
											      	Use Preset Values
											    </label>
											</div>
						  				</div>
						  				<div class="col-xs-6">
						  					<div class="input-group input-group-sm input-group-tier-filter">
												<select class="form-control" disabled>
													<option value="10">X</option>
													<option value="9">IX</option>
													<option value="8">VIII</option>
													<option value="7">VII</option>
													<option value="6">VI</option>
													<option value="5">V</option>
													<option value="4">IV</option>
													<option value="3">III</option>
													<option value="2">II</option>
													<option value="1">I</option>
												</select>
										      	<span class="input-group-btn">
										        	<a class="btn btn-primary btn-filter-incentive-tiers" role="button" disabled="disabled">Filter</a>
										      	</span>
									    	</div>
						  				</div>
									</div>
							    	<form class="form-horizontal clear" role="form" data-form-submit-url="${context}/app/payouts/incentive-payout">
										<div class="form-group">
										    <label for="incentive-tank" class="col-sm-5 col-lg-3 control-label">Tank</label>
										    <div class="col-sm-7 col-lg-9">
										    	<select id="incentive-tank" name="tankUnlockedId" class="form-control" data-is-preset-values="true">
										    		<c:forEach items="${clanTankIncentiveAmountTableRows}" var="clanTankIncentiveAmountTableRow">
														<option value="${clanTankIncentiveAmountTableRow.tankInformation.tankId}" data-tank-incentive-amount="${clanTankIncentiveAmountTableRow.tankIncentiveDefaultPayout.amount}">${clanTankIncentiveAmountTableRow.tankInformation.nameI18n}</option>
													</c:forEach>
												</select>
										   	</div>
										</div>
										<div class="form-group">
											<label for="incentive-playername" class="col-sm-5 col-lg-3 control-label">Player</label>
										    <div class="col-sm-7 col-lg-9">
										    	<select id="incentive-playername" name="accountId" class="form-control">
										    		<c:forEach items="${clanMemberList}" var="clanMember">
														<option value="${clanMember.accountId}">${clanMember.accountName}</option>
													</c:forEach>
												</select>
										    </div>
										</div>
										
										<div class="form-group">
										    <label for="incentive-amount" class="col-sm-5 col-lg-3 control-label">Amount</label>
										    <div class="col-sm-7 col-lg-9">
										    	<input id="incentive-amount" name="amount" type="text" class="form-control numeric numeric-gold" placeholder="Payout amount">
										   	</div>
										</div>
									 	<div class="col-xs-12 col-payout">
									    	<button type="button" class="btn btn-primary btn-payout btn-payout-standard">Payout</button>
									    </div>
									</form>
							  	</div>
							</div>
		        		</div>
		        		<div class="col-xs-12 col-sm-12 col-md-6">
			        		<div class="panel panel-default">
							  	<div class="panel-heading">
							    	<h3 class="panel-title bold">Caller Bonus</h3>
							  	</div>
							  	<div class="panel-body">
							    	<form class="form-horizontal" role="form" data-form-submit-url="${context}/app/payouts/get-caller-bonus-payout-groups">
						          		<div class="form-group form-group-caller-bonus triple-form-group-height text-center">
							          		<div class="col-xs-6">
							          			<label>Unpaid Battles</label>
							          			<div class="caller-bonus-badge-container">
							          				<span id="caller-bonus-unpaid-battles" class="badge badge-success">${unpaidCallerBattles}</span>
							          			</div>
							          		</div>
							          		<div class="col-xs-6">
								          		<label>Total Amount</label>
								          		<div class="caller-bonus-badge-container">
								          			<span id="caller-bonus-total-amount" class="badge badge-primary">0</span>
								          		</div>
							          		</div>
						          		</div>
						          		<div class="form-group">
							          		<label for="caller-bonus-battle-amount" class="col-sm-7 col-lg-4 control-label">Battle Amount</label>
										    <div class="col-sm-5 col-lg-8">
										    	<input type="text" id="caller-bonus-battle-amount" name="amount" class="form-control numeric numeric-gold" placeholder="Per Battle Amount" maxlength="7" value="${callerAmountPerBattle}">
										   	</div>
						          		</div>
									 	<div class="col-sm-12 col-payout">
									    	<button type="button" class="btn btn-primary btn-payout btn-payout-grp">Payout</button>
									    </div>
									</form>
							  	</div>
							</div>
		        		</div>
		        	</div>
		        	<div class="row">
				        <div class="col-xs-12 col-sm-6">
				        	<div class="panel panel-default">
							  	<div class="panel-heading">
							    	<h3 class="panel-title bold">Player Bonus</h3>
							  	</div>
							  	<div class="panel-body">
							    	<form class="form-horizontal" role="form" data-form-submit-url="${context}/app/payouts/player-bonus-payout">
										<div class="form-group">
											<label for="player-bonus-playername" class="col-sm-5 col-lg-4 control-label">Player</label>
										    <div class="col-sm-7 col-lg-8">
										    	<select id="player-bonus-playername" name="accountId" class="form-control numeric numeric-whole">
													<c:forEach items="${clanMemberList}" var="clanMember">
														<option value="${clanMember.accountId}">${clanMember.accountName}</option>
													</c:forEach>
												</select>
										    </div>
										</div>
										<div class="form-group">
										    <label for="player-bonus-reason" class="col-sm-5 col-lg-4 control-label">Reason</label>
										    <div class="col-sm-7 col-lg-8">
										    	<input type="text" id="player-bonus-reason" name="reason" class="form-control alpha alpha-numeric" placeholder="Reason">
										   	</div>
										</div>
										<div class="form-group single-form-group-height hidden-xs"></div>
										<div class="form-group">
										    <label for="player-bonus-amount" class="col-sm-5 col-lg-4 control-label">Amount</label>
										    <div class="col-sm-7 col-lg-8">
										    	<input type="text" id="player-bonus-amount" name="amount" class="form-control numeric numeric-gold" placeholder="Payout amount">
										   	</div>
										</div>
									 	<div class="col-sm-12 col-payout">
									    	<button type="button" class="btn btn-primary btn-payout btn-payout-standard">Payout</button>
									    </div>
									</form>
							  	</div>
							</div>
				        </div>
				        <div class="col-xs-12 col-sm-6">
				        	<div class="panel panel-default">
							  	<div class="panel-heading">
							    	<h3 class="panel-title bold">Campaigninging</h3>
							  	</div>
							  	<div class="panel-body">
							    	<form class="form-horizontal" role="form" data-form-submit-url="${context}/app/payouts/get-campaign-event-payout-groups">
						          		<div class="form-group">
										    <label for="campaign-event-name" class="col-sm-5 col-lg-4 control-label">Event Name</label>
										    <div class="col-sm-7 col-lg-8">
										    	<input type="text" id="campaign-event-name" name="campaignName" class="form-control alpha alpha-numeric" placeholder="Event Name">
										   	</div>
										</div>
						          		<div class="input-daterange">
											<div class="form-group">
												<label for="campaign-start-date" class="col-sm-5 col-lg-4 control-label">Start Date</label>
												<div class="col-sm-7 col-lg-8">
											    	<input type="text" id="campaign-start-date" class="form-control" placeholder="Start Date"/>
											    	<input type="hidden" id="hidden-campaign-start-date" class="numeric numeric-date" name="timeFrame.startTime" value="x">
											    </div>
											</div>
											<div class="form-group">
											    <label for="campaign-end-date" class="col-sm-5 col-lg-4 control-label">End Date</label>
											    <div class="col-sm-7 col-lg-8">
											    	<input type="text" id="campaign-end-date" class="form-control" placeholder="End Date">
											    	<input type="hidden" id="hidden-campaign-end-date" class="numeric numeric-date" name="timeFrame.endTime" value="x">
											   	</div>
											 </div>
										 </div>
										 <div class="form-group">
										    <label for="campaigning-amount" class="col-sm-5 col-lg-4 control-label">Amount</label>
										    <div class="col-sm-7 col-lg-8">
										    	<input type="text" id="campaigning-amount" name="amount" class="form-control numeric numeric-gold" placeholder="Payout amount">
										   	</div>
									 	</div>
										<div class="col-sm-12 col-payout">
											<button type="button" class="btn btn-primary btn-payout btn-payout-grp">Payout</button>
										</div>
									</form>
							  	</div>
							</div>
				        </div>
		   			</div>
		  		</div>
		  		
		  		<div class="col-xs-6 col-sm-3 col-clan-info" data-spy="affix" data-offset-top="1" data-offset-bottom="1050">
		  			
		  			<div class="row">
		  			
		  				<div class="col-xs-3 visible-xs-block col-offcanvas-btn-container"><!-- Off Canvas Button -->
		  					<a class="btn btn-primary btn-lg btn-offcanvas" role="button">
			  					<i class="fa fa-arrow-right fa-arrow-left fa-offcanvas-arrow fa-2"></i>
			  				</a>
		  				</div>
		  				
		  				<div class="col-xs-9 col-sm-12">
	  						<h2>Clan Info</h2>
				        	<div class="clan-info-container text-center">
				        		<h4 class="control-label">Gold in Treasury</h4>
					        	<p>
					        		<i id="reload-user-info" class="pmi pmi-gold-pile"></i>
									<span id="treasury-amount"><strong><fmt:formatNumber type="number" maxFractionDigits="0" value="${clanInfo.personal.treasury}"/></strong></span>
					        	</p>
					        	<h4 class="control-label">Total Provinces</h4>
					        	<p>
					        		<i id="reload-user-info" class="pmi pmi-provinces"></i>
									<span id="province-amount" class="vertical-align-super"><strong>${clanInfo.totalProvincesOwned}</strong></span>
					        	</p>
					        	<h4 class="control-label">Gold Per Day</h4>
					        	<p>
					        		<i id="reload-user-info" class="pmi pmi-gold-stack"></i>
									<span id="gold-per-day-amount"><strong><fmt:formatNumber type="number" maxFractionDigits="0" value="${clanInfo.totalGoldPerDay}"/></strong></span>
					        	</p>
					        	<h4 class="control-label">Gold Per Hour</h4>
					        	<p>
					        		<i id="reload-user-info" class="pmi pmi-gold-stack"></i>
									<span id="gold-per-hour-amount"><strong><fmt:formatNumber type="number" maxFractionDigits="0" value="${clanInfo.totalGoldPerHour}"/></strong></span>
					        	</p>
					        	<h4 class="control-label">Member Count</h4>
					        	<p>
					        		<i id="reload-user-info" class="fa fa-user"></i>
									<span id="gold-per-hour-amount"><strong>${clanInfo.membersCount}</strong></span>
					        	</p>
							</div>	
		  				</div>
		  			</div>
		        </div>
		    </div>
		    
		    <div class="row">
		        <div class="col-xs-12">
		        	<h2>Transaction History</h2>
		          	<div class="payout-table-container clearfix">
  						<table id="transaction-history-table" class="table table-hover table-striped">
    						<thead>
	    						<tr>
	    							<th class="text-center">Index</th>
	    							<th class="text-center">Transaction ID</th>
						            <th>Type</th>
						            <th class="text-right">Gold Amount</th>
						            <th class="text-center">Transaction Time</th>
						            <th>By</th>
						       </tr>
						   </thead>
						   <tbody>
							   <c:forEach items="${transactionHistoryList}" var="transactionHistory">
							   		<tr>
							   			<td class="text-center"></td>
							   			<td class="text-center">${transactionHistory.transactionId}</td>
							   			<td>${transactionHistory.type}</td>
							            <td class="text-right">${transactionHistory.amount}</td>
							            <td class="text-center transaction-payout-time">${transactionHistory.payoutTime}</td>
							            <td>${transactionHistory.payerNickname}</td>
							        </tr>
							   </c:forEach>
						   </tbody>
  						</table>
					</div>
					<div id="transaction-history-highchart"></div>
		        </div>
		    </div><!-- End of second row -->
		
		</div><!-- End of container -->
		<%@include file="jsp-fragment/footer.jsp"%>
		<%@include file="jsp-fragment/payout-modals.jsp"%>
		
		<!-- Scripts -->
		<%@include file="jsp-fragment/global-scripts.jsp"%>
		<script src="${context}/js/global/highcharts.js" type="text/javascript"></script>
		<script src="${context}/js/global/bootstrap-datepicker.js" type="text/javascript"></script>
		<script src="${context}/js/payouts.js" type="text/javascript"></script>
	</body>
</html>