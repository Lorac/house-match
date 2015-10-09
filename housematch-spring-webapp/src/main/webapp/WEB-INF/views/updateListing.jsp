<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Listing Details</title>
</head>
<body>
	<jsp:include page="../includes/navigationBar.jsp" />
	
	<div class="container">
		<h1>Extended Property Listing Details</h1>
		<form:form id="property-listing-update-form" class="form-horizontal" role="form"
			commandName="propertyListingUpdateForm" action="/updateListing" method="POST">
			
			<div class="form-group">
				<%@include file="../includes/alertMessage.jsp"%>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-2">Property Style:</label>
				<div class="col-sm-10">
					<form:input type="text" path="details.propertyStyle"
						class="form-control" min="0" tabindex="1"
						placeholder="Property Style" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Price Detail:</label>
				<div class="col-sm-10">
					<form:input type="text" path="details.priceDetail"
						class="form-control" min="0" tabindex="2"
						placeholder="Price Detail" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Number of Bedrooms:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.numberOfBedrooms"
						class="form-control" min="0" tabindex="3"
						placeholder="Number of Bedrooms" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Bedroom Details:</label>
				<div class="col-sm-10">
					<form:input type="text" path="details.bedroomDetails"
						class="form-control" min="0" tabindex="4"
						placeholder="Bedroom Details" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Number of Bathrooms:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.numberOfBathrooms"
						class="form-control" min="0" tabindex="5"
						placeholder="Number of Bathrooms" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Bathroom Details:</label>
				<div class="col-sm-10">
					<form:input type="text" path="details.bathroomDetails"
						class="form-control" min="0" tabindex="6"
						placeholder="Bathroom Details" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Total Number of Rooms:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.totalNumberOfRooms"
						class="form-control" min="0" tabindex="7"
						placeholder="Total Number of Rooms" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Number of Levels:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.numberOfLevels"
						class="form-control" min="0" tabindex="8"
						placeholder="Number of Levels" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Number of Half Baths:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.numberOfHalfbaths"
						class="form-control" min="0" tabindex="9"
						placeholder="Number of Half Baths" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Living Space Area:</label>
				<div class="col-sm-10">
					<form:input type="text" path="details.livingSpaceArea"
						class="form-control" min="0" tabindex="10"
						placeholder="Number of Half Baths" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Building Dimensions:</label>
				<div class="col-sm-10">
					<form:input type="text" path="details.buildingDimensions"
						class="form-control" min="0" tabindex="10"
						placeholder="Building Dimensions" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Lot Dimensions:</label>
				<div class="col-sm-10">
					<form:input type="text" path="details.lotDimensions"
						class="form-control" min="0" tabindex="11"
						placeholder="Lot Dimensions" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Year of Construction:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.yearOfConstruction"
						class="form-control" min="0" tabindex="12"
						placeholder="Year of Construction" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Municipal Assessment:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.municipalAssessment"
						class="form-control" min="0" tabindex="13"
						placeholder="Municipal Assessment" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Located on Which Floor:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.locatedOnWhichFloor"
						class="form-control" min="0" tabindex="14"
						placeholder="Located on Which Floor" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Number of Exterior Parking:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.numberOfExteriorParking"
						class="form-control" min="0" tabindex="15"
						placeholder="Number of Exterior Parking" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Number of Interior Parking:</label>
				<div class="col-sm-10">
					<form:input type="number" path="details.numberOfInteriorParking"
						class="form-control" min="0" tabindex="16"
						placeholder="Number of Interior Parking" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Ownership:</label>
				<div class="col-sm-10">
					<form:input type="text" path="details.ownership"
						class="form-control" min="0" tabindex="17"
						placeholder="Ownership" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Backyard Faces:</label>
				<div class="col-sm-10">
					<form:input type="text" path="details.backyardFaces"
						class="form-control" min="0" tabindex="18"
						placeholder="Backyard Faces" />
				</div>
			</div>
			
			<div class="form-group">
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<input type="submit" name="property-listing-update-submit"
							id="property-listing-creation-submit" tabindex="19" class="form-control btn"
							value="Save Details">
					</div>
				</div>
			</div>
		</form:form>
	</div>
	
	<%@include file="../includes/footer.jsp"%>
</body>
</html>