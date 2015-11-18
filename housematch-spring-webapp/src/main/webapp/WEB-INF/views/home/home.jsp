<%@ page import="ca.ulaval.glo4003.housematch.spring.web.controllers.PropertyController"%>
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
        <div class="row">
            <label for="category">View the most popular properties:</label>
            <select id="propertyTypeSelector" class="form-control" onchange="javascript:showMostPopularProperties()">
				<c:forEach var="propertyType" items="${propertyTypes}" varStatus="count">
                                    <option value="${propertyType}" ${count.index == 0 ? 'selected' : ''}>${propertyType.displayName}</option>
                                </c:forEach>
            </select>
            <div id="mostPopularPropertiesViewContainer" class="col-lg-12"></div>
        </div>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>