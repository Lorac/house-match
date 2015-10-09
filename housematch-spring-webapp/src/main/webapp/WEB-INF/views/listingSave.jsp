<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@include file="../includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">


<title>HouseMatch - Sell Confirmation</title>
</head>
<body>
	<jsp:include page="../includes/navigationBar.jsp" />
	
	<div class="container">
		<h1>Property Listing Details Saved</h1>
		<p>Congratulations! All your details have saved successfully. You may continue to explore the site via the navigation bar.</p>
	</div>
	
	<%@include file="../includes/footer.jsp"%>
</body>
</html>