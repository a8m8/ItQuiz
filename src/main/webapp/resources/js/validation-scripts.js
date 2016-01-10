$(document).ready(function () {
    $('#signup-form').validate({
        focusCleanup: true,
        focusInvalid: false,
        onkeyup: false,
        rules: {
            email: {
                required: true,
                email: true,
                maxlength: 60,
                remote: "signup"
            },
            password: {
                required: true,
                maxlength: 60
            },
            passwordConfirmed: {
                required: true,
                equalTo: "#password"
            },
            login: {
                required: true,
                maxlength: 60,
                remote: "signup"
            },
            fio: {
                required: true,
                maxlength: 200
            }
        },
        messages: {
            email: {
                required: 'The email is required and cannot be empty',
                email: 'Please enter valid email address',
                maxlength: 'The email address must not exceed 60 characters',
                remote: "Current email is already registered"
            },
            password: {
                required: 'Please enter the password',
                maxlength: 'The password must not exceed 60 characters'
            },
            passwordConfirmed: {
                required: 'Please confirm your password',
                equalTo: 'This password does not match that entered in the password field'
            },
            login: {
                required: 'Please enter the login',
                maxlength: 'The login must not exceed 60 characters',
                remote: 'Current login is already registered'
            },
            fio: {
                required: 'Please enter your name',
                maxlength: 'The name must not exceed 60 characters'
            }
        }
//		submitHandler: function(form) {
//		    $(form).ajaxSubmit();
//		    windows.location.href = "/login";
//		}
        //FIXME AJAX SUBMIT
    });
});

$(document).ready(function () {
    $('#password-recovery-form').validate({
        focusCleanup: true,
        focusInvalid: false,
        onkeyup: false,
        rules: {
            email: {
                required: true,
                email: true,
                remote: "password-recovery"
            }
        },
        messages: {
            email: {
                required: 'The email is required and cannot be empty',
                email: 'Please enter valid email address',
                remote: "Cannot send password on this email"
            }
        }
    });
});
