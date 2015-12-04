function createPhotoManager(propertyHashCode) {
    var downloadBaseUrl = "/seller/downloadPropertyPhoto/" + propertyHashCode + "/";
    var thumbnailDownloadBaseUrl = "/seller/downloadPropertyPhotoThumbnail/" + propertyHashCode + "/";
    var deleteBaseUrl = "/seller/deletePropertyPhoto/" + propertyHashCode + "/";

    return new PhotoManager($("#property-photo-manager"), true, false, true, "/seller/uploadPropertyPhoto/" + propertyHashCode, downloadBaseUrl, thumbnailDownloadBaseUrl, deleteBaseUrl);
}


