<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<link href="${context}/css/global/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css"/>

<div class="row row-table-add-btn">
	<div class="col-xs-12">
		<h2>
			<span class="pull-left">
				Upcoming Events
			</span>
			<span class="pull-right">
				<a class="btn btn-primary btn-large-modal" role="button" data-modal-url="get-upcoming-event-add-modal">Add Event</a>
			</span>
		</h2>
	</div>
</div>

<div class="row">
	<div class="col-xs-12">
		<table id="upcoming-events-table" class="table table-hover table-striped">
			<thead>
				<tr>
					<th class="text-center">Event ID</th>
		          	<th class="text-left">Event Type</th>
		          	<th class="text-center">Event Time</th>
		          	<th class="text-center">Edit</th>
		          	<th class="text-center">Delete</th>
		     	</tr>
		 	</thead>
		 	<tbody>
		  	<c:forEach items="${upcomingEvents}" var="event">
		  		<tr>
		  			<td class="text-center event-id">${event.id}</td>
		           	<td class="text-left">${event.type}</td>
		           	<td class="text-center event-time">${event.time}</td>
		            <td class="text-center"><a class="event-crud edit-event hand-cursor" data-ajax-url="get-upcoming-event-edit-modal">Edit</a></td>
					<td class="text-center"><a class="event-crud delete-event hand-cursor" data-ajax-url="get-upcoming-event-delete-modal">Delete</a></td>
		       </tr>
		  	</c:forEach>
		 	</tbody>
		</table>
	</div>
</div>

<!-- Scripts -->
<script type="text/javascript" src="${context}/js/global/bootstrap-timepicker.min.js"></script>
<script>
	initializeAdminToolDataTable($("#upcoming-events-table"), [ 2, "asc" ], true);
	formatMilisToLocalizedtime($(".event-time"));
</script>