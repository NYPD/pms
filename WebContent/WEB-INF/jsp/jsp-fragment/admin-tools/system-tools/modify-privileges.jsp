<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row row-table-add-btn">
	<div class="col-xs-12">
		<h2>User Privileges</h2>
		<a class="btn btn-primary btn-small-modal pull-right" role="button" data-modal-url="get-admin-add-modal">Add Admin</a>
	</div>
</div>

<div class="row">
	<div class="col-xs-12">
		<table id="user-permissions-table" class="table table-hover table-striped table-permissions">
			<thead>
				<tr>
					<th class="text-center">Index</th>
		          	<th class="text-left">Nickname</th>
		          	<th class="text-left">Role</th>
		          	<th class="text-center">Edit</th>
		          	<th class="text-center">Delete</th>
		     	</tr>
		 	</thead>
		 	<tbody>
		  	<c:forEach items="${admins}" var="admin">
		  		<tr>
		  			<td class="text-center"></td>
		           	<td class="text-left">
		           		${admin.nickname}
		           		<input type="hidden" class="admin-account-id" value="${admin.accountId}"/>
		           	</td>
		           	<td class="text-left">${admin.role.roleName}</td>
		           	<td class="text-center"><a class="priviledge-crud priviledge-admin" data-ajax-url="get-admin-edit-modal" href="javascript:void(0)">Edit</a></td>
					<td class="text-center"><a class="priviledge-crud priviledge-admin" data-ajax-url="get-admin-delete-modal" href="javascript:void(0)">Delete</a></td>
		       </tr>
		  	</c:forEach>
		 	</tbody>
		</table>
	</div>
</div>

<div class="row row-table-add-btn row-clan-permissions">
	<div class="col-xs-12">
		<h2>Clan Privileges</h2>
		<a class="btn btn-primary btn-small-modal pull-right" role="button" data-modal-url="get-clan-add-modal">Add Clan</a>
	</div>
</div>

<div class="row">
	<div class="col-xs-12">
		<table id="clan-permissions-table" class="table table-hover table-striped table-permissions">
			<thead>
				<tr>
					<th class="text-center">Index</th>
		          	<th class="text-left">Clan</th>
		          	<th class="text-center">Delete</th>
		     	</tr>
		 	</thead>
		 	<tbody>
		  	<c:forEach items="${allowedClans}" var="allowedClan">
		  		<tr>
		  			<td class="text-center news-banner-id"></td>
		           	<td class="text-left">
		           		${allowedClan.clanName}
		           		<input type="hidden" class="clan-id" value="${allowedClan.clanId}"/>
		           	</td>
		           	<td class="text-center"><a class="priviledge-crud priviledge-clan" data-ajax-url="get-clan-delete-modal" href="javascript:void(0)">Delete</a></td>
		       </tr>
		  	</c:forEach>
		 	</tbody>
		</table>
	</div>
</div>
<script>
	initializePrivlegesDataTable($(".table-permissions"), [ 0, "desc" ], true);
</script>