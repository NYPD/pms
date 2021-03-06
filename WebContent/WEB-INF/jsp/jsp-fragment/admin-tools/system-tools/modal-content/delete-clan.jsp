<div class="modal-header">
	<h4 class="modal-title">Delete Clan</h4>
</div>
<div class="modal-body">
	<form role="form" data-form-submit-url="delete-clan" data-success-text="Successfully deleted">
		<input type="hidden" name="clanId" class="numeric numeric-whole" value="${allowedClan.clanId}">
	</form>
	<p>Are you sure you want to delete ${allowedClan.clanName} from PMS?</p>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	<button type="button" class="btn btn-primary btn-modal-form-submit" data-page-refresh>Delete</button>
</div>