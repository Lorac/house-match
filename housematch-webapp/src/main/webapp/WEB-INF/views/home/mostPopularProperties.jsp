<%@ page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%pageContext.setAttribute("propertyTypes", ca.ulaval.glo4003.housematch.domain.property.PropertyType.values());%>


<c:choose>
    <c:when test="${not empty propertyList.propertyViewModels}">
        <table class="table table-hover align-middle clickable-rows">
            <thead>
                <tr>
                    <th></th>
                    <th>Address</th>
                    <th>Price</th>
                    <th>Views</th>
                </tr>
            </thead>
            <c:forEach var="property" items="${propertyList.propertyViewModels}">
                <tr
                    onclick='window.location = "<%=PropertyController.PROPERTY_VIEW_BASE_URL%>${property.hashCode}"'>
                    <td><img src="http://place-hold.it/140x100" alt="Thumbnail"></td>
                    <td>${property.address}</td>
                    <td>${property.sellingPrice}&nbsp;$</td>
                    <td>${property.viewCount}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>There are no most visited properties for the selected category.</p>
    </c:otherwise>
</c:choose>