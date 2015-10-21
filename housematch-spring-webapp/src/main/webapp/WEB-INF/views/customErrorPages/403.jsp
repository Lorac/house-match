<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Access Forbidden</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />

    <div class="container">
        <h1>Watch where you're going!</h1>
        <p>Seems like you're not allowed to view this page.</p>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>