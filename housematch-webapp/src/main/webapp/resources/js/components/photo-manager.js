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

ProgressGridCell.prototype = Object.create(GridCell.prototype);
ProgressGridCell.prototype.constructor = ProgressGridCell;
function ProgressGridCell(element) {
    GridCell.call(this, element);
    this.element = element;

}

UploadGridCell.prototype = Object.create(GridCell.prototype);
UploadGridCell.prototype.constructor = UploadGridCell;
function UploadGridCell(element, onClick) {
    GridCell.call(this, element);

    element.click(onClick);
}

PhotoGridCell.prototype = Object.create(GridCell.prototype);
PhotoGridCell.prototype.constructor = PhotoGridCell;
function PhotoGridCell(element, photoHashCode, reviewEnabled, deleteEnabled, thumbnailDownloadUrl, acceptUrl, rejectUrl) {
    GridCell.call(this, element);
    var thisAlias = this;
    this.photoHashCode = photoHashCode;
    var thumbnailDownloadUrl = thumbnailDownloadUrl;
    var acceptUrl = acceptUrl;
    var rejectUrl = rejectUrl;
    var onDeleteButtonClick;
    var onAcceptButtonClick;
    var onRejectButtonClick;
    var acceptButtonElement;
    var rejectButtonElement;
    var deleteButtonElement;

    this.show();
    showProgressControls();
    executeAjaxCall("GET", thumbnailDownloadUrl, null, downloadCompleted, void (0));

    function downloadCompleted(data) {
        hideProgressControls();
        thisAlias.itemContainerElement.css('background-image', 'url(data:image/png;base64,' + data + ')');
        initActionButtons(thisAlias.itemContainerElement.find(".action-button-container").first());
    }

    function showProgressControls() {
        thisAlias.itemContainerElement.find(".progress-controls").first().show();
    }

    function hideProgressControls() {
        thisAlias.itemContainerElement.find(".progress-controls").first().hide();
    }

    function initActionButtons(elementGroup) {
        rejectButtonElement = new ActionButton(elementGroup.find(".accept-button").first(), acceptButtonClicked);
        acceptButtonElement = new ActionButton(elementGroup.find(".reject-button").first(), rejectButtonClicked);
        deleteButtonElement = new ActionButton(elementGroup.find(".delete-button").first(), deleteButtonClicked);

        if (!reviewEnabled) {
            acceptButtonElement.hide();
            rejectButtonElement.hide();
        }
        if (!deleteEnabled) {
            deleteButtonElement.hide();
        }

        function deleteButtonClicked() {
            thisAlias.onDeleteButtonClick(thisAlias);
        }

        function acceptButtonClicked() {
            thisAlias.onAcceptButtonClick(thisAlias);
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

function PhotoManager(element, uploadEnabled, reviewEnabled, deleteEnabled, uploadUrl, defaultThumbnailDownloadBaseUrl, defaultDeleteBaseUrl) {
    this.element = element;
    var uploadEnabled = uploadEnabled;
    var reviewEnabled = reviewEnabled;
    var deleteEnabled = deleteEnabled;
    var uploadUrl = uploadUrl;
    var defaultThumbnailDownloadBaseUrl = defaultThumbnailDownloadBaseUrl;
    var defaultDeleteBaseUrl = defaultDeleteBaseUrl;

    var contentElement = this.element.find(".content").first();
    var progressGridCell;
    var uploadGridCell;
    var photoGridCells = {};

    init();

    function init() {
        initProgressGridCell();
        initUploadGridCell();
    }

    function initProgressGridCell() {
        progressGridCell = new ProgressGridCell(contentElement.find(".progress-container").first().parent());
        progressGridCell.hide();
    }

    function initUploadGridCell() {
        var gridCellElement = contentElement.find(".upload-container").first().parent();
        uploadGridCell = new UploadGridCell(gridCellElement, onUploadButtonClick);
        if (uploadEnabled) {
            uploadGridCell.show();
        }
    }

    this.addPhoto = function (photoHashCode, thumbnailDownloadUrl, deleteUrl, acceptUrl, rejectUrl) {
        addPhotoGridCell(photoHashCode, thumbnailDownloadUrl, deleteUrl, acceptUrl, rejectUrl);
    }

    function addPhotoGridCell(photoHashCode, thumbnailDownloadUrl, deleteUrl, acceptUrl, rejectUrl) {
        var photoGridCellElement = getElementTemplate("photo-grid-cell-template").clone();
        var photoGridCell = new PhotoGridCell(photoGridCellElement, photoHashCode, reviewEnabled, deleteEnabled, thumbnailDownloadUrl, deleteUrl, acceptUrl, rejectUrl);
        photoGridCell.onDeleteButtonClick = deleteButtonClicked;
        photoGridCell.onAcceptButtonClick = acceptButtonClicked;
        photoGridCell.onRejectButtonClick = rejectButtonClicked;

        photoGridCells[photoHashCode] = photoGridCell;
        photoGridCellElement.insertBefore(progressGridCell.element);
    }


    function getElementTemplate(templateClassName) {
        return element.find(".div-templates").first().find("." + templateClassName).first().children(":first");
    }

    function onUploadButtonClick() {
        showFileSelectionDialog();
    }

    function deleteButtonClicked(photoGridCell) {
        executeAjaxFormDataUpload("POST", photoGridCell.deleteUrl, null, void (0), void (0));
        photoGridCells[photoGridCell.photoHashCode].remove();
    }

    function acceptButtonClicked(photoGridCell) {
        executeAjaxFormDataUpload("POST", photoGridCell.acceptUrl, null, void (0), void (0));
        photoGridCells[photoGridCell.photoHashCode].remove();
    }

    function rejectButtonClicked(photoGridCell) {
        executeAjaxFormDataUpload("POST", photoGridCell.rejectUrl, null, void (0), void (0));
        photoGridCells[photoGridCell.photoHashCode].remove();
    }

    function showFileSelectionDialog() {
        var input = $(document.createElement('input'));
        input.attr("type", "file");
        input.attr("accept", "image/*");
        input.bind("change", onChange = function () {
            uploadPhoto(this.files[0]);
        });
        input.trigger('click');
    }

    function uploadPhoto(file) {
        showProgressControls();
        executeAjaxUploadQuery(file);
    }

    function executeAjaxUploadQuery(file) {
        var formData = new FormData();
        formData.append("file", file);
        executeAjaxFormDataUpload("POST", uploadUrl, formData, uploadCompleted, uploadFailed);
    }

    function uploadCompleted(photoHashCode) {
        hideProgressControls();
        addPhotoGridCell(photoHashCode, defaultThumbnailDownloadBaseUrl + photoHashCode, defaultDeleteBaseUrl + photoHashCode);
    }

    function uploadFailed(xmlHttpRequest, textStatus, errorThrown) {
        hideProgressControls();
    }

    function showProgressControls() {
        uploadGridCell.hide();
        progressGridCell.show();
    }

    function hideProgressControls() {
        progressGridCell.hide();
        uploadGridCell.show();
    }
};