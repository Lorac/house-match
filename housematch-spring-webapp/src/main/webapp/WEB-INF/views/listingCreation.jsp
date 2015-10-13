<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Sell</title>
</head>
<body>
	<jsp:include page="../includes/navigationBar.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="form">
					<div class="form-heading">
						<div class="row">
							<div class="col-xs-12">
								<h1>Sell a property</h1>
							</div>
						</div>
						<hr>
					</div>
					<div class="form-body">
						<div class="row">
							<div class="col-lg-12">
								<form:form id="property-listing-creation-form" role="form"
									commandName="propertyListingCreationForm" action="/sell"
									method="POST">
									<div class="form-group">
										<%@include file="../includes/alertMessage.jsp"%>
									</div>
									<h2>Basic property information</h2>
									<br>
									<div class="form-group">
										<form:select path="propertyType" class="form-control"
											tabindex="1">
											<form:options items="${propertyTypes}"
												itemLabel="displayName"></form:options>
										</form:select>
									</div>
									<div class="form-group">
										<form:input type="number" path="sellingPrice"
											class="form-control" step="any" min="0" tabindex="2"
											placeholder="Selling Price" />
									</div>
									<h2>Property location</h2>
									<br>
									<div class="form-group">
										<form:input type="number" path="address.addressNumber"
											class="form-control" min="0" tabindex="3"
											placeholder="Address" />
									</div>
									<div class="form-group">
										<form:input type="text" path="address.streetName"
											class="form-control" minlength="3" maxlength="32"
											tabindex="4" placeholder="Street Name" />
									</div>
									<div class="form-group">
										<form:input type="text" path="address.additionalAddressInfo"
											class="form-control" minlength="3" maxlength="32"
											tabindex="5" placeholder="Floor / Building / Unit (optional)" />
									</div>
									<div class="form-group">
										<form:input type="text" path="address.city"
											class="form-control" minlength="3" maxlength="32"
											tabindex="6" placeholder="City / Town" />
									</div>
									<div class="form-group">
										<form:input type="text" path="address.postCode"
											class="form-control" minlength="3" maxlength="7" tabindex="6"
											placeholder="Zip Code / Postal Code" />
									</div>
									<div class="form-group">
										<form:select path="address.region" class="form-control"
											tabindex="7">
											<form:options items="${address.region}" itemLabel="name"></form:options>
										</form:select>
									</div>
									<div class="form-group">
										<select class="form-control" tabindex="8" disabled>
											<option>Canada</option>
										</select>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="property-listing-creation-submit"
													id="property-listing-creation-submit" tabindex="8"
													class="form-control btn" value="Sell property">
											</div>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>