<c:choose>
    <c:when test="${not empty propertyViewModel.mainPhotoViewModel}">
        <div class="property-thumbnail"
            style='background-image:url("<%=PropertyPhotoController.PHOTO_THUMBNAIL_BASE_URL%>${propertyViewModel.mainPhotoViewModel.photoHashCode}")'>
        </div>
    </c:when>
    <c:otherwise>
        <div class="property-thumbnail generic" style="background-image: url('/resources/img/generic-house-thumbnail.png')"></div>
    </c:otherwise>
</c:choose>