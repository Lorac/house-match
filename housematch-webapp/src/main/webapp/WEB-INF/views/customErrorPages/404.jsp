<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.HomeController"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<title>HouseMatch - Page Not Found</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />

    <div class="container">
        <h1>Oops!</h1>
        <p>The resource you are looking for cannot be found. <a href="<%= HomeController.HOME_URL %>">Return to home page</a>.</p>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>