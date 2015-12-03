$(window).ready(function () {
    initTooltips();
});

$(window).resize(function () {
    adjustThumbnailsAspectRatio();
});

function adjustThumbnailsAspectRatio() {
    $(".image-manager .grid-cell").height($(".image-manager .grid-cell").width() * 0.75);
}

function initTooltips() {
    $('[data-toggle="tooltip"]').tooltip({
        'delay': { show: 500, hide: 250 }
    });
}