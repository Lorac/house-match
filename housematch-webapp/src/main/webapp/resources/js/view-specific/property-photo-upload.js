var photoManager

function bodyOnLoad(propertyHashCode) {
    initPhotoManager(propertyHashCode);
}

function initPhotoManager(propertyHashCode) {
    photoManager = new PhotoManager($("#property-photo-manager"), true, true, true, "/seller/uploadPropertyPhoto/" + propertyHashCode);
}


