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
        success: showMostPopularPropertiesSuccessHandler
    });
}

function showMostPopularPropertiesSuccessHandler(responseData, status) {
    $("#mostPopularPropertiesViewContainer").html(responseData);
}