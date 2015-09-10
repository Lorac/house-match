<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<%@ include file="../includes/head.jsp" %>
		
		<!-- Custom styles for this page -->
		<link href="/resources/css/login.css" rel="stylesheet">
		
		<title>HouseMatch - Login</title>
	</head>
  
	<body>
		<div class="container">
		
			<form:form class="form-login" commandName="loginForm" action="login" method="POST">
					<h2 class="form-login-heading">Please log in</h2>
					<label for="inputEmail" class="sr-only">Email address</label>
					<form:input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
					<label for="inputPassword" class="sr-only">Password</label>
					<form:input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
			</form:form>
		
		</div> <!-- /container -->
			
		<%@ include file="../includes/footer.jsp" %>
	</body>
</html>