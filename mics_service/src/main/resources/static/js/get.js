$(document).ready(function() {
    $('select[name=customQuotation1],select[name=customQuotation2],select[name=customQuotation3],select[name=customQuotation4],select[name=customQuotation5],select[name=customQuotation6],select[name=customQuotation7],select[name=customQuotation8]').change(function() {
        var value11 = $('select[name=customQuotation1]').val();
        var value12 = $('select[name=customQuotation2]').val();
        var value21 = $('select[name=customQuotation3]').val();
        var value22 = $('select[name=customQuotation4]').val();
        var value31 = $('select[name=customQuotation5]').val();
        var value32 = $('select[name=customQuotation6]').val();
        var value33 = $('select[name=customQuotation7]').val();
        var value34 = $('select[name=customQuotation8]').val();
        var value111 = value11 / 50000 * 43.8;
        var value121 = value12 / 2000 * 4;
        var value211 = value21 / 20 * 4;
        var value221 = value22 / 2000 * 1;
        var value311 = value31 / 500000 * 8;
        var value321 = value32 / 100000 * 1.5;
        var value331 = value33 / 100000 * 2.4;
        var value341 = value34 / 50000 * 2;
        var totalValue = value111 + value121 + value211 + value221 + value311 + value321 + value331 + value341;
        $('#total,#chooseActualPremium').text(totalValue);
        var dayPay = totalValue / 365;
        dayPay = dayPay.toFixed(2);
        $('.amountByDay').text(dayPay);
        console.log(totalValue);
    });
    $("#nextBtn").click(function() {
        objJSON = {};

        var orderDate = $('input[name=date]').val();
        objJSON.date = orderDate;
        var ageType = $('select[name=ageType]').val();
        objJSON.ageType = ageType;
        var personCount = $('select[name=personCount]').val();
        objJSON.personCount = personCount;



        objJSON.kind1 = "一般意外";
        objJSON.range1 = "意外身故、伤残";
        var customQuotation1 = $('select[name=customQuotation1]').val();
        objJSON.customQuotation1 = customQuotation1;



        objJSON.kind2 = "一般意外";
        objJSON.range2 = "意外医疗";
        var customQuotation2 = $('select[name=customQuotation2]').val();
        objJSON.customQuotation2 = customQuotation2;



        objJSON.kind3 = "津贴及车费";
        objJSON.range3 = "住院误工津贴";
        var customQuotation3 = $('select[name=customQuotation3]').val();
        objJSON.customQuotation3 = customQuotation3;



        objJSON.kind4 = "津贴及车费";
        objJSON.range4 = "救护车费用报销";
        var customQuotation4 = $('select[name=customQuotation4]').val();
        objJSON.customQuotation4 = customQuotation4;



        objJSON.kind5 = "交通意外";
        objJSON.range5 = "飞机意外身故、伤残";
        var customQuotation5 = $('select[name=customQuotation5]').val();
        objJSON.customQuotation5 = customQuotation5;



        objJSON.kind6 = "交通意外";
        objJSON.range6 = "火车意外身故、伤残";
        var customQuotation6 = $('select[name=customQuotation6]').val();
        objJSON.customQuotation6 = customQuotation6;



        objJSON.kind7 = "交通意外";
        objJSON.range7 = "船舶意外身故、伤残";
        var customQuotation7 = $('select[name=customQuotation7]').val();
        objJSON.customQuotation7 = customQuotation7;



        objJSON.kind8 = "交通意外";
        objJSON.range8 = "汽车意外身故、伤残";
        var customQuotation8 = $('select[name=customQuotation8]').val();
        objJSON.customQuotation8 = customQuotation8;

        objJSON.contractState = 1;



        var totalVlue = $('#total').html();
        objJSON.totalVlue = totalVlue;

        var d0 = orderDate.toString();
        var d1 = new Date(d0);
        var d2 = new Date(d1);
        d2.setFullYear(d2.getFullYear() + 1);
        d2.setDate(d2.getDate() - 1);
        var d3 = "" + d2.getFullYear() + "/";
        d3 += (d2.getMonth() + 1) + "/";
        d3 += d2.getDate();
        objJSON.orderstartDate = d0;
        objJSON.orderendDate = d3;


        $("#clibut").text("obj");
        $.ajax({
            type: 'post',
            data: objJSON,
            url: '/orderContract',

            success: function(data) {
                if (data == "fail") {
                    alert("请先登录");
                } else {
                    var objJSON2 = JSON.stringify(objJSON);
                    localStorage.setItem("合同", objJSON2); //用localStorage保存转化好的的字符串 
                    window.location.href = "./orderContract";
                }
            }
        })
    });
})