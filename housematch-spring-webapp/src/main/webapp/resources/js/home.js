function init() {
	showMostViewedProperties();
}

function showMostViewedProperties() {
var propertyType = $( "select#propertyTypeSelector" ).val();
$.ajax({
        type: "GET",
        url: "./mostViewedProperties",
        data: { "propertyType": propertyType },
        timeout: 15000,
        success: showMostViewedPropertiesSuccessHandler,
        error: showMostViewedPropertiesErrorHandler
    });
}

function showMostViewedPropertiesSuccessHandler(responseData, status) {
    $("#mostViewedPropertiesViewContainer").html(responseData);
}

function showMostViewedPropertiesErrorHandler(xmlHttpRequest, textStatus, errorThrown) {
    $("#mostViewedPropertiesViewContainer").html("An error is preventing the results from being diplayed: " + errorThrown);
}