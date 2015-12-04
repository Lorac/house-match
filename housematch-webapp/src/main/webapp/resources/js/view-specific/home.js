function init(userRole) {
	loadMostPopularProperties();
	loadStatistics();
}

function adminInit(userRole) {
	loadStatistics();
}

function loadMostPopularProperties() {
    var propertyType = $("select#propertyTypeSelector").val();
    executeAjaxCall("GET", "./mostPopularProperties", { "propertyType": propertyType }, mostPopularPropertiesLoadSuccessHandler, mostPopularPropertiesLoadErrorHandler);
}

function mostPopularPropertiesLoadSuccessHandler(responseData, status) {
    $("#mostPopularPropertiesViewContainer").html(responseData);
}

function mostPopularPropertiesLoadErrorHandler(xmlHttpRequest, textStatus, errorThrown) {
    $("#mostPopularPropertiesViewContainer").html("An error is preventing the results from being diplayed: " + errorThrown);
}

function loadStatistics() {
$.ajax({
        type: "GET",
        url: "./statistics",
        timeout: 15000,
        success: statisticsLoadSuccessHandler,
        error: statisticsLoadErrorHandler
    });
}

function statisticsLoadSuccessHandler(responseData, status) {
    $("#statisticsContainer").html(responseData);
}

function statisticsLoadErrorHandler(xmlHttpRequest, textStatus, errorThrown) {
    $("#statisticsContainer").html("An error is preventing the results from being diplayed: " + errorThrown);
}