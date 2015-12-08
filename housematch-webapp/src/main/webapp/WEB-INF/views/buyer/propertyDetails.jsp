<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.PropertyPhotoController"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom CSS for this page -->
<link href="/resources/css/components/photo-manager.css" rel="stylesheet">

<script>
var bodyOnLoad = function() {
	<c:if test="${not empty propertyViewModel.photoViewModels}">
        var photoManager = new PhotoManager($("#property-photo-manager"), false, false, false);
        <c:forEach var="photoViewModel" items="${propertyViewModel.photoViewModels}">
            photoManager.addPhoto(${photoViewModel.photoHashCode}, "<%=PropertyPhotoController.PHOTO_THUMBNAIL_BASE_URL %>${photoViewModel.photoHashCode}/");
        </c:forEach>
    </c:if>
}
</script>

<title>HouseMatch - Home</title>
</head>
<body onload="bodyOnLoad()">
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <h1 class="center">Property Details</h1>

        <c:if test="${propertyViewModel.isPropertyFavorited() == true}">
            <div id="favoriteCreationAlert" class="alert alert-info">
                <span class="icon glyphicon glyphicon-star"></span>&nbsp;This property is in your favorites.
            </div>
        </c:if>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Property Photos</h3>
            </div>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${not empty propertyViewModel.photoViewModels}">
                        <%@include file="/WEB-INF/includes/photoManager.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <p class="center">This property currently has no photos.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Basic Property Information</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Property Type:</label>

                                <div class="col-sm-9">
                                    <p class="control-label">${propertyViewModel.propertyType.displayName}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Selling Price:</label>

                                <div class="col-sm-9">
                                    <p class="control-label">${propertyViewModel.sellingPrice}&nbsp;$</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Address:</label>

                                <div class="col-sm-9">
                                    <p class="control-label">${propertyViewModel.address}</p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Detailed Property Information</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-6">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Property Style:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.propertyStyle.displayName}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Ownership Type:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.ownershipType.displayName}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Price Details:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.priceDetails}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Number of Levels:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.numberOfLevels}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Located on Which Floor?:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.floorNumber}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Municipal Assessment:</label>

                                <div class="col-sm-6">
                                    <c:if test="${not empty property.propertyDetails.municipalAssessment}">
                                        <p class="control-label">${propertyViewModel.propertyDetails.municipalAssessment}&nbsp;$</p>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Year of Construction:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.yearOfConstruction}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Backyard Faces:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.backyardDirection.displayName}</p>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Total Number of Rooms:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.totalNumberOfRooms}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Number of Bedrooms:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.numberOfBedrooms}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Bedroom Details:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.bedroomDetails}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Number of Bathrooms:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.numberOfBathrooms}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Bathroom Details:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.bathroomDetails}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Number of Half Baths:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.numberOfHalfbaths}</p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <br>

                <div class="row">
                    <div class="col-md-6">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Building Dimensions:</label>

                                <div class="col-sm-6">
                                    <c:if test="${not empty property.propertyDetails.buildingDimensionsInSquareFeet}">
                                        <p class="control-label">${propertyViewModel.propertyDetails.buildingDimensionsInSquareFeet}&nbsp;ft&#178;</p>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Lot Dimensions:</label>

                                <div class="col-sm-6">
                                    <c:if test="${not empty property.propertyDetails.lotDimensionsInSquareFeet}">
                                        <p class="control-label">${propertyViewModel.propertyDetails.lotDimensionsInSquareFeet}&nbsp;ft&#178;</p>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Living Space Area:</label>

                                <div class="col-sm-6">
                                    <c:if test="${not empty property.propertyDetails.livingSpaceAreaInSquareFeet}">
                                        <p class="control-label">${propertyViewModel.propertyDetails.livingSpaceAreaInSquareFeet}&nbsp;ft&#178;</p>
                                    </c:if>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Number of Exterior Parking Spaces:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.numberOfExteriorParkingSpaces}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-6 control-label">Number of Interior Parking Spaces:</label>

                                <div class="col-sm-6">
                                    <p class="control-label">${propertyViewModel.propertyDetails.numberOfInteriorParkingSpaces}</p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="favoriteCreationAlert" class="alert alert-success alert-dismissible" style="display: none" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <span class="icon glyphicon glyphicon-star"></span>&nbsp;This property has been added to your favorites.
        </div>
        <div class="form-group">
            <div class="row center">
                <c:if test="${propertyViewModel.isPropertyFavorited() == false}">
                    <button id="addPropertiesToFavorite" tabindex="9" class="btn btn-success btn-lg" value="${propertyViewModel.propertyHashCode}">
                        <span class="icon glyphicon glyphicon-star"></span>&nbsp;Add to Favorites
                    </button>
                </c:if>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>

    <!-- Custom JavaScript for this page -->
    <script src="/resources/js/view-specific/property-details.js"></script>
    <script src="/resources/js/components/photo-manager.js"></script>
</body>
</html>