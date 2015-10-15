<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.spring.web.controllers.HomeController;"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Contact Information Update Confirmation</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <p>Your contact information has been updated.</p>
        <c:if test="${sessionScope.user.isActivated() == false}">
            <p>
                <b>IMPORTANT: </b>You need to confirm your new email address. Click on the link that was sent to <i>${sessionScope.user.email}</i>.
                You will not be able to navigate the site until this step is complete.
            </p>
        </c:if>
        <p>
            <a href="<%= HomeController.HOME_URL %>">Go back to your profile</a>
        </p>
    </div>
    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>