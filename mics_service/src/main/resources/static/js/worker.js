$(document).ready(function() {
    $("#allButton").click(function() {
        //员工admin界面显示所有合同
        $.ajax({
            type: 'post',
            url: '/search',
            data: {},
            success: function(data) {
                var str = "";
                for (var i = 0; i < data.length; i++) {
                	  if(data[i].claimNumber == 6||data[i].claimNumber == 5){
                	  	 	continue;
                	  }else{
                	  		str += "<tr><td style=\"padding-top: 15px;\">" + data[i].policyNumber + "</td>";
                    		str += "<td style=\"padding-top: 15px;\">" + data[i].policyNumber + "</td>";
                    		str += "<td><a class=\"btn btn-primary work1\" style=\"margin-top: 3px;\">分配</a></td>";
                    		str += "<td><a class=\"btn btn-primary work2\" style=\"margin-top: 3px;\">分配</a></td>";
                    		str += "<td><a class=\"btn btn-primary work3\" style=\"margin-top: 3px;\">分配</a></td>";
                    		str += "<td><a class=\"btn btn-primary work4\" style=\"margin-top: 3px;\">分配</a></tr>"
                	  	  }	
                    
                }
                $("#workerList").append(str);
            }
        });
    })
    //员工admin界面查询某份合同
    $("#checkButton").click(function() {
        user = {};
        user.name = $('select[name=name]').val();
        $.ajax({
            type: 'post',
            url: '/detailSearch',
            data: user, //因为ajax是要传递的json格式的，但是我们需要传递过去的是数组，所以把整个数组当成json的一个key的数值传递过去，ids就是一个key。如果只传{clear_data},那么传递过去的key就是clear_data。
            success: function(data) {
                var str = "";
                $("#workerList").empty();
                for (var i = 0; i < data.length; i++) {
                if(data[i].claimNumber == 6||data[i].claimNumber == 5){
                	  	 	continue;
                	  }else{
                    str += "<tr><td style=\"padding-top: 15px;\" class=\"policyNumber\">" + data[i].policyNumber + "</td>";
                    str += "<td style=\"padding-top: 15px;\">" + data[i].medicalCareId + "</td>";
                    str += "<td style=\"padding-top: 15px;\">" + data[i].underwritingPerson + "</td>";
                    str += "<td><a class=\"btn btn-primary worker\" style=\"margin-top: 3px;\">分配</a></td>";
                    str += "<td><a class=\"btn btn-primary worker\" style=\"margin-top: 3px;\">分配</a></td>";
                    str += "<td><a class=\"btn btn-primary worker\" style=\"margin-top: 3px;\">分配</a></td>";
                    str += "<td><a class=\"btn btn-primary worker\" style=\"margin-top: 3px;\">分配</a></tr>"
                    }

                }
                $("#workerList").append(str);
                for (var i = 0; i < data.length; i++) {
                    $("#workerList").children('tr').eq(i).children('td').eq(3).children('a').attr("name", "worker1");
                    $("#workerList").children('tr').eq(i).children('td').eq(4).children('a').attr("name", "worker2");
                    $("#workerList").children('tr').eq(i).children('td').eq(5).children('a').attr("name", "worker3");
                    $("#workerList").children('tr').eq(i).children('td').eq(6).children('a').attr("name", "worker4");
                }
                //分配员工

                $(".worker").click(function() {
                    allocation = {};
                    allocation.policyNumber = $(this).parent().parent().children('td').eq(0).html();
                    allocation.medicalCareId = $(this).parent().parent().children('td').eq(1).html();
                    allocation.worker = $(this).attr("name");
                    var a = $(this).parent().parent().children('td').eq(2);
                    var b = $(this).parent().parent();
                    $.ajax({
                        type: 'post',
                        url: '/allocation',
                        data: allocation,
                        success: function(data) {
                            if (data != $(b).html()) {
                                $(b).empty();
                            }
                        }
                    })
                })
            }
        });

    })
})