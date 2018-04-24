$(document).ready(function() {
    //根据查询内容切换显示的list
    //查询按钮
    $("#searchButton").click(function() {
        $("#list").empty();
        searchDate = {};
        searchDate2 = {};
        searchDate.searchName = $("select[name=orderedName]").val();
        searchDate2.searchName = "medicalcare";
        searchDate.certifiNumber = $("input[name=certifiNumber]").val();
        searchDate2.certifiNumber = $("input[name=certifiNumber]").val();
        var selectedValue = $("select").val();
        if (selectedValue == "contract") {
            $("#list1").show();
            $("#list2").hide();
            $("#list3").hide();
        } else if (selectedValue == "medicalcare") {
            $("#list2").show();
            $("#list1").hide();
            $("#list3").hide();
        } else if (selectedValue == "claim") {
            $("#list3").show();
            $("#list1").hide();
            $("#list2").hide();
        };
        if (searchDate.searchName == "contract") {

            $.ajax({
                type: 'post',
                data: searchDate,
                url: '/checkDate',
                success: function(data) {
                    var medicalData = new Array();
                    evaluation();
                    var str = "";
                    var contaractDetail = new Array(data.length);
                    $("#list").empty();

                    function evaluation() {
                        $.ajax({
                            type: 'post',
                            data: searchDate2,
                            async: false,
                            url: '/checkDate',
                            success: function(data2) {

                                for (var i = 0; i < data2.length; i++) {
                                    var objj = eval("(" + data2[i] + ")");
                                    medicalData[i] = objj;
                                }
                            }
                        })
                    }


                    for (var i = 0; i < data.length; i++) {
                        var obj = eval("(" + data[i] + ")");
                        str += "<tr><td style=\"padding-top: 15px;\">" + obj.ContractName + "</td>";
                        str += "<td style=\"padding-top: 15px;\">" + obj.OrderedName + "</td>";
                        str += "<td style=\"padding-top: 15px;\">" + obj.OrderstartDate + "</td>";
                        str += "<td style=\"padding-top: 15px;\">" + obj.OrderendDate + "</td>";
                        str += "<td style=\"padding-top: 15px;\"><select class=\"form-control\">";
                        for (var m = 0; m < medicalData.length; m++) {
                            str += "<option name=\""+m+"\">就诊于:" + medicalData[m].VisitTime.substring(0,4)+"/"+medicalData[m].VisitTime.substring(4,6)+"/"+ medicalData[m].VisitTime.substring(6,8)+",病情:" +medicalData[m].Describe+"</option>";
                            
                        }
                        str += "</select></td>"
                        str += "<td><a class=\"btn btn-primary detail\" style=\"margin-top: 3px;\">查看详情</a></td>";
                        str += "<td><a class=\"btn btn-primary apply\" style=\"margin-top: 3px;\">申请理赔</a></tr>"
                        contaractDetail[i] = obj;
                    }
                    $("#list").append(str);
                    for (var i = 0; i < data.length; i++) {
                        var apply = "apply" + i;
                        $("#list").children('tr').eq(i).children('td').eq(5).children('a').attr("id", i);
                        $("#list").children('tr').eq(i).children('td').eq(6).children('a').attr("id", apply);
                    }
                    if (data == "") {
                        alert("暂无数据");
                    } else {
                        alert("搜索成功");
                    }

                    $(".detail").click(function() {
                        var i = $(this).attr("id");
                        contract = {};
                        for (var key in contaractDetail[i]) {
                            contract[key] = contaractDetail[i][key];
                        };
                        var detailContract = JSON.stringify(contract);
                        localStorage.setItem("合同详情", detailContract);
                        window.location.href = "./detail";
                    });

                    $(".apply").click(function() {
                        var i = $(this).parent().parent().find(".detail").attr("id");
                        var m = $(this).parent().parent().find("select option:selected").attr("name");
                        contract = {};
                        var obj2 = eval("(" + data[i] + ")");
                        contract.ContractId = obj2.ContractId;
                        contract.certifiNumber = obj2.CertifiNumber;
                        if(medicalData!=null){
                        contract.medicalcareID = medicalData[m].medicalcareID;
                        }else{
                        	contract.medicalcareID = null;
                        }
                        
                        if (contract.medicalcareID!=null) {
                            $.ajax({
                                type: 'post',
                                data: contract,
                                url: '/apply',
                                success: function(data) {
                                  var dataArray = [];


                                    for (var i = 0; i <= data.length - 2; i++) {
                                        var obj3 = eval("(" + data[i] + ")");
                                        dataArray.push(obj3);
                                    }
                                    if (data[0] == "没有匹配到数据") {
                                        alert("没有匹配到数据");
                                    } else {
                                        alert("申请成功");
                                    }
                                }
                            })
                        }else{
                            alert("没有医疗数据");
                        }
                        
                    });

                }
            })
        } else if (searchDate.searchName == "medicalcare") {
            $.ajax({
                type: 'post',
                data: searchDate,
                url: '/checkDate',
                success: function(data) {
                    var medicalcareDetail = new Array(data.length);
                    var str = "";
                    $("#list").empty();
                    for (var i = 0; i < data.length; i++) {
                        var obj = eval("(" + data[i] + ")");
                        str += "<tr><td style=\"padding-top: 15px;\">" + obj.PatientName + "</td>";
                        str += "<td style=\"padding-top: 15px;\">" + obj.VisitTime + "</td>";
                        str += "<td style=\"padding-top: 15px;\">" + obj.Describe + "</td>";
                        str += "<td><a class=\"btn btn-primary apply\" style=\"margin-top: 3px;\">查看详情</a></tr>"
                        medicalcareDetail[i] = obj;
                    }
                    $("#list").append(str);
                    for (var i = 0; i < data.length; i++) {
                        var apply = "apply" + i;
                        $("#list").children('tr').eq(i).children('td').eq(3).children('a').attr("id", i);
                    }

                    $(".apply").click(function() {
                        var i = $(this).attr("id");
                        data = {};
                        for (var key in medicalcareDetail[i]) {
                            data[key] = medicalcareDetail[i][key];
                        };
                        var data = JSON.stringify(data);
                        localStorage.setItem("medicalcareDetail", data);
                        window.location.href = "./medicalDetail";
                    });
                }
            })
        } else if (searchDate.searchName == "claim") {
            $.ajax({
                type: 'post',
                data: searchDate,
                url: '/settleData',
                success: function(data) {
                    if (data[0] == null) {
                        alert("暂无数据");
                    } else {
                        var str = "";
                        var state;
                        var contaractDetail = new Array(data.length);
                        var contaractState = new Array(data.length);
                        $("#list").empty();
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].claimNumber == 2) { //fail
                                var state = "理赔未通过";
                            } else if (data[i].claimNumber == 3) {
                                var state = "人工核保中";
                            } else if (data[i].claimNumber == 4) { //人工核保结束
                                var state = "人工核保未通过"
                            } else if (data[i].claimNumber == 5) {
                                var state = "完成";
                                data[i].claimState = "理赔金额:"+data[i].claimState+"元";
                            } else if (data[i].claimNumber == 6) { //success
                                var state = "理赔通过";
                                data[i].claimState = "理赔金额:"+data[i].claimState+"元";
                            }
                            contaractDetail[i] = data[i];
                            contaractState[i] = data[i].claimNumber;
                            str += "<tr><td style=\"padding-top: 15px;\">" + data[i].policyNumber + "</td>";
                            str += "<td style=\"padding-top: 15px;\">" + data[i].medicalCareId + "</td>";
                            str += "<td style=\"padding-top: 15px;\">" + state + "</td>";
                            str += "<td style=\"padding-top: 15px;\">" + data[i].claimState + "</td>";
                            str += "<td><a class=\"btn btn-primary check\" style=\"margin-top: 3px;\">复核</a></tr>"

                        }

                        $("#list").append(str);
                        for (var i = 0; i < data.length; i++) {
                            $("#list").children('tr').eq(i).children('td').eq(4).children('a').attr("id", i);
                        }
                    }

                    //人工核保申请
                    $(".check").click(function() {
                        var i = $(this).attr("id");
                        if (contaractState[i] == 6) {
                            alert("已通过核保，请勿重新复核");
                        } else if (contaractState[i] == 5) {
                            alert("该记录已经理赔结束，请勿重复申请");
                        } else if (contaractState[i] == 4) {
                            alert("该记录人工核保未通过，请勿重复申请");
                        } else if (contaractState[i] == 3) {
                            alert("该记录人工核保中");
                        } else {
                            claim = {};
                            claim.policyNumber = $(this).parent().parent().children('td').eq(0).html();
                            claim.medicalCareId = $(this).parent().parent().children('td').eq(1).html();
                            $.ajax({
                                type: 'post',
                                data: claim,
                                url: '/workerCheck',
                                success: function(data) {
                                    alert(data);
                                }
                            })
                        }

                    })
                }
            })
        }
    })
})