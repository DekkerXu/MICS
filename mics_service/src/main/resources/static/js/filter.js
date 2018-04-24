$(document).ready(function() {
    $("#regButton").click(function() {
        regForm = {};
        var username = $("input[name=username]").val().replace(/(^\s*)|(\s*$)/g, "");
        regForm.username = username;
        var password = $("input[name=password]").val().replace(/(^\s*)|(\s*$)/g, "");
        regForm.password = password;
        var password2 = $("input[name=password2]").val().replace(/(^\s*)|(\s*$)/g, "");
        regForm.password2 = password2;
        $.ajax({
            type: 'post',

            data: regForm,
            url: '/addregister',
            success: function(data) {
                if (data == "success") {
                    window.location.href = "../login";
                } else {
                    alert(data);
                }
            }
        })
    });


    $("#infoButton").click(function() {
        $.ajax({
            type: 'post',
            data: {},
            url: '/info',
            success: function(data) {
                if (data == "needLogin") {
                    alert("请先登录");
                } else {
                    window.location.href = "../information";
                }
            }
        })
    })
})