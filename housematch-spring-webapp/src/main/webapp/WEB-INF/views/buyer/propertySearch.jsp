<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>
<%@page import="ca.ulaval.glo4003.housematch.domain.property.PropertySortColumn"%>
<%@page import="ca.ulaval.glo4003.housematch.domain.SortOrder"%>

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
        <h1 class="center">Search Properties</h1>

        <c:if test="${not empty propertyList}">
            <h3 class="center">Search results</h3>
            <c:choose>
                <c:when test="${not empty propertyList.propertyViewModels}">
                    <table class="table table-hover align-middle clickable-rows">
                        <thead>
                            <tr>
                                <th></th>
                                <th>ID</th>
                                <th>Address</th>
                                <th style="min-width:100px;">
                                    <span class="glyphicon glyphicon-chevron-up" onclick="document.location = '<%=PropertyController.PROPERTY_SEARCH_URL%>?sortColumn=<%=PropertySortColumn.SELLING_PRICE%>&sortOrder=<%=SortOrder.ASCENDING%>';"></span>
                                    <span class="glyphicon glyphicon-chevron-down" onclick="document.location = '<%=PropertyController.PROPERTY_SEARCH_URL%>?sortColumn=<%=PropertySortColumn.SELLING_PRICE%>&sortOrder=<%=SortOrder.DESCENDING%>';"></span>Price</th>
                                <th>
                                	<span class="glyphicon glyphicon-chevron-up" onclick="document.location = '<%=PropertyController.PROPERTY_SEARCH_URL%>?sortColumn=<%=PropertySortColumn.CREATION_DATE%>&sortOrder=<%=SortOrder.ASCENDING%>';"></span>
                                    <span class="glyphicon glyphicon-chevron-down" onclick="document.location = '<%=PropertyController.PROPERTY_SEARCH_URL%>?sortColumn=<%=PropertySortColumn.CREATION_DATE%>&sortOrder=<%=SortOrder.DESCENDING%>';"></span>Date</th>
                            </tr>
                        </thead>
                        <c:forEach var="propertyViewModel" items="${propertyList.propertyViewModels}" varStatus="status">
                            <tr onclick="document.location = '<%=PropertyController.PROPERTY_VIEW_BASE_URL%>${propertyViewModel.hashCode}';">
                                <td><img src="http://place-hold.it/140x100" alt="Thumbnail"></td>
                                <td>${propertyViewModel.getHashCode()}</td>
                                <td>${propertyViewModel.getAddress()}</td>
                                <td>${propertyViewModel.getSellingPrice()}&nbsp;$</td>
                                <td>${propertyViewModel.getCreationDate()}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>Your query did not match any property.</p>
                </c:otherwise>
            </c:choose>
        </c:if>

    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>