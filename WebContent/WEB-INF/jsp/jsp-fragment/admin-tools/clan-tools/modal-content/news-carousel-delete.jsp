<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<h4 class="modal-title">Delete News Carousel Banner</h4>
</div>
<div class="modal-body">
	<form role="form" data-form-submit-url="delete-news-carousel" data-success-text="Successfully deleted">
		<input type="hidden" name="newsCarouselId" class="numeric numeric-whole" value="${newsCarouselId}">
	</form>
	<p>Are you sure you want to delete carousel news #${newsCarouselId}?</p>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	<button type="button" class="btn btn-primary btn-modal-form-submit" data-page-refresh>Delete</button>
</div>