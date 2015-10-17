<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.spring.web.controllers.PropertyController"%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Seller Home</title>
</head>
<body>
    <c:set var="homeLinkActive" value="active" scope="request" />
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container">
        <p>
            Hello <b><sec:authentication property="principal.username" /></b>!
        </p>

        <div class="profileMenu">
            <div class="row">
                <div class="col-sm-4 menuItem">
                    <a href="<%=PropertyController.PROPERTY_CREATION_URL%>"><span class="icon glyphicon glyphicon-tag"></span>
                        <p class="itemLabel">Sell a property</p></a>
                </div>
                <div class="col-sm-4 menuItem">
                    <a href="<%=PropertyController.PROPERTY_LIST_SELLER_URL%>"> <span class="icon glyphicon glyphicon-list-alt"></span>
                        <p class="itemLabel">Edit my properties</p></a>
                </div>
                <div class="col-sm-4 menuItem">
                    <span class="icon glyphicon glyphicon-question-sign"></span>
                    <p class="itemLabel">Sample menu item</p>
                </div>
            </div>
        </div>
        <br>

        <div class="list-group">
            <c:forEach items="${properties}" var="property">
                <a href="#" class="list-group-item active"><c:out value="${property}" /></a>
            </c:forEach>
        </div>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>