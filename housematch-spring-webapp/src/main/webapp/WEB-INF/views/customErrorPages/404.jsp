<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Page Not Found</title>
</head>
<body>
    <jsp:include page="../../includes/navigationBar.jsp" />

    <div class="container">
        <h1>Oops!</h1>
        <p>Seems we couldn't find what you were asking for. Please continue browsing the site using the navigation bar.</p>
    </div>

    <%@include file="../../includes/footer.jsp"%>
</body>
</html>