<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="isAddCrud" value="${carouselNews eq null || carouselNews.id == 0}" />
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<c:choose>
	    <c:when test="${isAddCrud}">
	       <h4 class="modal-title">Add News Carousel Banner</h4>
	    </c:when>
	    <c:otherwise>
	        <h4 class="modal-title">Edit News Carousel Banner</h4>
	    </c:otherwise>
	</c:choose>
</div>
<div class="modal-body">
	<div class="container-fluid">
		<c:choose>
		    <c:when test="${isAddCrud}">
		       <form role="form" data-form-submit-url="put-news-carousel" data-success-text="Successfully added news">
		    </c:when>
		    <c:otherwise>
		        <form role="form" data-form-submit-url="put-news-carousel" data-success-text="Successfully edited news">
		    </c:otherwise>
		</c:choose>
	   		<div class="row">
			 	<div class="col-sm-6">
			 		<div class="form-group">
				    	<label for="large-banner-url">Large Banner Url</label>
				    	<input type="text" id="large-banner-url" class="form-control alpha alpha-link optional" name="largeBannerUrl" placeholder="1140 x 300px" value="${carouselNews.largeBannerUrl}">
				    	<c:choose>
						    <c:when test="${carouselNews == null}">
						       <input type="hidden" name="newsCarouselId" class="numeric numeric-whole"  value="0"/>
						    </c:when>
						    <c:otherwise>
						        <input type="hidden" name="newsCarouselId" class="numeric numeric-whole"  value="${carouselNews.newsCarouselId}"/>
						    </c:otherwise>
						</c:choose>
				  	</div>
				  	<div class="form-group">
				    	<label for="medium-banner-url">Medium Banner Url</label>
				    	<input type="text" id="medium-banner-url" class="form-control alpha alpha-link optional" name="mediumBannerUrl" placeholder="940 x 300px" value="${carouselNews.mediumBannerUrl}">
				  	</div>
				  	<div class="form-group">
				    	<label for="small-banner-url">Small Banner Url</label>
				    	<input type="text" id="small-banner-url" class="form-control alpha alpha-link optional" name="smallBannerUrl" placeholder="720 x 300px" value="${carouselNews.smallBannerUrl}">
				  	</div>
				  	<div class="form-group">
				    	<label for="icon-image-url">Icon Image Url</label>
				    	<input type="text" id="icon-image-url" class="form-control alpha alpha-link optional" name="iconImageUrl" placeholder="64 x 64px" value="${carouselNews.iconImageUrl}">
				  	</div>
			 	</div>
			  	<div class="col-sm-6">
			  		<div class="form-group">
				    	<label for="news-title">Title</label>
				    	<input type="text" id="news-title" class="form-control alpha alpha-numeric" name="title" placeholder="News Title" value="${carouselNews.title}" maxlength="45">
				  	</div>
				  	<div class="form-group">
				    	<label for="news-text">Text</label>
				    	<div class="only-small-screen-news-option inline-block">
					    	<label>Only In Small Screens</label>
					    	<label class="radio-inline">
						  		<input type="radio" name="smallScreenOnlyText" value="1" <c:if test="${carouselNews.smallScreenOnlyText == true}">checked</c:if>> Yes
							</label>
							<label class="radio-inline">
							  	<input type="radio" name="smallScreenOnlyText" value="0" <c:if test="${carouselNews.smallScreenOnlyText == false}">checked</c:if>> No
							</label>
						</div>
				    	<textarea id="news-text" class="form-control modal-textarea alpha alpha-numeric optional" name="text" rows="5" maxlength="200">${carouselNews.text}</textarea>
				  	</div>
			  	</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-md-4">
			  		<div class="form-group">
				    	<label for="banner-background-color">Banner Background Color</label>
				    	<div class="input-group input-color-picker">
				    		<input type="text" id="banner-background-color" class="form-control alpha alpha-hex-color optional" name="bannerBackgroundColor" placeholder="#hex code" value="${carouselNews.bannerBackgroundColor}">
				    		<span class="input-group-addon"><i></i></span>
				    	</div>
				  	</div>
				  	
			  	</div>
			  	<div class="col-sm-6 col-md-4">
			  		<div class="form-group">
				    	<label for="news-title">Banner Text Color</label>
				    	<div class="input-group input-color-picker">
				    		<input type="text" id="banner-text-color" class="form-control alpha alpha-hex-color optional" name="bannerTextColor" placeholder="#hex code" value="${carouselNews.bannerTextColor}">
				    		<span class="input-group-addon"><i></i></span>
				    	</div>
				  	</div>
				  	
			  	</div>
			  	<div class="col-sm-6 col-md-4">
			  		<c:if test="${user.role.roleId == 3}">
				  		<label class="news-carousel-radio-label">Global News Post</label>
				  		<label class="radio-inline">
				  			<input type="radio" name="global" value="1" <c:if test="${carouselNews.global == true}">checked</c:if>> Yes
						</label>
						<label class="radio-inline">
						  	<input type="radio" name="global" value="0" <c:if test="${carouselNews.global == false}">checked</c:if>> No
						</label>
			  		</c:if>
					<label class="news-carousel-radio-label">Active News Post</label>
			  		<label class="radio-inline">
					  	<input type="radio" id="inlineRadio1" name="active" value="1" <c:if test="${carouselNews.active == true}">checked</c:if>> Yes
					</label>
					<label class="radio-inline">
					  	<input type="radio" id="inlineRadio1" name="active" value="0" <c:if test="${carouselNews.active == false}">checked</c:if>> No
					</label>
			  	</div>
			  	
			</div>
	   	</form>
   	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	<c:choose>
	    <c:when test="${isAddCrud}">
	       <button type="button" class="btn btn-primary btn-modal-form-submit" data-page-refresh>Add</button>
	    </c:when>
	    <c:otherwise>
	        <button type="button" class="btn btn-primary btn-modal-form-submit" data-page-refresh>Edit</button>
	    </c:otherwise>
	</c:choose>
</div>
<script>
	var $inputColorPickers = $(".input-color-picker");
	$inputColorPickers.colorpicker();
</script>