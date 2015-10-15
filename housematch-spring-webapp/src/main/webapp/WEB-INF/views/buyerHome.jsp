<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <%@include file="../includes/header.jsp" %>

    <!-- Custom styles for this page -->
    <link href="/resources/css/login.css" rel="stylesheet">


    <title>HouseMatch - Buyer Home</title>
</head>
<body>
<c:set var="homeLinkActive" value="active" scope="request" />
	<jsp:include page="../includes/navigationBar.jsp" />
	<div class="container">
		<p>Hello <sec:authentication property="principal.username"/> !</p>
		<div class="profileMenu">
			<div class="row">
				<div class="col-sm-4 menuItem">
				<a href="/updateContactInformation">
					<span class="icon glyphicon glyphicon-envelope"></span>
						<p class="itemLabel">Update contact information</p></a>
				</div>
				<div class="col-sm-4 menuItem">
					<span class="icon glyphicon glyphicon-question-sign"></span>
						<p class="itemLabel">Sample menu item</p>
				</div>
				<div class="col-sm-4 menuItem">
					<span class="icon glyphicon glyphicon-question-sign"></span>
						<p class="itemLabel">Sample menu item</p>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="../includes/footer.jsp"%>
</body>
</html>