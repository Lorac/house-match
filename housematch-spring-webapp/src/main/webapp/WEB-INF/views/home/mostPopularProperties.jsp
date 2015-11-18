<%@ page import="ca.ulaval.glo4003.housematch.spring.web.controllers.PropertyController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%pageContext.setAttribute("propertyTypes", ca.ulaval.glo4003.housematch.domain.property.PropertyType.values());%>

<table class="table table-hover align-middle clickable-rows">
    <thead>
        <tr>
            <th></th>
            <th>ID</th>
            <th>Address</th>
            <th>Selling Price</th>
        </tr>
    </thead>
    <c:forEach var="property" items="${propertyList.properties}">
        <tr onclick='window.location = "<%=PropertyController.PROPERTY_VIEW_BASE_URL + pageContext.getAttribute("property").hashCode()%>"'>
            <td><img src="http://place-hold.it/140x100" alt="Thumbnail"></td>
            <td>${property.hashCode()}</td>
            <td>${property.address}</td>
            <td>${property.sellingPrice}&nbsp;$</td>
        </tr>
    </c:forEach>
</table>
