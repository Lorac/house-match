<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${param.error != null}">
    Error
</c:if>

${param.error}
<c:if test="${not empty alertMessage.message}">
    <c:choose>
        <c:when test="${alertMessage.messageType == 'ERROR'}">
            <div class="alert alert-danger">${alertMessage.message}</div>
        </c:when>
        <c:when test="${alertMessage.messageType == 'SUCCESS'}">
            <div class="alert alert-success">${alertMessage.message}</div>
        </c:when>
    </c:choose>
</c:if>