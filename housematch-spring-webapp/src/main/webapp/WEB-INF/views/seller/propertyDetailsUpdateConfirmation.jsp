<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.spring.web.controllers.HomeController;"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Property Update Confirmation</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />

    <div class="container">
        <p>
            Your property has been successfully updated and is visible to other buyers. <a href="<%=HomeController.HOME_URL%>">Go back
                to your profile</a>.
        </p>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>