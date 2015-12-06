<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>
<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyPhotoController"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<title>HouseMatch - Home</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <h1 class="center">Properties for sale</h1>
        <c:choose>
            <c:when test="${not empty propertyList.propertyViewModels}">
                <table class="table table-hover align-middle clickable-rows">
                    <thead>
                        <tr>
                            <th></th>
                            <th>ID</th>
                            <th>Address</th>
                            <th>Selling Price</th>
                        </tr>
                    </thead>
                    <c:forEach var="propertyViewModel" items="${propertyList.propertyViewModels}">
                        <tr
                            onclick="window.location = '<%=PropertyController.PROPERTY_UPDATE_BASE_URL%>${propertyViewModel.hashCode}';">
                            <td>
                            <%@include file="/WEB-INF/includes/propertyThumbnail.jsp"%>
                            </td>
                            <td>${propertyViewModel.hashCode}</td>
                            <td>${propertyViewModel.address}</td>
                            <td>${propertyViewModel.sellingPrice}&nbsp;$</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>
                    You have no properties for sale. You can put a property up for sale <a
                        href="<%=PropertyController.PROPERTY_CREATION_URL%>">here</a>.
                </p>
            </c:otherwise>
        </c:choose>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>