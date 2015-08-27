<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="isAddCrud" value="${admin == null}" />
<div class="modal-header">
	<c:choose>
 		<c:when test="${isAddCrud}">
	       <h4 class="modal-title">Add Admin</h4>
	    </c:when>
	    <c:otherwise>
	        <h4 class="modal-title">Edit Admin</h4>
	    </c:otherwise>
	</c:choose>
</div>
<div class="modal-body">
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12">
				<c:choose>
				    <c:when test="${isAddCrud}">
				       <form class="form-horizontal" role="form" data-form-submit-url="add-admin" data-success-text="Admin added">
				    </c:when>
				    <c:otherwise>
				        <form class="form-horizontal" role="form" data-form-submit-url="edit-admin" data-success-text="Admin edited">
				    </c:otherwise>
				</c:choose>
					<div class="form-group">
						<label for="user-account-id" class="col-sm-3 control-label">User</label>
					    <div class="col-sm-8">
					    <c:choose>
					 		<c:when test="${isAddCrud}">
						    	<input type="text" id="user-account-id" class="form-control numeric numeric-whole" name="accountId" placeholder="User Account ID" maxlength="10">
						    </c:when>
						    <c:otherwise>
						        <input type="hidden" name="accountId" class="form-control numeric numeric-whole" value="${admin.accountId}">
						        <h5>${admin.nickname}</h5>
						    </c:otherwise>
						</c:choose>
						</div>
					</div>
					<div class="form-group">
					    <label for="caller-map" class="col-sm-3 control-label">Role</label>
					    <div class="col-sm-8">
					    	<select id="caller-map" name="role.roleId" class="form-control">
					    		<c:forEach items="${roles}" var="role">
									<option value="${role.roleId}">${role.roleName}</option>
								</c:forEach>
							</select>
					   	</div>
					 </div>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<c:choose>
	    <c:when test="${isAddCrud}">
	       <button type="button" class="btn btn-primary btn-modal-form-submit" data-page-refresh>Add</button>
	    </c:when>
	    <c:otherwise>
	        <button type="button" class="btn btn-primary btn-modal-form-submit" data-page-refresh>Edit</button>
	    </c:otherwise>
	</c:choose>
</div>
