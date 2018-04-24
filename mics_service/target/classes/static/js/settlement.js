$(document).ready(function() {
    $.ajax({
        type: 'post',
        data: {},
        url: '/settle',
        success: function(data) {
            $("#list").empty();
            var medicalcare = new Array(data.length);
            var str = "";

            $.ajax({
                type: 'post',
                data: {},
                async: false,
                url: '/settle2',
                success: function(data) {
                    for (var i = 0; i < data.length; i++) {
                        medicalcare[i] = data[i];
                    }

                }

            })
            for (var i = 0; i < data.length; i++) {
                var obj = eval("(" + data[i] + ")");
                str += "<tr><td style=\"padding-top: 15px;\">" + obj.ContractId + "</td>";
                str += "<td style=\"padding-top: 15px;\">" + obj.OrderName + "</td>";
                str += "<td style=\"padding-top: 15px;\">" + obj.PhoneNumber + "</td>";
                str += "<td style=\"padding-top: 15px;\">" + medicalcare[i].medicalCareId + "</td>";
                str += "<td><a class=\"btn btn-primary apply\" style=\"margin-top: 3px;\">通过    </a></tr>"
            }
            $("#list").append(str);
            for (var i = 0; i < data.length; i++) {
                var apply = "apply" + i;
                $("#list").children('tr').eq(i).children('td').eq(5).children('a').attr("id", i);
            }
            $(".apply").click(function() {
                var i = $(this).attr("id");
                information = {};
                information.policyNumber = $(this).parent().parent().children('td').eq(0).html();
                information.medicalCareId = $(this).parent().parent().children('td').eq(3).html();
                $.ajax({
                    type: 'post',
                    data: information,
                    url: '/settleState',
                    success: function(data) {
                        if (data == "ok") {
                            alert("理赔成功");
                             window.location.reload();
                      }
                    }
                })
            });
        }
    })
})