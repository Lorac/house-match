<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <%@include file="../includes/header.jsp"%>
    <title>Error Page</title>
</head>
    <body>
        <h1>Sorry, an error occurred.</h1>
        <h1>${message.message}</h1>
    </body>
</html>