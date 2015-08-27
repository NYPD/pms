<div class="modal-header">
	<h4 class="modal-title">Delete Admin</h4>
</div>
<div class="modal-body">
	<form role="form" data-form-submit-url="delete-admin" data-success-text="Successfully deleted">
		<input type="hidden" name="accountId" class="numeric numeric-whole" value="${admin.accountId}">
	</form>
	<p>Are you sure you want to delete ${admin.nickname} from the admin list?</p>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	<button type="button" class="btn btn-primary btn-modal-form-submit" data-page-refresh>Delete</button>
</div>