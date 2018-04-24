$(document).ready(function() {
    //用户界面查询历史电子病历
    $("#searchButton").click(function() {
        search = {};
        search.searchName = $("input[name=patientName]").val();
        search.certifiNumber = $("input[name=certifiNumber3]").val();
        $.ajax({
            type: 'post',
            data: search,
            url: '/checkDate',
            success: function(data) {
                contaractDetail = {};
                var str = "";
                $("#medicalData").empty();
                for (var i = 0; i < data.length; i++) {
                    var obj = eval("(" + data[i] + ")");
                    str += "<tr><td style=\"padding-top: 15px;\">" + obj.PatientName + "</td>";
                    str += "<td style=\"padding-top: 15px;\">" + obj.VisitTime + "</td>";
                    str += "<td><a class=\"btn btn-primary apply\" style=\"margin-top: 3px;\">查看详情</a></tr>"
                    contaractDetail[i] = data[i];
                }
                $("#medicalData").append(str);
                for (var i = 0; i < data.length; i++) {
                    var apply = "apply" + i;
                    $("#medicalData").children('tr').eq(i).children('td').eq(2).children('a').attr("id", i);
                }

                $(".apply").click(function() {
                    var i = $(this).attr("id");
                    data = {};
                    for (var key in contaractDetail[i]) {
                        data[key] = contaractDetail[i][key];
                    };
                    var data = JSON.stringify(data);
                    localStorage.setItem("详情", data);
                    window.location.href = "./medicalDetail";
                });
            }
        })
    })
})