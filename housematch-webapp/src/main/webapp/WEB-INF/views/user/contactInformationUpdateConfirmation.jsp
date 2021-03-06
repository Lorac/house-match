<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.HomeController"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<title>HouseMatch - Contact Information Update Confirmation</title>
</head>
<body>
    <c:if test="${sessionScope.user.isActivated() == false}">
        <c:set var="homeLinkHidden" value="true" scope="request" />
    </c:if>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <p>
            Your contact information has been updated.
            <c:if test="${sessionScope.user.isActivated() == true}">
                <a href="<%=HomeController.HOME_URL%>">Go back to your profile</a>.
            </c:if>
        </p>

        <c:if test="${sessionScope.user.isActivated() == false}">
            <p>
                <b>IMPORTANT: </b>You need to confirm your new email address. An activation link has been sent to <i>${sessionScope.user.email}</i>.
                You will not be able to browse the site until this step is complete.
            </p>
        </c:if>

    </div>
    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>