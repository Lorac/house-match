<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty message.message}">
	<c:choose>
		<c:when test="${message.messageType == 'ERROR'}">
			<div class="alert alert-danger">${message.message}</div>
		</c:when>
		<c:when test="${message.messageType == 'SUCCESS'}">
			<div class="alert alert-success">${message.message}</div>
		</c:when>
	</c:choose>
</c:if>