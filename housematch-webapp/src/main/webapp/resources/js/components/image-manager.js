
function GridCell(element) {
    this.element = element;
}

ProgressContainer.prototype = new GridCell();
ProgressContainer.prototype.constructor = ProgressContainer;
function ProgressContainer(element) {
    this.element = element;
}

UploadContainer.prototype = new GridCell();
UploadContainer.prototype.constructor = UploadContainer;
function UploadContainer(element) {
    this.element = element;
    var onclick;

    function onclick(method) {
        this.onclick = onclick;
    }

    element.click(function () {
        this.onclick();
    });
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
        progressContainerGridCell = new ProgressContainer(contentElement.find(".progress-container").first());
        progressContainerGridCell.element.hide();
    }

    function initUploadContainerGridCell() {
        uploadContainerGridCell = new UploadContainer(contentElement.find(".upload-container").first());
        uploadContainerGridCell.onclick = this.uploadButtonClicked;
        if (!uploadEnabled) {
            uploadContainerGridCell.element.hide();
        }
    }
    
    function addImageGridCell() {
        var gridCellElement = getElementTemplate("image-grid-cell-template")
        var gridCell = new GridCell(gridCellElement);
        this.imageGridCells.push(gridCell);

        this.element.append(gridCellElement);
    }

    function getElementTemplate(templateClassName) {
        return this.element.find(".div-templates").first().find("." + templateClassName).first().children(":first");
    }

    function uploadButtonClicked() {
        uploadContainerGridCell.element.hide();
        progressContainerGridCell.element.show();
    }

    function uploadCompleted() {
        progressContainerGridCell.element.hide();
        uploadContainerGridCell.element.show();
    }

};

