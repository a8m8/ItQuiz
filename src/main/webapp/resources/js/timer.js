/**
 * @author Artur Meshcheriakov
 */

var myTimer;

function timer() {
    myTimer = setInterval(function () {
        var timer = document.getElementById("timer");
        var s = timer.innerHTML;
        if (s == 0) {
            stopTimerAndPost();
        } else {
            s--;
        }
        document.getElementById("timer").innerHTML = s;
    }, 1000);
}

function stopTimerAndPost() {
    clearTimeout(myTimer);
    $("#passing-test-form").ajaxSubmit({
        url: '/student/passing-test',
        type: 'POST',
        headers: {
            Accept: 'application/json'
        },
        dataType: 'json',
        success: function (data) {
            window.location.replace(data['redirect']);
        }
    })
}
