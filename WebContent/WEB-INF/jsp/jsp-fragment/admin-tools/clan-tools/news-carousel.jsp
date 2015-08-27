<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row row-table-add-btn">
	<div class="col-xs-12">
		<h2>
			<span class="pull-left">
				All News Banners
			</span>
			<span class="pull-right">
				<a id="btn-add-news-carousel" class="btn btn-primary btn-large-modal" role="button" data-modal-url="get-news-carousel-add-modal">
					<span class="hidden-xs">Add Carousel Banner</span>
					<span class="visible-xs-block">Add Banner</span>
				</a>
			</span>
		</h2>
	</div>
</div>

<div class="row">
	<div class="col-xs-12">
		<table id="news-carousel-table" class="table table-hover table-striped">
			<thead>
				<tr>
					<th class="text-center">ID</th>
		          	<th class="text-left">News Title</th>
		          	<th class="text-center">Global</th>
		          	<th class="text-center">Active</th>
		          	<th class="text-center">Created By</th>
		          	<th class="text-center">Last Modified By</th>
		          	<th class="text-center">Edit</th>
		          	<th class="text-center">Copy</th>
		          	<th class="text-center">Delete</th>
		     	</tr>
		 	</thead>
		 	<tbody>
		  	<c:forEach items="${clanNews}" var="newsBanner">
		  		<tr>
		  			<td class="text-center news-banner-id">${newsBanner.id}</td>
		           	<td class="text-left">${newsBanner.title}</td>
		           	<td class="text-center">${newsBanner.globalAsString}</td>
		           	<td class="text-center">${newsBanner.activeAsString}</td>
		           	<td class="text-center">${newsBanner.createdByNickname}</td>
		           	<td class="text-center">${newsBanner.modifiedByNickname}</td>
		          	<c:choose>
					    <c:when test="${user.role.roleId != 3 && newsBanner.global == true}">
					       <td class="text-center">Edit</td>
					       <td class="text-center">Copy</td>
					       <td class="text-center">Delete</td>
					    </c:when>
					    <c:otherwise>
					        <td class="text-center"><a class="news-crud edit-clan-news" data-ajax-url="get-news-carousel-edit-modal" href="javascript:void(0)">Edit</a></td>
					        <td class="text-center"><a class="news-crud copy-clan-news" data-ajax-url="get-news-carousel-copy-modal" href="javascript:void(0)">Copy</a></td>
					        <td class="text-center"><a class="news-crud delete-clan-news" data-ajax-url="get-news-carousel-delete-modal" href="javascript:void(0)">Delete</a></td>
					    </c:otherwise>
					</c:choose>
		       </tr>
		  	</c:forEach>
		 	</tbody>
		</table>
	</div>
</div>
<script>
	initializeAdminToolDataTable($("#news-carousel-table"), [ 3, "desc" ], true);
</script>