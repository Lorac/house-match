$(window).ready(function () {
    initTooltips();
});

function initTooltips() {
    $('[data-toggle="tooltip"]').tooltip({
        'delay': { show: 500, hide: 250 }
    });
}

function executeAjaxCall(type, url, data, successFunction, errorFunction) {
    $.ajax({
        type: type,
        url: url,
        data: data,
        timeout: 15000,
        success: successFunction,
        error: errorFunction
    });
}

function executeAjaxFormDataUpload(type, url, formData, successFunction, errorFunction) {
    $.ajax({
        type: 'POST',
        url: url,
        data: formData,
        dataType: 'text',
        processData: false,
        contentType: false,
        cache : false,
        success: successFunction,
        error: errorFunction
    });
}