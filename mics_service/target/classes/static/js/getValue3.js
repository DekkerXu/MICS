$(document).ready(function() {
    $.ajax({
        type: 'post',
        url: '/search',
        data: {},
        success: function(data) {
            var str = "";
            for (var i = 0; i < data.length; i++) {
                str += "<tr><td>" + data[i].contractName + "</td>";
                str += "<td>" + data[i].orderedName + "</td>";
                str += "<td>" + data[i].orderstartDate + "</td>";
                str += "<td>" + data[i].orderendDate + "</td></tr>";
            }
            $("#workerList").append(str);
        }
    })

    $("#hh").click(function() {
        user = {};
        var name = $('input[name=name]').val();
        user.name = name;
        var number = $('input[name=number]').val();
        user.number = number;
        $.ajax({
            type: 'post',
            url: '/detailSearch',
            data: user, //因为ajax是要传递的json格式的，但是我们需要传递过去的是数组，所以把整个数组当成json的一个key的数值传递过去，ids就是一个key。如果只传{clear_data},那么传递过去的key就是clear_data。
            dataType: "json",
            success: function(data) {
                var str = "";
                $("#workerList").empty();
                for (var i = 0; i < data.length; i++) {
                    str += "<tr><td>" + data[i].contractName + "</td>";
                    str += "<td>" + data[i].orderedName + "</td>";
                    str += "<td>" + data[i].orderstartDate + "</td>";
                    str += "<td>" + data[i].orderendDate + "</td></tr>";
                }
                $("#workerList").append(str);
            }
        })
    });


    $("#subButton").click(function() {

        userData3 = {};

        var patientName = $('input[name=patientName]').val();
        userData3.patientName = patientName;

        var certifyType3 = $('select[name=certifiType3]').val();
        userData3.certifyType3 = certifyType3;

        var certifiNumber3 = $("input[name=certifiNumber3]").val();
        userData3.certifiNumber3 = certifiNumber3;

        var payment = $("input[name=payment]").val();
        userData3.payment = payment;

        var kind = $('select[name=kind]').val();
        userData3.kind = kind;

        var range_value = $('select[name=range]').val();
        userData3.range_value = range_value;

        var age = $('input[name=age]').val();
        userData3.age = age;

        var sex3 = $('select[name=sex]').val();
        userData3.sex3 = sex3;

        var in_hospital = $('select[name=zhuyuan]').val();
        userData3.in_hospital = in_hospital;

        var type = $('input[name=type]').val();
        userData3.type_value = type;


        var visitTime = $('input[name=visitTime]').val();
        var date = new Date(visitTime);
        var mon = date.getMonth() + 1;
        var day = date.getDate();
        var nowDay = "" + date.getFullYear() + (mon < 10 ? "0" + mon : mon) + (day < 10 ? "0" + day : day);
        userData3.visitTime = nowDay;

        var miaoshu = $('textarea[name=miaoshu]').val();
        userData3.miaoshu = miaoshu;

        var history = $('textarea[name=history]').val();
        userData3.history = history;
        //验证函数
        var str = "";
        var str2 = "";
        var orderName = $("#patientName").val();
        var certifiNumber = $("#certifiNumber3").val();

        //判断名称
        if ($.trim($('#patientName').val()).length == 0) {
            str += '名称没有输入\n';
            $('#patientName').focus();
            $('#inputName').text(str).css("display", "inline-block");
        } else if (!orderName.match(/^[\u4E00-\u9FA5]{1,6}$/)) {
            str += '名称不合法\n';
            $('#patientName').focus();
            $('#inputName').text(str).css("display", "inline-block");
        } else {
            $('#inputName').css("display", "none");
        }

        //验证身份证号
        if ($.trim($('#certifiNumber3').val()).length == 0) {
            str2 += '号码没有输入\n';
            $('#certifiNumber3').focus();
            $('#inputcertifiNumber').text(str2).css("display", "inline-block");
        } else if (!certifiNumber.match(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/)) {
            str2 += '号码不合法\n';
            $('#certifiNumber3').focus();
            $('#inputcertifiNumber').text(str2).css("display", "inline-block");
        } else {
            $('#inputcertifiNumber').text(str2).css("display", "none");
        }

        //最终验证判断
        if (str != '' || str2 != '') {
            //          alert("");
            return false;
        } else {
            //          alert(a)
            $.ajax({
                type: 'post',
                url: '/savemedicalCare',
                data: userData3, //因为ajax是要传递的json格式的，但是我们需要传递过去的是数组，所以把整个数组当成json的一个key的数值传递过去，ids就是一个key。如果只传{clear_data},那么传递过去的key就是clear_data。
                dataType: 'json',
                success: function(data) {
                    alert(data);
                    /*if(jsonData.errmsg === 'ok') {
                        $("input[name='checkShop[]:checked']").each(function(){
                           $(this).parent().parent("li").remove();
                       })
                    }*/
                }
            });
            var userData3 = JSON.stringify(userData3);
            localStorage.setItem("userData3", userData3);
            alert("提交成功");

        }
    })
});