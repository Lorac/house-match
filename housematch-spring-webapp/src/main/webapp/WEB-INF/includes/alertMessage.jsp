<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${param.error != null}">
    <div class="alert alert-danger">
        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
    </div>
</c:if>
<c:if test="${param.success != null}">
    <div class="alert alert-danger">Success</div>
</c:if>