<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false"
                aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">HouseMatch</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                
                <c:if test="${empty homeLinkHidden or homeLinkHidden eq false}">
                    <li class="${homeLinkActive}"><a href="/">Home</a></li>
                </c:if>
                <c:if test="${empty sessionScope.user}">
                    <li class="${loginLinkActive}"><a href="/login">Login</a></li>
                    <li class="${registerLinkActive}"><a href="/register">Register</a></li>
                </c:if>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="/logout">Logout</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>