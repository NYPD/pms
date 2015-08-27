<div class="modal-header">
	<h4 class="modal-title">Add Clan</h4>
</div>
<div class="modal-body">
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12">
				<form class="form-horizontal" role="form" data-form-submit-url="add-clan" data-success-text="Clan added">
					<div class="form-group">
						<label for="allowed-clan-id" class="col-sm-4 control-label">Clan</label>
						<div class="col-sm-8">
							<input type="text" id="allowed-clan-id" class="form-control numeric numeric-whole" name="clanId" placeholder="Clan ID" maxlength="10">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    <button type="button" class="btn btn-primary btn-modal-form-submit" data-page-refresh>Add</button>
</div>