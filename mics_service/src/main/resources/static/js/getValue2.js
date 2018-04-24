$(document).ready(function() {
    var students = localStorage.getItem("合同"); //取回students变量  

    students = JSON.parse(students); //把字符串转换成JSON对象  

    var text = JSON.stringify(students); //(将对象转化成文本对象)

    $("#date").text(students.orderstartDate);
    $("#date2").text(students.orderendDate);
    $("#totalValue").text(students.totalVlue);

    $("#submitButton").click(function() {
        userData1 = {};

        var orderstartDate = $('#date').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.orderstartDate = orderstartDate;

        var orderendDate = $('#date2').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.orderendDate = orderendDate;

        var orderName = $("input[name=orderName]").val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.orderName = orderName;

        var certifiType = $("select[name=certifitype]").val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.certifiType = certifiType;

        var certifiNumber = $('input[name=certifiNumber]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.certifiNumber = certifiNumber;

        var birthday = $('input[name=birthday]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.birthday = birthday;

        var phoneNumber = $('input[name=phonenumber]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.phoneNumber = phoneNumber;

        var sex = $('select[name=sex]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.sex = sex;

        var eMail = $('input[name=email]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.email = eMail;

        var city = $('input[name=city]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.city = city;

        userData1.orderstartDate = students.orderstartDate;
        userData1.orderendDate = students.orderendDate;

        var orderedName = $('input[name=orderedName]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.orderedName = orderedName;

        var relationship = $('select[name=relationship]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.relationship = relationship;

        var certifiType2 = $('select[name=certifiType2]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.certifiType2 = certifiType2;

        var birthday2 = $('input[name=birthday2]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.birthday2 = birthday2;

        var sex2 = $('select[name=sex2]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.sex2 = sex2;

        var certifiNumber2 = $('input[name=certifiNumber2]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.certifiNumber2 = certifiNumber2;

        var phoneNumber2 = $('input[name=phoneNumber2]').val().replace(/(^\s*)|(\s*$)/g, "");
        userData1.phoneNumber2 = phoneNumber2;

        userData1.contractState = 0;

        var str = "";
        var str2 = "";
        var str3 = "";
        var str4 = "";
        var str5 = "";
        var str6 = "";
        var str7 = "";
        var orderName = $("#orderName").val();
        var certifiNumber = $("#certifiNumber").val();
        var phonenumber = $("#phonenumber").val();
        var email = $("#email").val();
        var orderedName = $("#orderedName").val();
        var certifiNumber2 = $("#certifiNumber2").val();
        var phonenumber2 = $("#phonenumber2").val();

        //判断名称
        if ($.trim($('#orderName').val()).length == 0) {
            str += '名称没有输入\n';
            $('#orderName').focus();
            $('#inputName').text(str).css("display", "inline-block");
        } else if (!orderName.match(/^[\u4E00-\u9FA5]{1,6}$/)) {
            str += '名称不合法\n';
            $('#orderName').focus();
            $('#inputName').text(str).css("display", "inline-block");
        } else {
            $('#inputName').css("display", "none");
        }

        //验证身份证号
        if ($.trim($('#certifiNumber').val()).length == 0) {
            str2 += '号码没有输入\n';
            $('#certifiNumber').focus();
            $('#inputcertifiNumber').text(str2).css("display", "inline-block");
        } else if (!certifiNumber.match(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/)) {
            str2 += '号码不合法\n';
            $('#certifiNumber').focus();
            $('#inputcertifiNumber').text(str2).css("display", "inline-block");
        } else {
            $('#inputcertifiNumber').text(str2).css("display", "none");
        }


        //验证手机
        if ($.trim($('#phonenumber').val()).length == 0) {
            str3 += '手机没有输入\n';
            $('#phonenumber').focus();
            $('#inputphonenumber').text(str3).css("display", "inline-block");
        } else if (!phonenumber.match(/^1[34578]\d{9}$/)) {
            str3 += '手机号不合法\n';
            $('#phonenumber').focus();
            $('#inputphonenumber').text(str3).css("display", "inline-block");
        } else {
            $('#inputphonenumber').text(str3).css("display", "none");
        }

        //验证邮箱
        if ($("#email").val() == "") {
            str4 += '邮箱没有输入\n';
            $('#email').focus();
            $('#inputemail').text(str4).css("display", "inline-block");
        } else if (!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            str4 += '邮箱不合法\n';
            $('#email').focus();
            $('#inputemail').text(str4).css("display", "inline-block");
        } else {
            $('#inputemail').text(str4).css("display", "none");
        }

        //判断名称2
        if ($.trim($('#orderedName').val()).length == 0) {
            str5 += '名称没有输入\n';
            $('#orderedName').focus();
            $('#inputorderedName').text(str5).css("display", "inline-block");
        } else if (!orderedName.match(/^[\u4E00-\u9FA5]{1,6}$/)) {
            str5 += '名称不合法\n';
            $('#orderedName').focus();
            $('#inputorderedName').text(str5).css("display", "inline-block");
        } else {
            $('#inputorderedName').text(str5).css("display", "none");
        }

        //验证身份证号2
        if ($.trim($('#certifiNumber2').val()).length == 0) {
            str6 += '证件号没有输入\n';
            $('#certifiNumber2').focus();
            $('#inputcertifiNumber2').text(str6).css("display", "inline-block");
        } else if (!certifiNumber2.match(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/)) {
            str6 += '证件号不合法\n';
            $('#certifiNumber2').focus();
            $('#inputcertifiNumber2').text(str6).css("display", "inline-block");
        } else {
            $('#inputcertifiNumber2').text(str6).css("display", "none");
        }


        //验证手机2
        if ($.trim($('#phonenumber2').val()).length == 0) {
            str7 += '手机号没有输入\n';
            $('#phonenumber2').focus();
            $('#inputphonenumber2').text(str7).css("display", "inline-block");
        } else if (!phonenumber2.match(/^1[34578]\d{9}$/)) {
            str7 += '手机号不合法\n';
            $('#phonenumber2').focus();
            $('#inputphonenumber2').text(str7).css("display", "inline-block");
        } else {
            $('#inputphonenumber2').text(str7).css("display", "none");
        }

        //checkbox验证
        var a = $('input[name=group]').prop('checked');
        if (a != true) {
            alert("请勾选”我已阅读并同意以上内容“");
            $('input[name=group]').focus();
        }

        //最终验证判断
        if (str != '' || str2 != '' || str3 != '' || str4 != '' || str5 != '' || str6 != '' || str7 != '' || a != true) {
            //          alert("");
            return false;
        } else {
            //          alert(a)
            var userData1 = JSON.stringify(userData1);
            localStorage.setItem("userData", userData1);
            $.ajax({
                type: 'post',
                url: '/orderInfo',
                data: userData1,
                success: function(data) {
                    if (data == "contract") {
                        window.location.href = "./contract";
                    }
                }
            })
        }
    });

    //验证函数


})