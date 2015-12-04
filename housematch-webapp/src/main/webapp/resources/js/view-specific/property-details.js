$(document).ready(function () {
    $('#addPropertiesToFavorite').click('click', function () {
        var hashCode = $(this).attr("value");
        addPropertiesToFavorite(hashCode);
        return false;
    });
});

function addPropertiesToFavorite(propertyHashCode) {
    executeAjaxCall("POST", "/buyer/addPropertyToFavorites/" + propertyHashCode, null, favoritePropertyCreationSuccessHandler, favoritePropertyCreationErrorHandler);
}

function favoritePropertyCreationSuccessHandler(responseData, status) {
	showFavoritePropertyCreationAlert();
    $('#addPropertiesToFavorite').addClass('disabled');
}

function favoritePropertyCreationErrorHandler(xmlHttpRequest, textStatus, errorThrown) {
	showFavoritePropertyCreationAlert();
	$("#favoriteCreationAlert").switchClass("alert-success", "alert-danger");
    $("#mostPopularPropertiesViewContainer").html("An error is preventing this property from being added to your favorites: " + errorThrown);
}

function showFavoritePropertyCreationAlert() {
	$("#favoriteCreationAlert").fadeIn("slow");
}