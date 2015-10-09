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
	
	<h1>Edit Listing Details</h1>
	<div class="container">
		<p>Your property has been successfully put on for sale and is now visible to buyers. <a href="../">Go back to your profile</a>.</p>
	</div>
	
	<%@include file="../includes/footer.jsp"%>
</body>
</html>