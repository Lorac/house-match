<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.spring.web.controllers.PropertyController"%>

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
        <h1 class="center">Properties for sale</h1>
        <c:choose>
            <c:when test="${not empty user.properties}">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <c:forEach var="property" items="${user.properties}">
                        <tr>
                            <td>${property.hashCode()}</td>
                            <td><a
                                href="<%=PropertyController.PROPERTY_DETAILS_UPDATE_BASE_URL + pageContext.getAttribute("property").hashCode()%>">${property.address}</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>
                    You have no properties for sale. You can put a property up for sale <a
                        href="<%=PropertyController.PROPERTY_CREATION_URL%>">here</a>
                </p>
            </c:otherwise>
        </c:choose>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>