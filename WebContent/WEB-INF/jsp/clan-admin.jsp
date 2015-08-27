<!DOCTYPE html>
<html lang="en-us">
	<head>
		<%@include file="jsp-fragment/standard-head-block.jsp"%>
		
		<link href="${context}/css/global/datepicker3.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/global/bootstrap-colorpicker.css" rel="stylesheet" type="text/css"/>
		<link href="${context}/css/admin.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<div id="header-div">
			<%@include file="jsp-fragment/top-header.jsp"%>
  			<%@include file="jsp-fragment/topNav.jsp"%>
		</div>
		<div class="container">
			<div class="row">
		        <div class="col-sm-3 col-lg-2 sidebar">
			        <h3>Admin Tools</h3>
			        <ul class="nav nav-sidebar">
			            <li class="admin-sidebar-link" data-admin-tool-url="get-news-carousel-tool"><a>News Carousel</a></li>
			            <li class="admin-sidebar-link" data-admin-tool-url="get-caller-attendance-tool"><a>Caller Attendance</a></li>
			            <li class="admin-sidebar-link" data-admin-tool-url="get-player-checkin-tool"><a>Player Check-in</a></li>
			            <li class="admin-sidebar-link" data-admin-tool-url="get-custom-event-tool"><a>Custom Event</a></li>
			            <li class="admin-sidebar-link" data-admin-tool-url="get-incentive-default-tool"><a>Incentive Defaults</a></li>
			            <li class="admin-sidebar-link" data-admin-tool-url="get-clan-settings-tool"><a>Clan Settings</a></li>
			         </ul>
		        </div>
		        <div class="col-sm-9 col-lg-10 col-admin-tool">
		        	<div id="row-alert"></div>
		        	<div class="admin-tool-loading-container">
						<div class="bounce1"></div>
						<div class="bounce2"></div>
						<div class="bounce3"></div>
					</div>
					<div id="admin-tool-container"></div>
		       </div>
		    </div>
		
		</div><!-- End of container -->
		<%@include file="jsp-fragment/footer.jsp"%>
		<%@include file="jsp-fragment/admin-tools/admin-modals.jsp"%>
		
		<!-- Scripts -->
		<%@include file="jsp-fragment/global-scripts.jsp"%>
		<script src="${context}/js/global/bootstrap-datepicker.js" type="text/javascript"></script>
		<script src="${context}/js/global/bootstrap-colorpicker.min.js" type="text/javascript"></script>
		<script src="${context}/js/admin.js" type="text/javascript"></script>
		<script src="${context}/js/clan-admin.js" type="text/javascript"></script>
	</body>
</html>