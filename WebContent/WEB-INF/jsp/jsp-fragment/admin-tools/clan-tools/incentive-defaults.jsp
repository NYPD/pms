<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
	<div class="col-xs-12">
		<h2>
			<span>Incentive Payout Amounts</span>
			<span class="pull-right">
				<a id="save-all-table-incentive-amounts" class="btn btn-primary" role="button">Save All</a>
			</span>
		</h2>
	</div>
	<div class="col-xs-12">
		<div class="row">
			<div class="col-xs-offset-4 col-xs-4 col-md-3 col-lg-offset-5 col-lg-2">
				<div class="input-group input-group-tier-filter">
					<select id="tier-select" class="form-control">
						<option value="all">All</option>
						<option value="10" selected>X</option>
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
			        	<a class="btn btn-primary filter-incentive-tiers" role="button">Filter</a>
			      	</span>
		    	</div>
		    </div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12">
		<table id="tank-incentive-amounts-table" class="table table-hover table-striped">
			<thead>
				<tr>
					<th>Tier</th>
            		<th>Tank Name</th>
            		<th>Gold Amount</th>
            		<th>Save</th>
       			</tr>
		   	</thead>
		   	<tbody>
		   		<c:forEach items="${tankIncentiveAmountTableRows}" var="tankIncentiveAmountTableRow">
			   		<tr class="tank-incentive-default-payout" data-tank-incentive-default-payout-id="${tankIncentiveAmountTableRow.tankIncentiveDefaultPayout.tankIncentiveDefaultPayoutId}" data-tank-id="${tankIncentiveAmountTableRow.tankInformation.tankId}">
			   			<td>${tankIncentiveAmountTableRow.tankInformation.level}</td>
			            <td>${tankIncentiveAmountTableRow.tankInformation.nameI18n}</td>
			            <td class="text-right">
			            	<div class="form-group">
			            		<input type="text" class="form-control input-sm input-payout text-right numeric numeric-gold" value="${tankIncentiveAmountTableRow.tankIncentiveDefaultPayout.amount}"/>
			            	</div>
			            </td>
			            <td class="save-incentive-payout-amount-container"><a class="save-incetive-payout-amount">Save</a></td>
			        </tr>
		   		</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<script>

	var tankIncentiveDefaultTable = $('#tank-incentive-amounts-table').DataTable({
		"paging":   true,
		"pagingType": "simple_numbers",
		"order": [ 1, "asc" ],
		"columnDefs": 
			[ 
				{
		      		"targets": [0],
		      		"className": 'text-center'
		    	},
		    	{
		    		"targets": [2],
		      		"className": 'text-right payout-amount',
		      		"type": "html"
		    	}
			],
		 "columns": 
			 [
	             { "data": "tankTier" },
	             { "data": "tankName" },
	             { "data": "goldAmountInput" },
	             { "data": "saveLink" }
		     ]
	});
	
	saveInititalInputValues(tankIncentiveDefaultTable.$('.input-payout'));
</script>