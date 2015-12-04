<c:choose>
    <c:when test="${not empty propertyViewModel.mainPhoto}">
        <div class="property-thumbnail dynamic-download"
            data-thumbnail-download-url="<%=PropertyPhotoController.PHOTO_THUMBNAIL_BASE_DOWNLOAD_URL%>${propertyViewModel.hashCode()}/${propertyViewModel.mainPhoto.hashCode()}">
        </div>
    </c:when>
    <c:otherwise>
        <div class="property-thumbnail generic" style="background-image: url('/resources/img/generic-house-thumbnail.png')"></div>
    </c:otherwise>
</c:choose>