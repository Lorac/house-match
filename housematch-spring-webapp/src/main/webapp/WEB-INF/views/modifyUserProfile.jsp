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
                                        <form:input type="text" path="address" class="form-control" minlength="3" maxlength="32" tabindex="1"
                                            value="${user.address}" placeholder="Address" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="text" path="postCode" class="form-control" minlength="3" maxlength="128" tabindex="2"
                                            value="${user.postCode}" placeholder="Post Code" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="text" path="city" class="form-control" minlength="3" maxlength="32" tabindex="3"
                                            value="${user.city}" placeholder="City" />
                                    </div>
                                   	<div class="form-group">
                                        <form:input type="text" path="region" class="form-control" minlength="3" maxlength="32" tabindex="3"
                                            value="${user.region}" placeholder="Region" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="text" path="country" class="form-control" minlength="3" maxlength="32" tabindex="3"
                                            value="${user.country}" placeholder="Country" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="email" path="email" class="form-control" minlength="3" maxlength="32" tabindex="3"
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