$(document).ready(function () {

    $('#login-form-link').click(function (e) {
        $("#login-form").delay(200).fadeIn(200);
        $("#register-form").fadeOut(200);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $('#register-form-link').click(function (e) {
        $("#register-form").delay(200).fadeIn(200);
        $("#login-form").fadeOut(200);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

});
