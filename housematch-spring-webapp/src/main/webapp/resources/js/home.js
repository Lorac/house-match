function init() {
	showMostPopularProperties();
}

function showMostPopularProperties() {
var propertyType = $( "select#propertyTypeSelector" ).val();
$.ajax({
        type: "GET",
        url: "./mostPopularProperties",
        data: { "propertyType": propertyType },
        timeout: 15000,
        success: showMostPopularPropertiesSuccessHandler,
        error: showMostPopularPropertiesErrorHandler
    });
}

function showMostPopularPropertiesSuccessHandler(responseData, status) {
    $("#mostPopularPropertiesViewContainer").html(responseData);
}

function showMostPopularPropertiesErrorHandler(xmlHttpRequest, textStatus, errorThrown) {
    $("#mostPopularPropertiesViewContainer").html("An error is preventing the results from being diplayed: " + errorThrown);
}