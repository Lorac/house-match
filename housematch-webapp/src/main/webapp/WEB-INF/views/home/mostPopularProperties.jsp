<%@ page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyPhotoController"%>

<% pageContext.setAttribute("propertyTypes", ca.ulaval.glo4003.housematch.domain.property.PropertyType.values()); %>


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
            <c:forEach var="propertyViewModel" items="${propertyList.propertyViewModels}">
                <tr onclick='window.location = "<%=PropertyController.PROPERTY_VIEW_BASE_URL%>${propertyViewModel.hashCode}"'>
                    <td>
                    <%@include file="/WEB-INF/includes/propertyThumbnail.jsp"%>
                    </td>
                    <td>${propertyViewModel.address}</td>
                    <td>${propertyViewModel.sellingPrice}&nbsp;$</td>
                    <td>${propertyViewModel.viewCount}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>There are no most visited properties for the selected category.</p>
    </c:otherwise>
</c:choose>

<!-- Custom JavaScript for this page -->
<script src="/resources/js/components/property-thumbnail-loader.js"></script>