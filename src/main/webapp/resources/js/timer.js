/**
 * @author Artur Meshcheriakov
 */

function timer() {
    setInterval(function () {
        var timer = document.getElementById("timer");
        var s = timer.innerHTML;
        if (s == 0) {
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
        } else {
            s--;
        }
        document.getElementById("timer").innerHTML = s;
    }, 1000);
}
