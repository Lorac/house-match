function Element(element) {
    this.element = element;
}

Element.prototype.hide = function () {
    this.element.hide();
}

Element.prototype.show = function () {
    this.element.show();
}

Element.prototype.remove = function () {
    this.element.remove();
}

GridCell.prototype = Object.create(Element.prototype);
GridCell.prototype.constructor = Element;
function GridCell(element) {
    Element.call(this, element);
    this.itemContainerElement = element.find(".item-container").first();
}

UploadGridCell.prototype = Object.create(GridCell.prototype);
UploadGridCell.prototype.constructor = UploadGridCell;
function UploadGridCell(element, onClick) {
    GridCell.call(this, element);

    element.click(onClick);
}

PhotoGridCell.prototype = Object.create(GridCell.prototype);
PhotoGridCell.prototype.constructor = PhotoGridCell;
function PhotoGridCell(element, reviewEnabled, deleteEnabled) {
    GridCell.call(this, element);
    var thisAlias = this;
	var reviewEnabled = reviewEnabled;
	var deleteEnabled = deleteEnabled;
    var onDeleteButtonClick;
    var onApproveButtonClick;
    var onRejectButtonClick;
    var approveButtonElement;
    var rejectButtonElement;
    var deleteButtonElement;
    var progressControlsElement = this.itemContainerElement.find(".progress-controls").first();

    init();
    
    function init() {
    	thisAlias.show();
    }
    
    this.showProgress = function () {
    	progressControlsElement.show();
    }
    
    this.showPhoto = function (photoHashCode, thumbnailUrl, deleteUrl, approveUrl, rejectUrl) {
    	progressControlsElement.hide();
    	
    	thisAlias.photoHashCode = photoHashCode;
        thisAlias.approveUrl = approveUrl;
        thisAlias.rejectUrl = rejectUrl;
        thisAlias.deleteUrl = deleteUrl;
        thisAlias.thumbnailUrl = thumbnailUrl;
        
    	setBackgroundImage();
        initActionButtons(thisAlias.itemContainerElement.find(".action-button-container").first());
    }
    
    function setBackgroundImage() {
        thisAlias.itemContainerElement.css('background-image', 'url(' + thisAlias.thumbnailUrl + ')');
    }
    
    function initActionButtons(elementGroup) {
    	elementGroup.show();
        rejectButtonElement = new ActionButton(elementGroup.find(".approve-button").first(), approveButtonClicked);
        approveButtonElement = new ActionButton(elementGroup.find(".reject-button").first(), rejectButtonClicked);
        deleteButtonElement = new ActionButton(elementGroup.find(".delete-button").first(), deleteButtonClicked);

        if (reviewEnabled) {
            approveButtonElement.show();
            rejectButtonElement.show();
        }
        if (deleteEnabled) {
            deleteButtonElement.show();
        }

        function deleteButtonClicked() {
            thisAlias.onDeleteButtonClick(thisAlias);
        }

        function approveButtonClicked() {
            thisAlias.onApproveButtonClick(thisAlias);
        }

        function rejectButtonClicked() {
            thisAlias.onRejectButtonClick(thisAlias);
        }
    }
}

ActionButton.prototype = Object.create(Element.prototype);
ActionButton.prototype.constructor = Element;
function ActionButton(element, onClick) {
    this.element = element;
    this.element.click(onClick);
}

