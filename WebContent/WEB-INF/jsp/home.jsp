<!DOCTYPE html>
<html lang="en-us">
	<head>
    	<%@include file="jsp-fragment/standard-head-block.jsp"%>
    	
    	<link href="${context}/css/home.css" rel="stylesheet" type="text/css"/>
  	</head>
  	<body>
  		<div id="header-div">
  			<%@include file="jsp-fragment/top-header.jsp"%>
  			<%@include file="jsp-fragment/topNav.jsp"%>
  		</div>
		<div class="container">
		
			<c:if test="${clanNewsSize != 0}">
			
				<div id="home-news-carousel" class="carousel slide hidden-xs" data-ride="carousel">
					  
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<c:forEach begin="1" end="${clanNewsSize}" varStatus="loop">
						    <li data-target="#home-news-carousel" data-slide-to="${loop.index}" <c:if test="${loop.index == 1}">class="active"</c:if>></li>
						</c:forEach>
					</ol>
					
					<!-- Wrapper for slides -->
					<div class="carousel-inner">
						<c:forEach items="${clanNews}" var="clanNewsEntry" varStatus="status">
							<div class="item<c:if test="${status.count == 1}"> active</c:if>">
						    	<div class="visible-sm-block">
						    		<img src="${clanNewsEntry.smallBannerUrl}" alt="Small Banner" width="720" height="300"/>
						    	</div>
						    	<div class="visible-md-block">
						    		<img src="${clanNewsEntry.mediumBannerUrl}" alt="Medium Banner" width="940" height="300"/>
						    	</div>
						    	<div class="visible-lg-block">
						    		<img src="${clanNewsEntry.largeBannerUrl}" alt="Large Banner" width="1140" height="300"/>
						    	</div>
						    	<div class="carousel-caption">
						       		<p>
						       			<c:if test="${clanNewsEntry.smallScreenOnlyText eq false }">
							       			${clanNewsEntry.text}
						       			</c:if>
		                        	</p>
						    	</div>
						    </div>
						</c:forEach>
					</div>
					
					<!-- Controls -->
					<a class="left carousel-control" href="#home-news-carousel" data-slide="prev">
						<i class="fa fa-arrow-left fa-2x"></i>
					</a>
					<a class="right carousel-control" href="#home-news-carousel" data-slide="next">
						<i class="fa fa-arrow-right fa-2x"></i>
					</a>
				</div><!-- End of home-news-carousel -->
				
				<div id="home-news-non-carousel" class="row visible-xs-block">
					<c:forEach items="${clanNews}" var="clanNewsEntry" varStatus="status">
						<div class="col-xs-12">
							<div class="media media-news">
								<a class="pull-left">
							    	<img class="media-object" src="${clanNewsEntry.iconImageUrl}" alt="Icon News Image" height="64" width="64">
							  	</a>
							  	<div class="media-body">
							    	<h4 class="media-heading">${clanNewsEntry.title}</h4>
							    	${clanNewsEntry.text}
							  	</div>
							</div>
						</div>
					</c:forEach>
				</div><!-- End of home-news-non-carousel -->
			</c:if>
	     	
	     	<div class="row">
	     		<div class="col-md-9 check-in-column">
	     			<h2>Check-in</h2>
	     			<div class="check-in-container">
	          			<button type="button" class="btn btn-primary btn-lg center-block check-in-button inline-block" data-loading-text="Checking In..." data-complete-text="Successfully Checked In">Press Button Recieve Gold</button>
    				</div>
	     		</div>
	     		
	     		<!-- Battle Column -->
	     		<div class="col-md-3 battle-column">
	     			<h2>Battles</h2>
	     			<div class="battles-container">
	     				<c:choose>
						    <c:when test="${empty clanBattles and empty events}">
						    	<div class="battle-info">
						    		<i class="fa fa-frown-o fa-5x"></i>
						    		<h3>No Battles</h3>
						        </div>
						    </c:when>
						    <c:otherwise>
						       <c:forEach items="${clanBattles}" var="clanBattle">
		   							<div class="battle-info">
				          				<div class="map-chip-div">
				          					<h4 class="map-name">${clanBattle.firstArena.nameI18n}</h4>
				          					<span class="chip-count">${clanBattle.personal.chips}</span>
				          				</div>
				          				<img alt="Map picture" src="http://cw1.worldoftanks.com/static/412584/wgcw/arenas/${clanBattle.firstArena.arenaId}.png" height="150" width="150" class="img-thumbnail">
				          				<div class="map-info">
				          					<div class="info-line">
				          						<label>Time:</label>
				          						<span class="battle-time">${clanBattle.time}</span>
				          					</div>
				          					<div class="info-line">
				          						<label>Battle Type:</label>
				          						<span class="battle-type">${clanBattle.formattedBattleType}</span>
				          					</div>
				          				</div>
				         			</div><!-- End of battle-info div -->
								</c:forEach>
								<c:forEach items="${events}" var="event">
		   							<div class="battle-info">
				          				<div class="map-chip-div">
				          					
				          				</div>
				          				<c:choose>
				          					<c:when test="${empty event.imageUrl}">
				          						<img alt="Map picture" src="${clanInfo.emblems.large}" height="150" width="150" class="img-thumbnail">
				          					</c:when>
				          					<c:otherwise>
				          						<img alt="Map picture" src="${event.imageUrl}" height="150" width="150" class="img-thumbnail">
				          					</c:otherwise>
				          				</c:choose>
				          				
				          				<div class="map-info">
				          					<div class="info-line">
				          						<label>Time:</label>
				          						<span class="event-time">${event.time}</span>
				          					</div>
				          					<div class="info-line">
				          						<label>Type:</label>
				          						<span class="battle-type">${event.type}</span>
				          					</div>
				          				</div>
				         			</div><!-- End of battle-info div -->
								</c:forEach>
						    </c:otherwise>
						</c:choose>
          			</div><!-- End of battle-container div -->
	     		</div><!-- End of battle-column div -->
	     		
	     		<div class="col-xs-12 col-md-9 payout-history-column">
	     			<h2>Payout History</h2>
          			<div class="payout-table-container clearfix">
  						<table id="payout-history-table" class="table table-hover table-striped">
    						<thead>
	    						<tr>
	    							<th class="text-center">Index</th>
						            <th class="text-center">Type</th>
						            <th class="text-right">Gold Amount</th>
						            <th class="text-right">Payout Date</th>
						            <th class="text-center">Payed By</th>
						       </tr>
						   </thead>
						   <tbody>
							   <c:forEach items="${payouts}" var="payout">
							   		<tr>
							   			<td class="text-center"></td>
							            <td class="text-center">${payout.type}</td>
							            <td class="payout-amount text-right">${payout.amount}</td>
							            <td class="payout-date text-right">${payout.payoutTime}</td>
							            <td class="text-center">${payout.payerNickname}</td>
							        </tr>
							   </c:forEach>
						   </tbody>
  						</table>
					</div>
					<div class="payout-year-selection-container">
						<div class="input-group highchart-year-selection-container">
							<span class="input-group-addon">Select Year:</span>
							<select class="form-control highchart-year-selection">
								<c:forEach items="${yearsSincePmsCreation}" var="year">
									<option value="${year}">${year}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div id="payout-history-highchart"></div>
	     		</div><!-- End of Payout History Column -->
	     		
	     	</div>
		</div><!-- End of container -->
		
		<div id="check-in-error-modal" class="modal fade">
			<div class="modal-dialog modal-sm">
		    	<div class="modal-content">
		       		<div class="modal-header">
				        <h4 class="modal-title" id="myModalLabel">Check In Error</h4>
				    </div>
				    <div class="modal-body">
				        <p></p>
				    </div>
		      		<div class="modal-footer">
		        		<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
		      		</div>
		    	</div>
	  		</div>
		</div>
    	<%@include file="jsp-fragment/footer.jsp"%>
  	</body>
  	<!-- Scripts -->
  	<%@include file="jsp-fragment/global-scripts.jsp"%>
  	<script src="${context}/js/global/highcharts.js" type="text/javascript"></script>
    <script src="${context}/js/home.js" type="text/javascript"></script>
</html>