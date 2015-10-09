<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Sell Confirmation</title>
</head>
<body>
	<jsp:include page="../includes/navigationBar.jsp" />
	
	<div class="container">
		<h1>Edit Property Listing</h1>
		<form:form id="property-listing-update-form" class="form-horizontal" role="form"
			commandName="propertyListingUpdateForm" action="/updateListing" method="POST">
			
			<div class="form-group">
				<%@include file="../includes/alertMessage.jsp"%>
			</div>
			
			<h2>Extended Property Listing Details</h2>
			
			<p>Not implemented.</p>
			
			<div class="form-group">
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<input type="submit" name="property-listing-update-submit"
							id="property-listing-creation-submit" tabindex="8" class="form-control btn"
							value="Save Details">
					</div>
				</div>
			</div>
		</form:form>
	</div>
	
	<%@include file="../includes/footer.jsp"%>
</body>
</html>