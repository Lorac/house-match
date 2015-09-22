<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Login</title>
</head>
<body>
	<c:set var="homeActive" value="active" scope="request"/>
	<jsp:include page="_navigation.jsp"/>
	 <c:if test="${not empty sessionScope.username}">
	     <h1>${sessionScope.username}, your are connected as a ${sessionScope.role}</h1>
     </c:if>
</body>
</html>