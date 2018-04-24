$(document).ready(function() {

    $.ajax({
        type: 'post',
        data: {},
        url: '/underwriter',
        success: function(data) {
            if (data[0] == null) {
                alert("暂无数据");
            } else {
                var str = "";
                var state;
                var contaractDetail = new Array(data.length);
                $("#list").empty();
                for (var i = 0; i < data.length; i++) {
                    str += "<tr><td style=\"padding-top: 15px;\">" + data[i].policyNumber + "</td>";
                    str += "<td style=\"padding-top: 15px;\">" + data[i].medicalCareId + "</td>";
                    str += "<td style=\"padding-top: 15px;\">" + data[i].claimState + "</td>";
                    str += "<td><a class=\"btn btn-primary check\" style=\"margin-top: 3px;\">复核</a></tr>"
                    contaractDetail[i] = data[i];
                }
                $("#list").append(str);
                for (var i = 0; i < data.length; i++) {
                    $("#settlementList").children('tr').eq(i).children('td').eq(3).children('a').attr("id", i);
                }
                underwriterDate = {};
                $(".check").click(function() {
                    underwriterDate.policyNumber = $(this).parent().parent().children('td').eq(0).html();
                    underwriterDate.medicalCareId = $(this).parent().parent().children('td').eq(1).html();
                    $.ajax({
                        type: 'post',
                        data: underwriterDate,
                        url: '/configure',
                        success: function(data) {
                            window.location.href = "./configure";
                        }
                    })
                })
            }
        }
    })
});