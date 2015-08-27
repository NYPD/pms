<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="en-us">
	<head>
		<%@include file="../jsp-fragment/standard-head-block.jsp"%>
		<link href="${context}/css/error/error.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<div id="header-div">
			<c:if test="${hasSessionInfo == true}">
				<%@include file="../jsp-fragment/top-header.jsp"%>
			</c:if>
	  		<%@include file="../jsp-fragment/topNav.jsp"%>
  		</div>
  		<div class="container">
			<div class="row">
				<div class="col-xs-12">
					<c:choose>
					    <c:when test="${randomNumber == 0}">
					   		<div class="text-center">
								<img src="${context}/images/error/gupText.png" alt="gup text">
							</div>
					       	<div class="gup-tank-image">
								<img src="${context}/images/error/gup.png" alt="404">
							</div>
					    </c:when>
					    <c:when test="${randomNumber == 1}">
					        <div class="text-center">
								<img src="${context}/images/error/ntr404sans.png" alt="404">
							</div>
					    </c:when>
					    <c:when test="${randomNumber == 2}">
					        <div class="text-center">
								<img src="${context}/images/error/doujinNTR.png" alt="404">
							</div>
					    </c:when>
					</c:choose>
				</div>
			</div>
		</div>
		<%@include file="../jsp-fragment/footer.jsp"%>
	</body>
</html>