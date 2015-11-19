<%@ page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%
    pageContext.setAttribute("propertyTypes", ca.ulaval.glo4003.housematch.domain.property.PropertyType.values());
%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">

<script src="/resources/js/home.js"></script>

<title>HouseMatch - Home</title>
</head>
<body onload="javascript:init()">


    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
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
            </div>
        </div>
        <hr>
        <br>
        <div class="row">
            <div class="col-md-8">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-sm-6">Browse the most popular properties:</label>
                        <div class="col-sm-6">
                            <select id="propertyTypeSelector" class="form-control" onchange="javascript:showMostPopularProperties()">
                                <c:forEach var="propertyType" items="${propertyTypes}" varStatus="count">
                                    <option value="${propertyType}" ${count.index == 0 ? 'selected' : ''}>${propertyType.displayName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
                <div id="mostPopularPropertiesViewContainer"></div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Site Statistics</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-9 control-label">Number of Active Buyers:</label>
                                        <div class="col-sm-3">
                                            <p class="control-label">${statistics.userStatistics.numberOfActiveBuyers}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-9 control-label">Number of Active Sellers:</label>
                                        <div class="col-sm-3">
                                            <p class="control-label">${statistics.userStatistics.numberOfActiveSellers}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-9 control-label">Number of Properties Sold This Year:</label>
                                        <div class="col-sm-3">
                                            <p class="control-label">${statistics.propertyStatistics.numberOfSoldPropertiesThisYear}</p>
                                        </div>
                                    </div>
                                    <label class="control-label">Number of Properties For Sale:</label>
                                    <c:forEach var="propertyType" items="${propertyTypes}">
                                        <div class="row">
                                            <p class="control-label col-sm-12">${propertyType.displayName}:&nbsp;
                                                <c:choose>
                                                    <c:when
                                                        test="${not empty statistics.propertyStatistics.numberOfPropertiesForSale[propertyType]}">
                                                      ${statistics.propertyStatistics.numberOfPropertiesForSale[propertyType]}
                                                    </c:when>
                                                    <c:otherwise>0</c:otherwise>
                                                </c:choose>
                                            </p>
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