function PhotoManager(element, uploadEnabled, reviewEnabled, deleteEnabled, uploadUrl, defaultThumbnailBaseUrl, defaultDeleteBaseUrl) {
    this.element = element;
    var uploadEnabled = uploadEnabled;
    var reviewEnabled = reviewEnabled;
    var deleteEnabled = deleteEnabled;
    var uploadUrl = uploadUrl;
    var defaultThumbnailBaseUrl = defaultThumbnailBaseUrl;
    var defaultDeleteBaseUrl = defaultDeleteBaseUrl;

    var contentElement = this.element.find(".content").first();
    var progressGridCell;
    var uploadGridCell;
    var photoGridCells = [];

    init();

    function init() {
        initUploadGridCell();
    }

    function initUploadGridCell() {
        var gridCellElement = contentElement.find(".upload-container").first().parent();
        uploadGridCell = new UploadGridCell(gridCellElement, onUploadButtonClick);
        if (uploadEnabled) {
            uploadGridCell.show();
        }
    }

    this.addPhoto = function (photoHashCode, thumbnailUrl, deleteUrl, approveUrl, rejectUrl) {
        var photoGridCell = addPhotoGridCell(deleteUrl, approveUrl, rejectUrl);
        photoGridCell.showPhoto(photoHashCode, thumbnailUrl, deleteUrl, approveUrl, rejectUrl);
    }

    function addPhotoGridCell() {
        var photoGridCellElement = getElementTemplate("photo-grid-cell-template").clone();
        var photoGridCell = new PhotoGridCell(photoGridCellElement, reviewEnabled, deleteEnabled);
        photoGridCell.onDeleteButtonClick = deleteButtonClicked;
        photoGridCell.onApproveButtonClick = approveButtonClicked;
        photoGridCell.onRejectButtonClick = rejectButtonClicked;

        photoGridCells.push(photoGridCell);
        photoGridCellElement.insertBefore(uploadGridCell.element);
        return photoGridCell;
    }

    function removePhotoGridCell(photoGridCell) {
	photoGridCell.hide();
    	photoGridCells.splice(photoGridCells.indexOf(photoGridCell), 1);
        if (photoGridCells.length == 0 && !uploadEnabled) {
            showEmptyPhotoListLabel();
        }
    }

    function showEmptyPhotoListLabel() {
        element.find(".empty-photo-list-label").first().show();
    }

    function getElementTemplate(templateClassName) {
        return element.find(".div-templates").first().find("." + templateClassName).first().children(":first");
    }

    function onUploadButtonClick() {
        showFileSelectionDialog();
    }

    function deleteButtonClicked(photoGridCell) {
        executeAjaxFormDataUpload("POST", photoGridCell.deleteUrl, null, void (0), void (0));
        removePhotoGridCell(photoGridCell);
    }

    function approveButtonClicked(photoGridCell) {
        executeAjaxFormDataUpload("POST", photoGridCell.approveUrl, null, void (0), void (0));
        removePhotoGridCell(photoGridCell);
    }

    function rejectButtonClicked(photoGridCell) {
        executeAjaxFormDataUpload("POST", photoGridCell.rejectUrl, null, void (0), void (0));
        removePhotoGridCell(photoGridCell);
    }

    function showFileSelectionDialog() {
        var input = $(document.createElement('input'));
        input.attr("type", "file");
        input.attr("multiple", "");
        input.attr("approve", "image/*");
        input.bind("change", onChange = function () {
            uploadPhotos(this.files);
        });
        input.trigger('click');
    }

    function uploadPhotos(files) {
    	for (var i = 0; i < files.length; i++) {
    		uploadPhoto(files[i]);
    	}
    }
    
    function uploadPhoto(file) {
    	var photoGridCell = addPhotoGridCell(this.deleteUrl, this.approveUrl, this.rejectUrl);
    	photoGridCell.showProgress();
    	executeAjaxUploadQuery(file, photoGridCell);
    }

    function executeAjaxUploadQuery(file, photoGridCell) {
        var formData = new FormData();
        formData.append("file", file);
        executeAjaxFormDataUpload("POST", uploadUrl, formData, function(photoHashCode) {uploadCompleted(photoHashCode, photoGridCell);},  function(xmlHttpRequest, textStatus, errorThrown) {uploadFailed(photoGridCell, errorThrown);});
    }

    function uploadCompleted(photoHashCode, photoGridCell) {
        photoGridCell.showPhoto(photoHashCode, defaultThumbnailBaseUrl + photoHashCode, defaultDeleteBaseUrl + photoHashCode);
    }
    
    function uploadFailed(photoGridCell, errorThrown) {
    	removePhotoGridCell(photoGridCell);
    	alert("Upload failed: " + errorThrown);
    }
};