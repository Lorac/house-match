
function GridCell(element) {
    this.element = element;

    this.getContainer = function() {
        var cont = this.element.find(".item-container").first();
        return this.element.find(".item-container").first();
    }
}

ProgressContainer.prototype = new GridCell();
ProgressContainer.prototype.constructor = ProgressContainer;
function ProgressContainer(element) {
    this.element = element;
}

UploadContainer.prototype = new GridCell();
UploadContainer.prototype.constructor = UploadContainer;
function UploadContainer(element, onclick) {
    GridCell.call(this, element);

    this.element.click({ param1: onclick }, onclick);
}


function ImageManager(element, uploadEnabled, reviewEnabled, deleteEnabled) {
    this.element = element;
    var contentElement = this.element.find(".content").first();
    var progressContainerGridCell;
    var uploadContainerGridCell;
    var imageGridCells = [];
    var images = [];
    var uploadEnabled = uploadEnabled;
    var reviewEnabled = reviewEnabled;
    var deleteEnabled = deleteEnabled;

    init();

    function init() {
        initProgressContainerGridCell();
        initUploadContainerGridCell();
    }

    function initProgressContainerGridCell() {
        progressContainerGridCell = new ProgressContainer(contentElement.find(".progress-container").first().parent());
        progressContainerGridCell.element.hide();
    }

    function initUploadContainerGridCell() {
        var containerElement = contentElement.find(".upload-container").first().parent();
        uploadContainerGridCell = new UploadContainer(containerElement, uploadButtonClicked);
        if (!uploadEnabled) {
            uploadContainerGridCell.element.hide();
        }
    }
    
    function addImageGridCell() {
        var gridCellElement = getElementTemplate("image-grid-cell-template").clone();
        var gridCell = new GridCell(gridCellElement);
        imageGridCells.push(gridCell);

        gridCellElement.insertBefore(progressContainerGridCell.element);
    }

    function getElementTemplate(templateClassName) {
        return element.find(".div-templates").first().find("." + templateClassName).first().children(":first");
    }

    function uploadCompleted() {
        progressContainerGridCell.element.hide();
        uploadContainerGridCell.element.show();
    }

    function uploadButtonClicked() {
        //uploadContainerGridCell.element.hide();
        //progressContainerGridCell.element.show();
        addImageGridCell();
    }

};

