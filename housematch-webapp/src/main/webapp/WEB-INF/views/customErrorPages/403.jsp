<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.HomeController"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Access Forbidden</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />

    <div class="container">
        <h1>Access Denied</h1>
        <p>You are not allowed to view this page. <a href="<%= HomeController.HOME_URL %>">Return to home page</a>.</p>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>