<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Profile</title>
</head>
<body>
    <c:set var="registerLinkActive" value="active" scope="request"/>
    <jsp:include page="../includes/navigationBar.jsp"/>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-12">
                                <p>User Profile</p>
                            </div>
                        </div>
                        <hr>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <form:form id="profile-form" role="form" commandName="profileModificationForm" action="/modifyProfile"
                                    method="POST">
                                    <div class="form-group">
                                        <%@include file="../includes/alertMessage.jsp" %>
                                    </div>
                                    <div class="form-group">
                                        <form:input type="text" path="address.addressNumber" class="form-control" minlength="3" maxlength="32" tabindex="1"
                                            value="${user.address.addressNumber}" placeholder="Address Number" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="text" path="address.streetName" class="form-control" minlength="3" maxlength="32" tabindex="2"
                                            value="${user.address.streetName}" placeholder="Street Name" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="text" path="address.city" class="form-control" minlength="3" maxlength="32" tabindex="3"
                                            value="${user.address.city}" placeholder="City" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="text" path="address.postCode" class="form-control" minlength="3" maxlength="128" tabindex="4"
                                            value="${user.address.postCode}" placeholder="Post Code" />
                                    </div>
                                   	<div class="form-group">
                                        <form:select path="address.region" class="form-control" 
											tabindex="5">
											<form:option value="${user.address.region.name}"></form:option>
											<form:options items="${address.region}"  itemLabel="name"></form:options>
										</form:select>
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" tabindex="6" disabled>
											<option>Canada</option>
										</select>
                                    </div>
                                    <div class="form-group">
                                        <form:input type="email" path="email" class="form-control" minlength="3" maxlength="32" tabindex="7"
                                            value="${user.email}" placeholder="Email" />
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm-6 col-sm-offset-3">
                                                <input type="submit" name="profile-submit" id="profile-submit"
                                                    tabindex="4" class="form-control btn"
                                                    value="Save">
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
    <script src="/resources/js/login.js"></script>

</body>
</html>