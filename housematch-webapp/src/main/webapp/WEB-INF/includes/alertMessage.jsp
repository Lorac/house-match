<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
    <div class="alert alert-danger">
        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
        <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
    </div>
</c:if>

<c:if test="${not empty alertMessageViewModel.message}">
	<c:choose>
		<c:when test="${alertMessageViewModel.messageType == 'ERROR'}">
			<div class="alert alert-danger">${alertMessageViewModel.message}</div>
		</c:when>
		<c:when test="${alertMessageViewModel.messageType == 'SUCCESS'}">
			<div class="alert alert-success">${alertMessageViewModel.message}</div>
		</c:when>
	</c:choose>
</c:if>