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
                <li class="${homeLinkActive}"><sec:authorize access="hasRole('Administrator')">
                        <a href="/adminHome">Home</a>
                    </sec:authorize> <sec:authorize access="hasRole('Seller')">
                        <a href="/sellerHome">Home</a>
                    </sec:authorize> <sec:authorize access="hasRole('Buyer')">
                        <a href="/buyerHome">Home</a>
                    </sec:authorize></li>
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