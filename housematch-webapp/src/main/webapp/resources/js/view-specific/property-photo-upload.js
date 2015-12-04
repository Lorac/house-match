var photoManager

function bodyOnLoad(propertyHashCode) {
    initPhotoManager(propertyHashCode);
}

function initPhotoManager(propertyHashCode) {
    photoManager = new PhotoManager($("#property-photo-manager"), true, false, true, "/seller/uploadPropertyPhoto/" + propertyHashCode, "/seller/downloadPropertyPhoto/", "/seller/downloadPropertyPhotoThumbnail/", "/seller/deletePropertyPhoto/");
}


