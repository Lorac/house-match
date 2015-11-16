<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Home</title>
</head>
<body>


    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <p>
                    Hello <b>${sessionScope.user.username}</b>! You are connected as a ${sessionScope.user.role.displayName}.
                </p>
            </c:when>
            <c:otherwise>
                <p>You are viewing this page as anonymous.</p>
            </c:otherwise>
        </c:choose>
        <div class="row">
            <div class="col-md-10">Top Properties Here</div>
            <div class="col-md-2">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Statistics</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">Number of Active Buyers:</label>
                                        <div class="col-sm-9">
                                            <p class="control-label">${statistics.userStatistics.numberOfActiveBuyers}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">Number of Active Sellers:</label>
                                        <div class="col-sm-9">
                                            <p class="control-label">${statistics.userStatistics.numberOfActiveSellers}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">Number of Properties Sold This Year:</label>
                                        <div class="col-sm-9">
                                            <p class="control-label">${statistics.propertyStatistics.numberOfPropertiesSoldThisYear}</p>
                                        </div>
                                    </div>
                                    <label class="col-sm-3 control-label">Number of Properties For Sale:</label>
                                    <c:forEach var="propertyType" items="${propertyTypes}">
                                        <div class="form-group">
                                            <div class="col-sm-9">
                                                <p class="control-label">${propertyType}:&nbsp;${statistics.propertyStatistics.numberOfPropertiesForSale[propertyType]}</p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>