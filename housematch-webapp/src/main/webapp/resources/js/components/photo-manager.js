
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
function PhotoGridCell(element, thumbnailUrl, photoHashCode, reviewEnabled, deleteEnabled, onDeleteButtonClick) {
    GridCell.call(this, element);
    var thisAlias = this;
    var photoHashCode = photoHashCode;
    var deleteButtonElement = new ActionButton(element.find(".delete-button").first());
    var acceptButtonElement = new ActionButton(element.find(".accept-button").first());
    var rejectButtonElement = new ActionButton(element.find(".reject-button").first());

    showProgressControls();
    executeAjaxCall("GET", thumbnailUrl, null, downloadCompleted, void (0));

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
        var deleteButtonElement = new ActionButton(elementGroup.find(".delete-button").first(), function () { onDeleteButtonClick(photoHashCode); });
        var acceptButtonElement = new ActionButton(elementGroup.find(".accept-button").first(), void (0));
        var rejectButtonElement = new ActionButton(elementGroup.find(".reject-button").first(), void (0));

        if (!reviewEnabled) {
            acceptButtonElement.hide();
            rejectButtonElement.hide();
        }
        if (!deleteEnabled) {
            deleteButtonElement.hide();
        }
    }
}

ActionButton.prototype = Object.create(Element.prototype);
ActionButton.prototype.constructor = Element;
function ActionButton(element, onClick) {
    this.element = element;
    this.element.click(onClick);
}

function PhotoManager(element, uploadEnabled, reviewEnabled, deleteEnabled, uploadUrl, downloadBaseUrl, thumbnailDownloadBaseUrl, deleteBaseUrl) {
    this.element = element;
    var uploadEnabled = uploadEnabled;
    var reviewEnabled = reviewEnabled;
    var deleteEnabled = deleteEnabled;
    var uploadUrl = uploadUrl;
    var downloadBaseUrl = downloadBaseUrl;
    var thumbnailDownloadBaseUrl = thumbnailDownloadBaseUrl;
    var deleteBaseUrl = deleteBaseUrl;

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
        if (!uploadEnabled) {
            uploadGridCell.hide();
        }
    }

    function addPhotoGridCell(photoHashCode) {
        var photoGridCellElement = getElementTemplate("photo-grid-cell-template").clone();
        var photoGridCell = new PhotoGridCell(photoGridCellElement, thumbnailDownloadBaseUrl + photoHashCode, photoHashCode, reviewEnabled, deleteEnabled, onDeleteButtonClick);

        photoGridCells[photoHashCode] = photoGridCell;
        photoGridCellElement.insertBefore(progressGridCell.element);
    }


    function getElementTemplate(templateClassName) {
        return element.find(".div-templates").first().find("." + templateClassName).first().children(":first");
    }

    function onUploadButtonClick() {
        showFileSelectionDialog();
    }

    function onDeleteButtonClick(photoHashCode) {
        executeAjaxFormDataUpload("POST", deleteBaseUrl + photoHashCode, null, void (0), void (0));
        photoGridCells[photoHashCode].remove();
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
        addPhotoGridCell(photoHashCode);
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

