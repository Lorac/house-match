$(document).ready(function () {
    $(".property-thumbnail").each(function () {
        var thisAlias = $(this);
        executeAjaxCall("GET", $(this).attr("data-thumbnail-download-url"), null, function (data) { downloadCompleted(thisAlias, data) }, void (0));
    }
    )

    function downloadCompleted(thumbnailElement, data) {
        thumbnailElement.css('background-image', 'url(data:image/png;base64,' + data + ')');
    }
});
