<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<%@ include file="../includes/header.jsp" %>
		
		<!-- Custom styles for this page -->
		<link href="/resources/css/login.css" rel="stylesheet">
		
		<title>HouseMatch - Login</title>
	</head>
  
	<body>
		<div class="container">
		
			<form:form class="form-login" commandName="loginFormViewModel" action="login" method="POST">
					<h2 class="form-login-heading">Please log in</h2>
					<label for="inputEmail" class="sr-only">Email address</label>
					<form:input type="email" path="email" id="inputEmail" class="form-control" placeholder="Email address" />
					<label for="inputPassword" class="sr-only">Password</label>
					<form:input type="password" path="password" id="inputPassword" class="form-control" placeholder="Password" />
					<button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
			</form:form>
		
		</div> <!-- /container -->
			
		<%@ include file="../includes/footer.jsp" %>
	</body>
</html>