<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyController"%>
<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyPhotoController"%>
<%@page import="ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel"%>

<% pageContext.setAttribute("propertyOwnershipTypes", ca.ulaval.glo4003.housematch.domain.property.PropertyOwnershipType.values()); %>
<% pageContext.setAttribute("propertyStyles", ca.ulaval.glo4003.housematch.domain.property.PropertyStyle.values()); %>
<% pageContext.setAttribute("cardinalDirections", ca.ulaval.glo4003.housematch.domain.CardinalDirection.values()); %>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom CSS for this page -->
<link href="/resources/css/components/photo-manager.css" rel="stylesheet">

<script>
var bodyOnLoad = function() {
	<c:if test="${not empty propertyPhotoList.propertyPhotoViewModels}">
        var photoManager = new PhotoManager($("#property-photo-manager"), false, true, false);
        <c:forEach var="propertyPhotoViewModel" items="${propertyPhotoList.propertyPhotoViewModels}">
        	photoManager.addPhoto(${propertyPhotoViewModel.hashCode}, "<%=PropertyPhotoController.PHOTO_THUMBNAIL_BASE_DOWNLOAD_URL %>${propertyPhotoViewModel.hashCode}/", null, "<%= PropertyPhotoController.PHOTO_APPROVE_BASE_URL %>${propertyPhotoViewModel.hashCode}/", "<%= PropertyPhotoController.PHOTO_REJECT_BASE_URL %>${propertyPhotoViewModel.hashCode}/");
        </c:forEach>
    </c:if>
}
</script>

<title>HouseMatch - Edit Property</title>
</head>
<body onload="bodyOnLoad()">
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />

    <div class="container">
        <h1 class="center">Property Photo Review</h1>
        
        <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Photos Pending Approval</h3>
                </div>
                <div class="panel-body">
                <c:choose>
                    <c:when test="${not empty propertyPhotoList.propertyPhotoViewModels}">
                        <%@include file="/WEB-INF/includes/photoManager.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <p class="center">There are currently no photos waiting for approval.</p>
                    </c:otherwise>
                </c:choose>
                </div>
            </div>
            
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>

    <!-- Custom JavaScript for this page -->
    <script src="/resources/js/components/photo-manager.js"></script>
</body>
</html>