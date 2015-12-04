<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.UserProfileController"%>
<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<title>HouseMatch - Buyer Home</title>
</head>
<body>
    <c:set var="homeLinkActive" value="active" scope="request" />
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
                            <a href="<%=UserProfileController.USER_PROFILE_SETTINGS_URL%>"> <span
                                class="icon glyphicon glyphicon-user"></span>
                                <p class="itemLabel">Edit my profile</p></a>
                        </div>
                        <div class="col-sm-4 menuItem">
                            <a href="<%=PropertyController.PROPERTY_SEARCH_URL%>"><span class="icon glyphicon glyphicon-search"></span>
                                <p class="itemLabel">Search properties for sale</p></a>
                        </div>
                        <div class="col-sm-4 menuItem">
                            <a href="<%=PropertyController.FAVORITE_PROPERTIES_VIEW_URL%>">
                                <span class="icon glyphicon glyphicon-star"></span>
                                <p class="itemLabel">My favorite properties</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>