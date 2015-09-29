<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Home</title>
</head>
<body>


	<jsp:include page="../includes/navigationBar.jsp" />
	<div class="container">
		<c:choose>
			<c:when test="${not empty sessionScope.username}">
				<p>Hello ${sessionScope.user.username}! You are connected as a
					${sessionScope.user.role.displayName}.</p>
			</c:when>
			<c:otherwise>
				<p>You are viewing this page as anonymous.</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>