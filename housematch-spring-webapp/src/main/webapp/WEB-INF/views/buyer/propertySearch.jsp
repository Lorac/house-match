<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.spring.web.controllers.PropertyController"%>
<%@page import="ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchFormViewModel"%>

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

        <c:if test="${not empty propertySearchResults}">
            <h3 class="center">Search results</h3>
            <c:choose>
                <c:when test="${not empty propertySearchResults.properties}">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Address</th>
                            </tr>
                        </thead>
                        <c:forEach var="property" items="${propertySearchResults.properties}">
                            <tr>
                                <td>${property.hashCode()}</td>
                                <td><a
                                    href="<%=PropertyController.PROPERTY_DETAILS_BASE_URL + pageContext.getAttribute("property").hashCode()%>">${property.address}</a>
                                </td>
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