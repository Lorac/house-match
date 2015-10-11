<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <%@include file="../includes/header.jsp" %>

    <!-- Custom styles for this page -->
    <link href="/resources/css/login.css" rel="stylesheet">


    <title>HouseMatch - Administrator Home</title>
</head>
<body>
<c:set var="homeLinkActive" value="active" scope="request"/>
<jsp:include page="../includes/navigationBar.jsp"/>
<div class="container">
    <p>Hello <sec:authentication property="principal.username"/> !</p>
</div>

<%@include file="../includes/footer.jsp" %>

</body>
</html>