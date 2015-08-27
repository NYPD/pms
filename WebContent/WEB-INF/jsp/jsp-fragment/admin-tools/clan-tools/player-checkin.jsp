<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
	<div class="col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
		<div class="panel panel-default">
		  	<div class="panel-heading">
		    	<h3 class="panel-title bold">Manual Player Check-in</h3>
		  	</div>
		  	<div class="panel-body">
		    	<form class="form-horizontal" role="form" data-form-submit-url="incentive-payout" data-success-text="Successfully checked in player">
					<div class="form-group">
						<label for="incentive-playername" class="col-sm-5 col-lg-4 control-label">Player</label>
					    <div class="col-sm-7 col-lg-8">
					    	<select id="incentive-playername" name="accountId" class="form-control">
					    		<c:forEach items="${clanMemberList}" var="clanMember">
									<option value="${clanMember.accountId}">${clanMember.accountName}</option>
								</c:forEach>
							</select>
					    </div>
					</div>
					<div class="form-group">
					    <label for="check-in-date" class="col-sm-5 col-lg-4 control-label">Date</label>
					    <div class="col-sm-7 col-lg-8">
					    	<div class="input-group date">
		  						<input id="check-in-date" type="text" class="form-control alpha alpha-formatted-date">
		  						<span class="input-group-addon">
		  							<i class="fa fa-calendar"></i>
		  						</span>
							</div>
					   	</div>
					</div>
				 	<div class="col-sm-12 col-payout text-center">
				    	<button type="button" class="btn btn-primary btn-player-check-in">Check in</button>
				    </div>
				</form>
		  	</div>
		  	
		</div>
	</div>
</div>

<script>
	$('.input-group.date').datepicker({
	    todayHighlight: true,
	    autoclose: true
	});
</script>