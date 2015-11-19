<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>
<%@page import="ca.ulaval.glo4003.housematch.web.viewmodels.PropertySearchFormViewModel"%>

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

        <form:form id="property-search-form" role="form" modelAttribute="<%=PropertySearchFormViewModel.NAME%>"
            action='<%=PropertyController.PROPERTY_SEARCH_EXECUTE_URL%>' method="GET">
            <div class="input-group">
                <form:input type="text" path="searchExpression" class="form-control" maxlength="32" placeholder="Search for..."
                    autofocus="true" />
                <span class="input-group-btn"> <input type="submit" name="property-search-submit" id="property-seatch-submit"
                        tabindex="0" class="btn btn-default" value="Search">
                </span>
            </div>
        </form:form>

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
                                <th>
                                    <span class="glyphicon glyphicon-chevron-up" onclick="document.location = '<%=PropertyController.PROPERTY_SEARCH_SORT_BY_PRICE_ASC_URL%>';"></span>
                                    <span class="glyphicon glyphicon-chevron-down" onclick="document.location = '<%=PropertyController.PROPERTY_SEARCH_SORT_BY_PRICE_DESC_URL%>';"></span>
                                    Price</th>
                                <th>
                                	<span class="glyphicon glyphicon-chevron-up" onclick="document.location = '<%=PropertyController.PROPERTY_SEARCH_SORT_BY_DATE_ASC_URL%>';"></span>
                                    <span class="glyphicon glyphicon-chevron-down" onclick="document.location = '<%=PropertyController.PROPERTY_SEARCH_SORT_BY_DATE_DESC_URL%>';"></span>
                                    Date</th>
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