<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<header id="top-head">
	<div class="container">
		<div class="user-info-container pull-right">
		
			<div class="user-info-top-div">
				<span>Welcome, <span class="bold">${player.nickname}</span></span>
				<span class="clan-position">Position: <span class="bold">${memberInfo.roleI18n}</span></span>
			</div>
			
			<hr class="top-head-hr">
			
			<div class="user-info-bottom-div">
				<ul>
					<li class="header-refresh hand-cursor">
						<span class="last-refresh-time hidden-xs"></span>
						<i id="reload-user-info" class="fa fa-refresh"></i>
					</li>
					<li>
						<img alt="goldCoins" src="${context}/images/global/goldCoins.png">
						<span class="user-monetary-info">${player.personal.gold}</span>
					</li>
					<li>
						<img alt="silverCoins" src="${context}/images/global/silverCoins.png">
						<span class="user-monetary-info">${player.personal.credits}</span>
					</li>
					<li>
						<img alt="freeXp" src="${context}/images/global/freeXp.png">
						<span class="user-monetary-info">${player.personal.freeXp}</span>
					</li>
				</ul>
			</div>
			
		</div><!-- End of user-user-info-container div -->
	</div><!-- End of container div -->
</header>
