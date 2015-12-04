
function GridCell(element) {
    this.element = element;
    this.itemContainerElement = element.find(".item-container").first();
}

ProgressGridCell.prototype = Object.create(GridCell.prototype);
ProgressGridCell.prototype.constructor = ProgressGridCell;
function ProgressGridCell(element) {
    this.element = element;

}

UploadGridCell.prototype = Object.create(GridCell.prototype);
UploadGridCell.prototype.constructor = UploadGridCell;
function UploadGridCell(element, onclick) {
    GridCell.call(this, element);

    this.element.click({ param1: onclick }, onclick);
}

PhotoGridCell.prototype = Object.create(GridCell.prototype);
PhotoGridCell.prototype.constructor = PhotoGridCell;
function PhotoGridCell(element, thumbnailUrl) {
    GridCell.call(this, element);
    var thisAlias = this;

    showProgressControls();
    executeAjaxCall("GET", thumbnailUrl, null, downloadCompleted, void (0));

    function downloadCompleted(data) {
        hideProgressControls();
        thisAlias.itemContainerElement.css('background-image', 'url(data:image/png;base64,' + data + ')');
    }

    function showProgressControls() {
        thisAlias.itemContainerElement.find(".progress-controls").first().show();
    }

    function hideProgressControls() {
        thisAlias.itemContainerElement.find(".progress-controls").first().hide();
    }
}


function PhotoManager(element, uploadEnabled, reviewEnabled, deleteEnabled, uploadUrl, downloadBaseUrl, thumbnailDownloadBaseUrl) {
    this.element = element;
    var uploadEnabled = uploadEnabled;
    var reviewEnabled = reviewEnabled;
    var deleteEnabled = deleteEnabled;
    var uploadUrl = uploadUrl;
    var downloadBaseUrl = downloadBaseUrl;
    var thumbnailDownloadBaseUrl = thumbnailDownloadBaseUrl;

    var contentElement = this.element.find(".content").first();
    var progressGridCell;
    var uploadGridCell;
    var photoGridCells = [];
    var photos = [];

    init();

    function init() {
        initProgressGridCell();
        initUploadGridCell();
    }

    function initProgressGridCell() {
        progressGridCell = new ProgressGridCell(contentElement.find(".progress-container").first().parent());
        progressGridCell.element.hide();
    }

    function initUploadGridCell() {
        var gridCellElement = contentElement.find(".upload-container").first().parent();
        uploadGridCell = new UploadGridCell(gridCellElement, uploadButtonClicked);
        if (!uploadEnabled) {
            uploadGridCell.element.hide();
        }
    }

    function addPhotoGridCell(relativeThumbnailUrl) {
        var photoGridCellElement = getElementTemplate("photo-grid-cell-template").clone();
        var photoGridCell = new PhotoGridCell(photoGridCellElement, thumbnailDownloadBaseUrl + relativeThumbnailUrl);

        photoGridCells.push(photoGridCell);
        photoGridCellElement.insertBefore(progressGridCell.element);
    }

    function getElementTemplate(templateClassName) {
        return element.find(".div-templates").first().find("." + templateClassName).first().children(":first");
    }

    function uploadCompleted(photoUrl) {
        hideProgressControls();
        addPhotoGridCell(photoUrl);
    }

    function uploadButtonClicked() {
        showFileSelectionDialog();
    }

    function showFileSelectionDialog() {
        var input = $(document.createElement('input'));
        input.attr("type", "file");
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
        executeAjaxFormDataUpload("POST", uploadUrl, formData, uploadCompleted, void (0));
    }

    function showProgressControls() {
        uploadGridCell.element.hide();
        progressGridCell.element.show();
    }

    function hideProgressControls() {
        progressGridCell.element.hide();
        uploadGridCell.element.show();
    }
};

