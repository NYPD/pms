<header id="top-nav">
	<div class="container">
		<div class="navbar navbar-default" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
			     	<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			        	<span class="sr-only">Toggle navigation</span>
			        	<span class="icon-bar"></span>
			        	<span class="icon-bar"></span>
			        	<span class="icon-bar"></span>
			      	</button>
			      	<a class="navbar-brand" href="${context}/app/home">
			      		<img class="navbar-image-logo" src="${clanInfo.emblems.x32.portal}"/>
			      		<span class="hidden-xs hidden-sm">Payment Management System 0.9.0</span>
			      		<span class="visible-xs-inline-block visible-sm-inline-block">PMS 0.9.0</span>
			     	</a>
			    </div>
			    <div class="collapse navbar-collapse">
			      	<ul class="nav navbar-nav navbar-right">
			        	<li id="home"><a href="${context}/app/home"><i class="fa fa-home"></i> Home</a></li>
			        	<li id="clan-info" class="dropdown">
				        	<a class="dropdown-toggle hand-cursor" data-toggle="dropdown"><i class="fa fa-info"></i> Clan Info<span class="caret"></span></a>
				          	<ul class="dropdown-menu" role="menu">
				            	<li id="attendance"><a href="${context}/app/attendance"><i class="fa fa-clock-o"></i> Attendance</a></li>
				            	<li id="incentive"><a href="${context}/app/incentive"><i class="fa fa-trophy"></i> Tank Incentive</a></li>
				          	</ul>
				        </li>
			        	<c:if test="${user.role.roleId == 2 || user.role.roleId == 3}">
			        		<li id="payments"><a href="${context}/app/payouts"><i class="fa fa-jpy"></i> Payouts</a></li>
			        		<li id="admin" class="dropdown">
					        	<a class="dropdown-toggle hand-cursor" data-toggle="dropdown"><i class="fa fa-wrench"></i> Admin<span class="caret"></span></a>
					          	<ul class="dropdown-menu" role="menu">
					            	<li id="clan-settings"><a href="${context}/app/admin/clan/administration-tools"><i class="fa fa-users"></i> Clan Settings</a></li>
					            	<c:if test="${user.role.roleId == 3}">
					            		<li id="system-settings"><a href="${context}/app/admin/system/administration-tools"><i class="fa fa-terminal"></i> System Settings</a></li>
					            	</c:if>
					          	</ul>
					        </li>
			        	</c:if>
			        	<li id="logout"><a href="${context}/app/logout"><i class="fa fa-power-off"></i> Logout</a></li>
			      	</ul>
			   	</div>
			</div>
		</div>
	</div>
</header>