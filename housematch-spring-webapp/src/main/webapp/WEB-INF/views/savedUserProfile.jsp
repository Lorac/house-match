<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Profile</title>
</head>
<body>
	<c:set var="homeLinkActive" value="active" scope="request" />
	<jsp:include page="../includes/navigationBar.jsp" />
	<div class="container">
		<p>Your profile has been saved!</p>
		<p>If you changed you email address, a confirmation link has been sent to your new email address to complete the modification.</p>
	</div>
</body>
</html>