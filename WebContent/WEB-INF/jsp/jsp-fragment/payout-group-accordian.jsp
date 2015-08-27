<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h4>Total Gold after mathamagics and dividing by zero: ${totalAfterCalculations}</h4>
<div class="panel-group" id="accordion">
	<c:forEach items="${payoutGroups}" var="payoutGroup" varStatus="status">
		<c:if test="${payoutGroup.avgAmount > 0}">
			<div class="panel panel-primary">
			    <div class="panel-heading">
			    	<h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" href="#collapse${status.index}">Payout Group #${status.index + 1}: ${payoutGroup.avgAmount} gold</a>
			      	</h4>
			    </div>
			    <div id="collapse${status.index}" class="panel-collapse collapse ${status.index == 0 ? 'in' : ''}">
			      	<div class="panel-body">
			      		<ul class="list-inline">
			      			<c:forEach items="${payoutGroup.playerPayoutSummaries}" var="playerPayoutSummary">
			      				<li><h4 class="player-nickname">${playerPayoutSummary.nickname}</h4></li>
			      			</c:forEach>
						</ul>
						<div class="form-group">
						 	<div class="col-xs-12">
						 		<div class="pull-right">
							 		<span class="script-tooltip" data-toggle="tooltip" data-placement="left" title="Script requires Greasemonkey (or equivelent) to be installed on the browser">
							    		<button type="button" class="btn btn-warning btn-script-generation" data-gold-amount="${payoutGroup.avgAmount}">Generate/Install Script</button>
							    	</span>
							    	<a href="http://worldoftanks.com/community/clans/${memberInfo.clan.clanId}" class="btn btn-default" role="button" target="_blank">Clan Page</a>
						    	</div>
						    </div>
						 </div>
			      	</div>
			    </div>
			</div>
		</c:if>
	</c:forEach>
</div>
<form id="new-calculated-payout-group-total-form" data-form-submit-url="${finalizeUrl}">
	<input type="hidden" name="amount" value="${payoutAmount}">
</form>
<script>
	$(".script-tooltip").tooltip();
</script>