<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.spring.web.controllers.RegistrationController;"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Login</title>
</head>

<body>
    <c:set var="loginLinkActive" value="active" scope="request" />
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-6">
                                <a href="#" class="active" id="login-form-link">Login</a>
                            </div>
                            <div class="col-xs-6">
                                <a href="<%= RegistrationController.REGISTRATION_URL %>" id="register-form-link">Register</a>
                            </div>
                        </div>
                        <hr>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">

                                <form:form action="/login" id="login-form" commandName="loginForm" method="POST">
                                    
                                    <%@include file="/WEB-INF/includes/alertMessage.jsp"%>
                                    
                                    <div class="form-group">
                                        <form:input id="username" name="username" class="form-control" minlength="3" maxlength="32"
                                            tabindex="1" placeholder="Username" path="username" />
                                    </div>
                                    <div class="form-group">
                                        <form:input id="password" name="password" type="password" minlength="3" maxlength="32"
                                            class="form-control" tabindex="2" placeholder="Password" path="password" />
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm-6 col-sm-offset-3">
                                                <input type="submit" name="login-submit" id="login-submit" tabindex="3"
                                                    class="form-control btn" value="Log In">
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

    <%@include file="/WEB-INF/includes/footer.jsp"%>
    <script src="/resources/js/login.js"></script>

</body>
</html>