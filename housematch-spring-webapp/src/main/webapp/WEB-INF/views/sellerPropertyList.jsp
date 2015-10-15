<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Home</title>
</head>
<body>


    <jsp:include page="../includes/navigationBar.jsp" />
    <div class="container">
    	<c:choose>
	    	<c:when test="${not empty user.properties}">
	    	<ul>
				<c:forEach var="property" items="${user.properties}">
					<a href="/updatePropertyDetails/${property.hashCode}"><li>${property.sellingPrice}</li></a>
				</c:forEach>
	    	</ul>
	    	</c:when>
	    	<c:otherwise>
	    		<p>No property to edit</p>
	    	</c:otherwise>
	    </c:choose>
    </div>

    <%@include file="../includes/footer.jsp"%>
</body>
</html>