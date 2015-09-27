<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Buyer Home</title>
</head>
<body>
	<c:set var="homeLinkActive" value="active" scope="request" />
	<jsp:include page="../includes/navigationBar.jsp" />
	<div class="container">
		<p>Hello ${sessionScope.user.username}! You are connected as a
			${sessionScope.user.role.displayName}.</p>
	</div>
</body>
</html>