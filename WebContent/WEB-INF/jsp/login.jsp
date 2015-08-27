<!DOCTYPE html>
<html lang="en-us">
	<head>
    	<%@include file="jsp-fragment/standard-head-block.jsp"%>
    	
    	<link href="${context}/css/login.css" rel="stylesheet" type="text/css"/>
  	</head>
  	<body>
	  	<div class="container">
	  		<div class="login-form-container">
				<h4 class="text-center">Sign in with your Wargaming.net ID</h4><br>
				<div class="text-center">
					<img src="${context}/images/login/WG_Logo_Sharpen_Metal_01_325x177.png" alt="Wargaming Logo Sharpen Metal">
				</div><br>
				<a href="../entry/openIDAuthentication?rememberMe=yes" class="btn btn-lg btn-block btn-primary" role="button">Sign In &#38; Remember Me</a>
			 	<a href="../entry/openIDAuthentication?rememberMe=no" class="btn btn-lg btn-block btn-primary" role="button">Sign In</a>
			 	<c:if test="${loginError == true}">
		 			<div class="alert alert-danger alert-login-error alert-dismissible" role="alert">
					  	<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					  	<strong>Crap!</strong> Something went wrong during login, see if you can try again.
					</div>
			 	</c:if>
			</div>
		</div>
		
		<!-- Scripts -->
		<%@include file="jsp-fragment/global-scripts.jsp"%>
  	</body>
</html>
