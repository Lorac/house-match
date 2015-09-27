<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <%@include file="../includes/header.jsp" %>

    <!-- Custom styles for this page -->
    <link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Home</title>
</head>
<body>


	<jsp:include page="../includes/navigationBar.jsp" />
	<c:choose>
		<c:when test="${not empty sessionScope.username}">
			<h1>${sessionScope.user.username}, you are connected as a
				${sessionScope.user.role.displayName}.</h1>
		</c:when>
		<c:otherwise>
        You are viewing this page as anonymous.
    	</c:otherwise>
	</c:choose>
</body>
</html>