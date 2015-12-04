<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.LoginController"%>
<%@page import="ca.ulaval.glo4003.housematch.web.viewmodels.RegistrationFormViewModel"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<title>HouseMatch - Register</title>
</head>

<body>
    <c:set var="registerLinkActive" value="active" scope="request" />
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-6">
                                <a href="<%=LoginController.LOGIN_URL%>" id="login-form-link">Login</a>
                            </div>
                            <div class="col-xs-6">
                                <a href="#" class="active" id="register-form-link">Register</a>
                            </div>
                        </div>
                        <hr>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <form:form id="register-form" role="form" modelAttribute="<%=RegistrationFormViewModel.NAME%>"
                                    action="/register" method="POST">

                                    <%@include file="/WEB-INF/includes/alertMessage.jsp"%>

                                    <div class="form-group">
                                        <form:input type="text" path="username" class="form-control" minlength="3" maxlength="32"
                                            tabindex="1" placeholder="Username" autofocus="true" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="email" path="email" class="form-control" minlength="3" maxlength="128"
                                            tabindex="2" placeholder="Email address" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="password" path="password" class="form-control" minlength="8" maxlength="32"
                                            tabindex="3" placeholder="Password" />
                                    </div>
                                    <div class="form-group">
                                        <label>Register as:</label>
                                        <form:select path="role" class="form-control" tabindex="4">
                                            <form:options items="${publiclyRegistrableRoles}" itemLabel="displayName"></form:options>
                                        </form:select>
                                    </div>
                                    <div class="form-group">
                                        <div class="row center">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4"
                                                class="btn btn-primary btn-lg" value="Register Now">
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

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>