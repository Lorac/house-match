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
        <h1 class="center">Property Details</h1>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Images</h3>
            </div>
            <div class="panel-body">...</div>
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
                            <label class="col-sm-4 control-label">Property Type:</label>
                            <div class="col-sm-8">
                                <p class="control-label">${property.propertyType.displayName}</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Selling Price:</label>
                            <div class="col-sm-8">
                                <p class="control-label">${property.sellingPrice} $</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Address:</label>
                            <div class="col-sm-8">
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
                                <label class="col-sm-4 control-label">Property Style:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.propertyStyle.displayName}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Ownership Type:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.ownershipType.displayName}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Price Detail:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.priceDetail}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Number of Levels:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.numberOfLevels}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Located on Which Floor?:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.floorNumber}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Municipal Assessment:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.municipalAssessment}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Year of Construction:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.yearOfConstruction}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Backyard Faces:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.backyardDirection.displayName}</p>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Total Number of Rooms:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.totalNumberOfRooms}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Number of Bedrooms:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.numberOfBedrooms}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Bedroom Details:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.bedroomDetails}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Number of Bathrooms:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.numberOfBathrooms}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Bathroom Details:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.bathroomDetails}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Number of Half Baths:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.numberOfHalfbaths}</p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Building Dimensions (ft²):</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.buildingDimensionsInSquareFeet}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Lot Dimensions (ft²):</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.lotDimensionsInSquareFeet}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Living Space Area (ft²):</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.livingSpaceAreaInSquareFeet}</p>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Number of Exterior Parking Spaces:</label>
                                <div class="col-sm-8">
                                    <p class="control-label">${property.propertyDetails.numberOfExteriorParkingSpaces}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Number of Interior Parking Spaces:</label>
                                <div class="col-sm-8">
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
                    <input tabindex="8"
                        class="btn btn-primary btn-lg" onclick="window.history.back()" value="Back to search results">
                </div>
            </div>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>