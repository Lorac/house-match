<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <%@ include file="../includes/header.jsp" %>

    <!-- Custom styles for this page -->
    <link href="/resources/css/login.css" rel="stylesheet">


    <title>HouseMatch - Login</title>
</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#" class="active" id="login-form-link">Login</a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#" id="register-form-link">Register</a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form:form id="login-form" commandName="loginFormViewModel" action="login" method="POST">
                                <div class="form-group">
                                    <label for="inputEmail" class="sr-only">Email address</label>
                                    <form:input type="email" path="email" id="inputEmail" class="form-control"
                                                tabindex="1" placeholder="Email address"/>
                                </div>
                                <div class="form-group">
                                    <label for="inputPassword" class="sr-only">Password</label>
                                    <form:input type="password" path="password" id="inputPassword" class="form-control"
                                                tabindex="2" placeholder="Password"/>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" id="login-submit" tabindex="4"
                                                   class="form-control btn btn-login" value="Log In">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="text-center">
                                                <a href="http://phpoll.com/recover" tabindex="5"
                                                   class="forgot-password">Forgot Password?</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                            <form:form id="register-form" commandName="loginFormViewModel" action="login"
                                       method="POST" style="display: none;">
                                <div class="form-group">
                                    <form:input type="text" path="username" id="inputEmail" class="form-control"
                                                tabindex="1" placeholder="Username"/>
                                </div>
                                <div class="form-group">
                                    <form:input type="email" path="email" id="inputEmail" class="form-control"
                                                tabindex="2" placeholder="Email address"/>
                                </div>
                                <div class="form-group">
                                    <form:input type="password" path="password" id="inputPassword" class="form-control"
                                                tabindex="3" placeholder="Password"/>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit"
                                                   tabindex="4" class="form-control btn btn-register"
                                                   value="Register Now">
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


<!-- /container -->
<%@ include file="../includes/footer.jsp" %>
<script src="/resources/js/login.js"></script>

</body>
</html>