<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="ca.ulaval.glo4003.housematch.web.controllers.UserProfileController"%>
<%@page import="ca.ulaval.glo4003.housematch.web.viewmodels.NotificationSettingsFormViewModel"%>

<%
    pageContext.setAttribute("notificationIntervals",
            ca.ulaval.glo4003.housematch.domain.notification.NotificationInterval.values());
%>

<html>
<head>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- Custom styles for this page -->
<link href="/resources/css/login.css" rel="stylesheet">



<title>HouseMatch - Notification Settings</title>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigationBar.jsp" />
    <div class="container medium">
        <h1 class="center">Notification Settings</h1>

        <form:form id="property-creation-form" class="form-horizontal" role="form"
            modelAttribute="<%=NotificationSettingsFormViewModel.NAME%>" action="<%=UserProfileController.NOTIFICATION_SETTINGS_URL%>"
            method="POST">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Email Notifications</h3>
                </div>

                <div class="panel-body">
                    <p>Notify me when:</p>

                    <div class="form-group">
                        <label class="control-label col-sm-6">A new property has been put up for sale:</label>
                        <div class="col-sm-6">
                            <form:select path="propertyPutUpForSaleNotificationInterval" class="form-control" tabindex="1">
                                <c:forEach var="notificationInterval" items="${notificationIntervals}">
                                    <option value="${notificationInterval}"
                                        ${notificationInterval == notificationSettingsFormViewModel.propertyPutUpForSaleNotificationInterval ? 'selected' : ''}>${notificationInterval.displayName}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-6">One of my favorite property has changed:</label>
                        <div class="col-sm-6">
                            <form:select path="favoritePropertyModificationNotificationInterval" class="form-control" tabindex="1">
                                <c:forEach var="notificationInterval" items="${notificationIntervals}">
                                    <option value="${notificationInterval}"
                                        ${notificationInterval == notificationSettingsFormViewModel.favoritePropertyModificationNotificationInterval ? 'selected' : ''}>${notificationInterval.displayName}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row center">
                    <input type="submit" name="property-creation-submit" id="property-creation-submit" tabindex="8"
                        class="btn btn-primary btn-lg" value="Update Settings">
                </div>
            </div>

        </form:form>
    </div>

    <%@include file="/WEB-INF/includes/footer.jsp"%>
    <script src="/resources/js/propertyDetails.js"></script>
</body>
</html>