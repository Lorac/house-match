<%@ page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<% pageContext.setAttribute("propertyTypes", ca.ulaval.glo4003.housematch.domain.property.PropertyType.values()); %>

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
                    <c:when test="${not empty statistics.propertyStatistics.numberOfPropertiesForSale[propertyType]}">
                                                      ${statistics.propertyStatistics.numberOfPropertiesForSale[propertyType]}
                                                    </c:when>
                    <c:otherwise>0</c:otherwise>
                </c:choose>
            </p>
        </div>
    </c:forEach>
</form>