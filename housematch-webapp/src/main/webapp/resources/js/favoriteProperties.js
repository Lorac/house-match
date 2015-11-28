$(document).ready(function () {
    $('#addPropertiesToFavorite').one('click', function (ev) {
        var hashCode = $(this).attr("value");
        addPropertiesToFavorite(hashCode);
        return false;
    });
});


function addPropertiesToFavorite(propertyHashCode) {
    $.ajax({
        type: "POST",
        url: "/buyer/addPropertyToFavorites/" + propertyHashCode,
        timeout: 15000,
        success: favoritePropertyCreationSuccessHandler,
        error: favoritePropertyCreationErrorHandler
    });
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
	$("#favoriteCreationAlert").addClass("in");
}