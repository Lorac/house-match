<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<%@ include file="../includes/header.jsp" %>
		
		<title>HouseMatch - Register</title>
	</head>

	<body>
		<div class="container">
			<h2>Register</h2>
			<form:form role="form" method="POST" action="/users/registerUser" modelAttribute="user">
				<div class="form-group">
					<c:if test="${not empty message}">
							<div class="alert alert-danger" style="margin-top: 10px;" role="alert">${message.message}</div>
					</c:if>
				</div>
				<div class="form-group">
					<form:label path="email">Email</form:label>
					<form:input class="form-control" type="email" path="email" value="${user.email}" required="required" />
				</div>
				<div class="form-group">
					<form:label path="password">Password</form:label>
					<form:input class="form-control" path="password" value="${user.password}" required="required" />
				</div>

				<div class="form-group">
					<b>Role</b>
					<td>
						<form:select class="form-control" path="role">
						<form:options items="${user.registerableRoles}"></form:options>
						</form:select>
					</td>
				</div>

				<div class="form-group">
					<input type="submit" value="Register" class="btn btn-primary"></input>
				</div>
			</form:form>
		</div>

		<%@include file="../includes/footer.jsp"%>

	</body>
</html>