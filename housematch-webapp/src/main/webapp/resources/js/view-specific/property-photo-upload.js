var photoManager

function bodyOnLoad(propertyHashCode) {
    initPhotoManager(propertyHashCode);
}

function initPhotoManager(propertyHashCode) {
    var downloadBaseUrl = "/seller/downloadPropertyPhoto/" + propertyHashCode + "/";
    var thumbnailDownloadBaseUrl = "/seller/downloadPropertyPhotoThumbnail/" + propertyHashCode + "/";
    var deleteBaseUrl = "/seller/deletePropertyPhoto/" + propertyHashCode + "/";

    photoManager = new PhotoManager($("#property-photo-manager"), true, false, true, "/seller/uploadPropertyPhoto/" + propertyHashCode, downloadBaseUrl, thumbnailDownloadBaseUrl, deleteBaseUrl);
}


