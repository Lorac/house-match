<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.UserProfileController"%>
<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Buyer Home</title>
</head>
<body>

    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <p>
            Hello <b><sec:authentication property="principal.username" /></b>!
        </p>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Menu</h3>
            </div>
            <div class="panel-body">
                <div class="profileMenu">
                    <div class="row">
                        <div class="col-sm-4 menuItem">
                            <a href="<%=UserProfileController.CONTACT_INFO_UPDATE_URL%>"> <span
                                class="icon glyphicon glyphicon-envelope"></span>
                                <p class="itemLabel">Update contact information</p></a>
                        </div>
                        <c:choose>
                            <c:when test="${sessionScope.user.hasRole('BUYER')}">
                                <div class="col-sm-4 menuItem">
                                    <a href="<%=UserProfileController.NOTIFICATION_SETTINGS_URL%>"> <span
                                        class="icon glyphicon glyphicon-bell"></span>
                                        <p class="itemLabel">Edit notification settings</p></a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col-sm-4 menuItem">
                                    <span class="icon glyphicon glyphicon-question-sign"></span>
                                    <p class="itemLabel">Sample menu item</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="col-sm-4 menuItem">
                            <span class="icon glyphicon glyphicon-question-sign"></span>
                            <p class="itemLabel">Sample menu item</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>