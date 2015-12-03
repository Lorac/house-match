$(window).ready(function () {
    initTooltips();
});

function initTooltips() {
    $('[data-toggle="tooltip"]').tooltip({
        'delay': { show: 500, hide: 250 }
    });
}