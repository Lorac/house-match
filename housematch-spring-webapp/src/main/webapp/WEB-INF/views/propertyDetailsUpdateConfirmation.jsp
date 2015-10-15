<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Property Update Confirmation</title>
</head>
<body>
    <jsp:include page="../includes/navigationBar.jsp" />

    <div class="container">
        <p>
            Your property has been successfully updated and is visible to other buyers. <a href="../">Go back to your profile</a>.
        </p>
    </div>

    <%@include file="../includes/footer.jsp"%>
</body>
</html>