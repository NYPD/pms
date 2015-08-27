<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="isAddCrud" value="${event eq null}"/>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<c:choose>
	    <c:when test="${isAddCrud}">
	       <h4 class="modal-title">Add Event</h4>
	    </c:when>
	    <c:otherwise>
	        <h4 class="modal-title">Edit Event</h4>
	    </c:otherwise>
	</c:choose>
</div>
<div class="modal-body">
	<div class="container-fluid">
		<div class="row">
		<c:choose>
		    <c:when test="${isAddCrud}">
		       <form role="form" data-form-submit-url="add-event" data-success-text="Successfully added event">
		    </c:when>
		    <c:otherwise>
		        <form role="form" data-form-submit-url="edit-event" data-success-text="Edit successfull">
		        <input type="hidden" class="numeric numeric-whole" name="id" value="${event.id}">
		    </c:otherwise>
		</c:choose>
				<div class="col-sm-5">
				
					<div class="form-group">
						<label for="event-type">Type</label>
					    <input type="text" id="event-type" class="form-control alpha alpha-numeric" name="type" placeholder="Event Type" value="${event.type}">
					</div>
			   		<div class="row">
						<div class="col-md-6">
							<div class="form-group">
							    <label for="event-start-date">Start Date</label>
						    	<div class="input-group date">
									<input id="event-start-date" type="text" class="form-control alpha alpha-formatted-date">
									<span class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
							    <label for="event-start-time">Start Time</label>
						    	<div class="input-group">
				                    <input type="text" id="event-start-time" class="form-control numeric numeric-merica-time" />
				                    <span class="input-group-addon">
				                    	<i class="fa fa-clock-o fa-quarter-lg"></i>
				                    </span>
				                </div>
							</div>
						</div>
					</div>	
					
					<div class="form-group">
						<label for="event-image-url">Image</label>
					    <input id="event-image-url" type="text" class="form-control alpha alpha-link optional" name="imageUrl" placeholder="Image URL" value="${event.imageUrl}">
					</div>
					
				</div>
				
				<div class="col-sm-2 text-center">
					<h4>Repeat</h4>
					<div class="btn-group btn-group-event-repeat" role="group" data-toggle="buttons">
						<label class="btn btn-default btn-sm active">
							<input type="radio" name="repeat" autocomplete="off" value="0" checked> No
						</label>
						<label class="btn btn-default btn-sm">
							<input type="radio" name="repeat" autocomplete="off" value="1"> Yes
						</label>
					</div>
				</div>
				
				<div id="repeat-options-container" class="col-sm-5">
					<div class="row">
					
						<div class="col-md-6">
							<div class="form-group">
								<label for="repeat-type">Repeats</label>
							    <select id="repeat-type" class="form-control" name="repeatType">
									  <option value="0">Daily</option>
									  <option value="1">Weekly</option>
									  <!-- Listeners are disabled too in js -->
<!-- 									  <option value="2">Monthly</option> -->
<!-- 									  <option value="3">Yearly</option> -->
								</select>
							</div>
						</div>
						
<!-- 						<div class="col-md-6"> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label for="repeat-interval">Repeat Every</label> -->
<!-- 								<div class="input-group"> -->
<!-- 							      	<input id="repeat-interval" type="text" class="form-control" name="repeatInterval" value="1"> -->
<!-- 							      	<div id="repeat-interval-input-group-addon" class="input-group-addon">Day&#40;s&#41;</div> -->
<!-- 							    </div> -->
<!-- 							</div> -->
<!-- 						</div> -->
						
					</div>
					
					<div class="form-group form-group-repeats-on">
						<label>Repeat On</label>
						
						<div class="weekly-checkbox-container">
							<label class="checkbox-inline">
							  	<input type="checkbox" name="weeklyRepeatDays" value="0"> S
							</label>
							<label class="checkbox-inline">
							  	<input type="checkbox" name="weeklyRepeatDays" value="1"> M
							</label>
							<label class="checkbox-inline">
							  	<input type="checkbox" name="weeklyRepeatDays" value="2"> T
							</label>
							<label class="checkbox-inline">
							  	<input type="checkbox" name="weeklyRepeatDays" value="3"> W
							</label>
							<label class="checkbox-inline">
							  	<input type="checkbox" name="weeklyRepeatDays" value="4"> T
							</label>
							<label class="checkbox-inline">
							  	<input type="checkbox" name="weeklyRepeatDays" value="5"> F
							</label>
							<label class="checkbox-inline">
							  	<input type="checkbox" name="weeklyRepeatDays" value="6"> S
							</label>
						</div>
						
						<div class="monthly-radio-container">
							<label class="radio-inline">
							  	<input type="radio" name="monthlyRepeatBy" value="0"> day of the month
							</label>
							<label class="radio-inline">
							  	<input type="radio" name="monthlyRepeatBy" value="1"> day of the week
							</label>
						</div>
						
					</div>
					
					<div class="form-group form-group-event-end">
						<label>Ends</label>
						<div class="radio">
							<label>
								<input type="radio" name="eventEndType" value="0" title="Ends never">
							    Never
							</label>
						</div>
<!-- 						<div class="radio inline-block"> -->
<!-- 							<label> -->
<!-- 								<input type="radio" name="eventEndType" value="1" title="Ends after a number of occurrences"> -->
<!-- 							   	After: -->
<!-- 							</label> -->
<!-- 						</div> -->
<!-- 						<div class="radio-input-partner-container"> -->
<!-- 							<input id="occurence-end-frequency" type="text" class="form-control input-sm inline-block" name="occurenceEndFrequency" maxlength="6"> -->
<!-- 	                    	<label for="occurence-end-frequency">Occurrences</label> -->
<!-- 						</div> -->
<!-- 						<br> -->
						<div class="radio inline-block">
							<label>
						    	<input type="radio" name="eventEndType" value="2" title="End on a specific date">
						    	On:
						  	</label>
						</div>
						<div class="radio-input-partner-container">
							<div class="input-group input-group-sm date">
								<input type="text" class="form-control alpha alpha-formatted-date">
								<span class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</span>
							</div>
						</div>
					</div>
				</div>
	  		</form>
	  	</div>
  	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	<c:choose>
	    <c:when test="${isAddCrud}">
	       <button type="button" class="btn btn-primary btn-event-put" data-page-refresh>Add</button>
	    </c:when>
	    <c:otherwise>
	        <button type="button" class="btn btn-primary btn-event-put" data-page-refresh>Edit</button>
	    </c:otherwise>
	</c:choose>
</div>

<input id="event-time" type="hidden" value="${event.time}"/>
<!-- Scripts -->
<script src="${context}/js/modals/admin/event-put.js" type="text/javascript"></script>