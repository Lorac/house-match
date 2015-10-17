<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Home</title>
</head>
<body>


    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <p>Hello <b>${sessionScope.user.username}</b>! You are connected as a ${sessionScope.user.role.displayName}.</p>
            </c:when>
            <c:otherwise>
                <p>You are viewing this page as anonymous.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>