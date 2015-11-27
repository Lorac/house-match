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
        url: "/buyer/addPropertyToFavorite/" + propertyHashCode,
        timeout: 15000,
        success: showMostPopularPropertiesSuccessHandler,
        error: showMostPopularPropertiesErrorHandler
    });
}

function showMostPopularPropertiesSuccessHandler(responseData, status) {
    $("#favoriteAlert").addClass("in");
    $('#addPropertiesToFavorite').addClass('disabled');
}

function showMostPopularPropertiesErrorHandler(xmlHttpRequest, textStatus, errorThrown) {
    $("#mostPopularPropertiesViewContainer").html("An error is preventing the results from being diplayed: " + errorThrown);
}