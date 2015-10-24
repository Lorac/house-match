<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.spring.web.controllers.UserProfileController"%>
<%@page import="ca.ulaval.glo4003.housematch.spring.web.viewmodels.ContactInformationFormViewModel"%>

<% pageContext.setAttribute("regions", ca.ulaval.glo4003.housematch.domain.address.Region.values()); %>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Update Contact Information</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <h1 class="center">Update Contact Information</h1>
        <form:form id="profile-form" class="form-horizontal" role="form" modelAttribute="<%= ContactInformationFormViewModel.NAME %>"
            action="<%= UserProfileController.CONTACT_INFO_UPDATE_URL %>" method="POST">
            
            <%@include file="/WEB-INF/includes/alertMessage.jsp"%>
            
            <div class="form-group">
                <label class="control-label col-sm-2">Street Number:</label>
                <div class="col-sm-10">
                    <form:input type="number" path="address.streetNumber" class="form-control" min="0" max="9999999" tabindex="1"
                        value="${user.address.streetNumber}" placeholder="Street Number" autofocus="true"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Street Name:</label>
                <div class="col-sm-10">
                    <form:input type="text" path="address.streetName" class="form-control" minlength="3" maxlength="256" tabindex="2"
                        value="${user.address.streetName}" placeholder="Street Name" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">City / Town:</label>
                <div class="col-sm-10">
                    <form:input type="text" path="address.town" class="form-control" minlength="3" maxlength="256" tabindex="3"
                        value="${user.address.town}" placeholder="City / Town" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Zip / Postal Code:</label>
                <div class="col-sm-10">
                    <form:input type="text" path="address.postCode" class="form-control" minlength="3" maxlength="7" tabindex="4"
                        value="${user.address.postCode}" placeholder="Zip / Postal Code" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Region:</label>
                <div class="col-sm-10">
                    <form:select path="address.region" class="form-control" tabindex="5">
                        <c:forEach var="region" items="${regions}">
                            <option value="${region}" ${region == user.address.region ? 'selected' : ''}>${region.name}</option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Country:</label>
                <div class="col-sm-10">
                    <select class="form-control" tabindex="6" disabled>
                        <option>Canada</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Email:</label>
                <div class="col-sm-10">
                    <form:input type="email" path="email" class="form-control" minlength="3" maxlength="32" tabindex="7"
                        value="${user.email}" placeholder="Email" />
                </div>
            </div>
            <div class="form-group">
                <div class="row center">
                    <input type="submit" name="profile-submit" id="profile-submit" tabindex="4" class="btn btn-primary btn-lg" value="Update">
                </div>
            </div>
        </form:form>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>

</body>
</html>