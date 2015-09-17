<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<body>
	<c:set var="homeActive" value="active" scope="request"/>
	<jsp:include page="_navigation.jsp"/>
	
    <h1>${sessionScope.username}</h1>
</body>
</html>