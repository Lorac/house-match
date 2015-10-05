<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Email Reconfirm</title>
</head>

<body>
	<jsp:include page="../includes/navigationBar.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel">
					<div class="panel-body">
						<div class="alert alert-warning">Your account has not been
							activated yet. Please click the activation link that was sent to
							'${sessionScope.user.email}'. If you did not receive the
							activation link or the activation needs to be sent to another
							email address, please enter it below:</div>
						<form:form id="email-reconfirm-form" role="form"
							commandName="emailReconfirmForm" action="/emailReconfirm"
							method="POST" modes="">
							<div class="form-group">
								<%@include file="../includes/alertMessage.jsp"%>
							</div>
							<div class="form-group">
								<label for="inputEmail" class="sr-only">Email</label>
								<form:input type="email" path="email" minlength="3"
									maxlength="32" class="form-control" tabindex="1"
									placeholder="Email" />
							</div>
							<div class="form-group">
								<div class="row">
									<div class="col-sm-6 col-sm-offset-3">
										<input type="submit" name="email-reconfirm-submit"
											id="email-reconfirm-submit" tabindex="2"
											class="form-control btn" value="Resend Activation Link">
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="../includes/footer.jsp"%>
	<script src="/resources/js/login.js"></script>
</body>
</html>