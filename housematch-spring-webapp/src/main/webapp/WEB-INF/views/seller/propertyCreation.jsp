<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.spring.web.controllers.PropertyController"%>
<%@page import="ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Sell</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <h1 class="center">Sell a property</h1>

        <form:form id="property-creation-form" class="form-horizontal" role="form" modelAttribute="<%=PropertyViewModel.NAME%>"
            action="<%=PropertyController.PROPERTY_CREATION_URL%>" method="POST">

            <%@include file="/WEB-INF/includes/alertMessage.jsp"%>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Basic Property Informatiom</h3>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="control-label col-sm-2">Property Type:</label>
                        <div class="col-sm-10">
                            <form:select path="propertyType" class="form-control" tabindex="1">
                                <form:options items="${propertyTypes}" itemLabel="displayName"></form:options>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Selling Price:</label>
                        <div class="col-sm-10">
                            <form:input type="number" path="sellingPrice" class="form-control" step="any" min="0" tabindex="2"
                                placeholder="Selling Price" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Property Location</h3>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="control-label col-sm-2">Street Number:</label>
                        <div class="col-sm-10">
                            <form:input type="number" path="address.streetNumber" class="form-control" min="0" tabindex="3"
                                placeholder="Street Number" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Street Name:</label>
                        <div class="col-sm-10">
                            <form:input type="text" path="address.streetName" class="form-control" minlength="3" maxlength="32" tabindex="4"
                                placeholder="Street Name" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Floor / Building / Unit (optional):</label>
                        <div class="col-sm-10">
                            <form:input type="text" path="address.additionalAddressInfo" class="form-control" minlength="3" maxlength="32"
                                tabindex="5" placeholder="Floor / Building / Unit (optional)" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">City / Town:</label>
                        <div class="col-sm-10">
                            <form:input type="text" path="address.town" class="form-control" minlength="3" maxlength="32" tabindex="6"
                                placeholder="City / Town" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Zip / Postal Code:</label>
                        <div class="col-sm-10">
                            <form:input type="text" path="address.postCode" class="form-control" minlength="3" maxlength="7" tabindex="6"
                                placeholder="Zip / Postal Code" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">State / Province:</label>
                        <div class="col-sm-10">
                            <form:select path="address.region" class="form-control" tabindex="7">
                                <form:options items="${address.region}" itemLabel="name"></form:options>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Country:</label>
                        <div class="col-sm-10">
                            <select class="form-control" tabindex="8" disabled>
                                <option>Canada</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row center">
                    <input type="submit" name="property-creation-submit" id="property-creation-submit" tabindex="8"
                        class="btn btn-primary btn-lg" value="Sell Property">
                </div>
            </div>
        </form:form>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>

</body>
</html>