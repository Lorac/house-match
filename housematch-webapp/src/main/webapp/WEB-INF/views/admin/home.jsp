<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyPhotoController"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<title>HouseMatch - Administrator Home</title>
</head>
<body onload="javascript:adminInit()">
    <c:set var="homeLinkActive" value="active" scope="request" />
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <p>
            Hello <b><sec:authentication property="principal.username" /></b>!
        </p>
        <hr>
        <div class="row">
            <div class="col-md-8">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Menu</h3>
                    </div>
                    <div class="panel-body">
                        <div class="profileMenu">
                            <div class="row">
                                <div class="col-sm-4 menuItem">
                                    <a href="<%=PropertyPhotoController.PHOTO_REVIEW_URL%>"><span class="icon glyphicon glyphicon glyphicon-ok"></span>
                                        <p class="itemLabel">Property photo review</p></a>
                                </div>
                                <div class="col-sm-4 menuItem">
                                    <span class="icon glyphicon glyphicon-question-sign"></span>
                                    <p class="itemLabel">Sample menu item</p>
                                </div>
                                <div class="col-sm-4 menuItem">
                                    <span class="icon glyphicon glyphicon-question-sign"></span>
                                    <p class="itemLabel">Sample menu item</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Site Statistics</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div id="statisticsContainer" class="col-lg-12"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>

    <!-- Custom JavaScript for this page -->
    <script src="/resources/js/view-specific/home.js"></script>
</body>
</html>