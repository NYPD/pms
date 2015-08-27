<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<%@include file="../jsp-fragment/standard-head-block.jsp"%>
	</head>
	<body>
		<div id="header-div">
			<%@include file="../jsp-fragment/top-header.jsp"%>
  			<%@include file="../jsp-fragment/topNav.jsp"%>
  		</div>
		<div class="container">
			<div class="row">
				<div class="col-sm-5 col-md-4 text-center">
					<img alt="ritsu hardhat" src="${context}/images/error/ritsu-hard-hat.png">
				</div>
				<div class="col-sm-7 col-md-8">
					<h2>
						Well, looks like something broke somewhere in the back-end.
						See if you could try again, or if this keeps happening, submit a <a class="hand-cursor submit-bug-report">report</a> and lets see if NY responds.
					</h2>
				</div>
			</div>
		</div>
		<%@include file="../jsp-fragment/footer.jsp"%>
	</body>
	<!-- Scripts -->
  	<%@include file="../jsp-fragment/global-scripts.jsp"%>
</html>