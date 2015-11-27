<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <%@include file="/WEB-INF/includes/header.jsp" %>

    <!-- Custom styles for this page -->
    <link href="/resources/css/login.css" rel="stylesheet">



    <title>HouseMatch - Home</title>
</head>
<body>
<jsp:include page="/WEB-INF/includes/navigationBar.jsp"/>
<div class="container">
    <h1 class="center">Property Details</h1>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Photos</h3>
        </div>
        <div class="panel-body">

            <div id="imageCarousel" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#imageCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#imageCarousel" data-slide-to="1"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="http://place-hold.it/1200x256">

                        <div class="container">
                            <div class="carousel-caption">
                                <p>Photo 1</p>

                                <p>Feature coming soon.</p>
                            </div>
                        </div>
                    </div>
                    <div class="item">
                        <img src="http://place-hold.it/1200x256">

                        <div class="container">
                            <div class="carousel-caption">
                                <p>Photo 2</p>

                                <p>Feature coming soon.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <a class="left carousel-control" href="#imageCarousel" role="button" data-slide="prev"> <span
                        class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span>
                </a> <a class="right carousel-control" href="#imageCarousel" role="button" data-slide="next"> <span
                    class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span>
            </a>
            </div>

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
                                <p class="control-label">${property.propertyType.displayName}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Selling Price:</label>

                            <div class="col-sm-9">
                                <p class="control-label">${property.sellingPrice}&nbsp;$</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Address:</label>

                            <div class="col-sm-9">
                                <p class="control-label">${property.address}</p>
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
                                <p class="control-label">${property.propertyDetails.propertyStyle.displayName}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Ownership Type:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.ownershipType.displayName}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Price Details:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.priceDetails}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Number of Levels:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.numberOfLevels}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Located on Which Floor?:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.floorNumber}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Municipal Assessment:</label>

                            <div class="col-sm-6">
                                <c:if test="${not empty property.propertyDetails.municipalAssessment}">
                                    <p class="control-label">${property.propertyDetails.municipalAssessment}&nbsp;$</p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Year of Construction:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.yearOfConstruction}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Backyard Faces:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.backyardDirection.displayName}</p>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-6">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Total Number of Rooms:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.totalNumberOfRooms}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Number of Bedrooms:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.numberOfBedrooms}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Bedroom Details:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.bedroomDetails}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Number of Bathrooms:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.numberOfBathrooms}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Bathroom Details:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.bathroomDetails}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Number of Half Baths:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.numberOfHalfbaths}</p>
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
                                    <p class="control-label">${property.propertyDetails.buildingDimensionsInSquareFeet}&nbsp;ft&#178;</p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Lot Dimensions:</label>

                            <div class="col-sm-6">
                                <c:if test="${not empty property.propertyDetails.lotDimensionsInSquareFeet}">
                                    <p class="control-label">${property.propertyDetails.lotDimensionsInSquareFeet}&nbsp;ft&#178;</p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Living Space Area:</label>

                            <div class="col-sm-6">
                                <c:if test="${not empty property.propertyDetails.livingSpaceAreaInSquareFeet}">
                                    <p class="control-label">${property.propertyDetails.livingSpaceAreaInSquareFeet}&nbsp;ft&#178;</p>
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
                                <p class="control-label">${property.propertyDetails.numberOfExteriorParkingSpaces}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Number of Interior Parking Spaces:</label>

                            <div class="col-sm-6">
                                <p class="control-label">${property.propertyDetails.numberOfInteriorParkingSpaces}</p>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row center">
            <input tabindex="8" class="btn btn-primary btn-lg" onclick="window.history.back()" value="Back to Search Results">
            <button id="addPropertiesToFavorite" tabindex="9" class="btn btn-success btn-lg"
                    value="${property.hashCode}">Add to favorites
            </button>
        </div>
    </div>
    <div id="favoriteAlert" class="alert alert-success alert-dismissible fade" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        You added this property to your favorite
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>
<script src="/resources/js/buyerFavorite.js"></script>
</body>
</html>