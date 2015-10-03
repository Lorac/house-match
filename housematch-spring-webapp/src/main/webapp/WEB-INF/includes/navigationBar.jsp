<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand">House Match</a>
		<ul class="nav navbar-nav navbar-right">
			<li class="${homeLinkActive}"><a href="/">Home</a></li>
			<c:if test="${empty sessionScope.user}">
				<li class="${loginLinkActive}"><a href="/login">Login</a></li>
				<li class="${registerLinkActive}"><a href="/register">Register</a></li>
			</c:if>
			<c:if test="${sessionScope.user.role == 'BUYER'}">
                <li><a href="/">${sessionScope.user.username}</a></li>
            </c:if>
			<c:if test="${not empty sessionScope.user}">
				<li><a href="/logout">Logout</a></li>
			</c:if>
		</ul>
	</div>
</nav>