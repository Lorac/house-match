<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.spring.web.controllers.PropertyController"%>
<%@page import="ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyDetailsFormViewModel"%>

<%pageContext.setAttribute("propertyOwnershipTypes", ca.ulaval.glo4003.housematch.domain.property.PropertyOwnershipType.values());%>
<%pageContext.setAttribute("propertyStyles", ca.ulaval.glo4003.housematch.domain.property.PropertyStyle.values());%>
<%pageContext.setAttribute("cardinalDirections", ca.ulaval.glo4003.housematch.domain.CardinalDirection.values());%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Property Details</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />

    <div class="container">
        <h1 class="center">Property Details</h1>
        <form:form id="property-update-form" class="form-horizontal" role="form" modelAttribute="<%=PropertyDetailsFormViewModel.NAME%>"
            action='<%=PropertyController.PROPERTY_DETAILS_UPDATE_BASE_URL + request.getAttribute("propertyHashCode")%>' method="POST">

            <%@include file="/WEB-INF/includes/alertMessage.jsp"%>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">General Information</h3>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="control-label col-sm-3">Property Style:</label>
                        <div class="col-sm-9">
                            <form:select path="details.propertyStyle" class="form-control" tabindex="1">
                                <c:forEach var="propertyStyle" items="${propertyStyles}">
                                    <option value="${propertyStyle}"
                                        ${propertyStyle == propertyDetailsForm.details.propertyStyle ? 'selected' : ''}>${propertyStyle.displayName}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Ownership Type:</label>
                        <div class="col-sm-9">
                            <form:select path="details.ownershipType" class="form-control" tabindex="17">
                                <c:forEach var="ownershipType" items="${propertyOwnershipTypes}">
                                    <option value="${ownershipType}"
                                        ${ownershipType == propertyDetailsForm.details.ownershipType ? 'selected' : ''}>${ownershipType.displayName}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Price Detail:</label>
                        <div class="col-sm-9">
                            <form:input type="text" path="details.priceDetails" class="form-control" maxlength="256" tabindex="2"
                                placeholder="Price Details" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Number of Levels:</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.numberOfLevels" class="form-control" min="0" max="999" tabindex="8"
                                placeholder="Number of Levels" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Located on Which Floor?:</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.floorNumber" class="form-control" min="0" max="999" tabindex="14"
                                placeholder="Located on Which Floor?" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Municipal Assessment (CAD$):</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.municipalAssessment" class="form-control" min="1" max="9999999" tabindex="13"
                                placeholder="Municipal Assessment" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Year of Construction:</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.yearOfConstruction" class="form-control" min="0" max="9999" tabindex="12"
                                placeholder="Year of Construction" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Backyard Faces:</label>
                        <div class="col-sm-9">
                            <form:select path="details.backyardDirection" class="form-control" tabindex="17">
                                <c:forEach var="cardinalDirection" items="${cardinalDirections}">
                                    <option value="${cardinalDirection}"
                                        ${cardinalDirection == propertyDetailsForm.details.backyardDirection ? 'selected' : ''}>${cardinalDirection.displayName}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Room Layout</h3>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="control-label col-sm-3">Total Number of Rooms:</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.totalNumberOfRooms" class="form-control" min="1" max="999" tabindex="7"
                                placeholder="Total Number of Rooms" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Number of Bedrooms:</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.numberOfBedrooms" class="form-control" min="0" max="999" tabindex="3"
                                placeholder="Number of Bedrooms" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Bedroom Details:</label>
                        <div class="col-sm-9">
                            <form:input type="text" path="details.bedroomDetails" class="form-control" maxlength="256" tabindex="4"
                                placeholder="Bedroom Details" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Number of Bathrooms:</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.numberOfBathrooms" class="form-control" min="0" max="999" tabindex="5"
                                placeholder="Number of Bathrooms" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Bathroom Details:</label>
                        <div class="col-sm-9">
                            <form:input type="text" path="details.bathroomDetails" class="form-control" maxlength="256" tabindex="6"
                                placeholder="Bathroom Details" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Number of Half Baths:</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.numberOfHalfbaths" class="form-control" min="0" max="999" tabindex="9"
                                placeholder="Number of Half Baths" />
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Dimensions</h3>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="control-label col-sm-3">Building Dimensions (ft²):</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.buildingDimensionsInSquareFeet" class="form-control" min="1" max="9999999"
                                tabindex="10" placeholder="Building Dimensions (ft²)" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Lot Dimensions (ft²):</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.lotDimensionsInSquareFeet" class="form-control" min="1" max="9999999" tabindex="11"
                                placeholder="Lot Dimensions (ft²)" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Living Space Area (ft²):</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.livingSpaceAreaInSquareFeet" class="form-control" min="1" max="9999999" tabindex="10"
                                placeholder="Living Space Area (ft²)" />
                        </div>
                    </div>

                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Parking Space</h3>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="control-label col-sm-3">Number of Exterior Parking Spaces:</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.numberOfExteriorParkingSpaces" class="form-control" min="0" max="9999999"
                                tabindex="15" placeholder="Number of Exterior Parking Spaces" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Number of Interior Parking Spaces:</label>
                        <div class="col-sm-9">
                            <form:input type="number" path="details.numberOfInteriorParkingSpaces" class="form-control" min="0" max="9999999"
                                tabindex="16" placeholder="Number of Interior Parking Spaces" />
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row center">
                    <input type="submit" name="property-details-update-submit" id="property-details-update-submit" tabindex="19"
                        class="btn btn-primary btn-lg" value="Save Property Details">
                </div>
            </div>
        </form:form>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>