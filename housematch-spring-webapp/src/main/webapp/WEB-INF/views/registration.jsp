<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Register</title>
</head>

<body>
    <c:set var="registerLinkActive" value="active" scope="request" />
    <jsp:include page="../includes/navigationBar.jsp" />
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-6">
                                <a href="#" id="login-form-link" onclick="javascript: window.location.href = '/login'">Login</a>
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
                                <form:form id="register-form" role="form" commandName="registrationForm" action="/register" method="POST">
                                    <div class="form-group">
                                        <%@include file="../includes/alertMessage.jsp"%>
                                    </div>
                                    <div class="form-group">
                                        <form:input type="text" path="username" class="form-control" minlength="3" maxlength="32"
                                            tabindex="1" placeholder="Username" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="email" path="email" class="form-control" minlength="3" maxlength="128"
                                            tabindex="2" placeholder="Email address" />
                                    </div>
                                    <div class="form-group">
                                        <form:input type="password" path="password" class="form-control" minlength="3" maxlength="32"
                                            tabindex="3" placeholder="Password" />
                                    </div>
                                    <div class="form-group">
                                        <label>Register as:</label>
                                        <form:select path="role" class="form-control" tabindex="4">
                                            <form:options items="${publiclyRegistrableRoles}" itemLabel="displayName"></form:options>
                                        </form:select>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm-6 col-sm-offset-3">
                                                <input type="submit" name="register-submit" id="register-submit" tabindex="4"
                                                    class="form-control btn" value="Register Now">
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