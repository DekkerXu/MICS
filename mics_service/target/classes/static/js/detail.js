$(document).ready(function() {
    var getData = localStorage.getItem("合同详情"); //取回变量  
    getData = JSON.parse(getData); //把字符串转换成JSON对象
    var kind = "kind";
    var range = "range";
    var customQuotation = "customQuotation";
    var str = "";
    for (var i = 1; i <= 8; i++) {
        var kind = "Kind" + i;
        var range = "Range" + i;
        var customQuotation = "CustomQuotation" + i;
        str += "<tr><td>" + getData[kind] + "</td>";
        str += "<td>" + getData[range] + "</td>";
        str += "<td>" + "0-" + getData[customQuotation] + "W" + "</td>";
        str += "<td>" + getData.OrderstartDate + "--" + getData.OrderendDate + "</td></tr>";
    }

    $("#detailList").append(str);
    $("#detailList").children().eq(0).children().eq(0).text(getData.kind1);
    $("#startDate").text(getData.OrderstartDate.substring(0,4)+"/"+getData.OrderstartDate.substring(4,6)+"/"+ getData.OrderstartDate.substring(6,8));
    $("#endDate").text(getData.OrderendDate);
    $("#value").text(getData.TotalValue);
    $("#name").text(getData.OrderName);
    $("#certifiType").text(getData.CertifiType);
    $("#certifiNumber").text(getData.CertifiNumber);
    $("#sex").text(getData.Sex);
    $("#birthday").text(getData.Birthday);
    $("#phoneNumber").text(getData.PhoneNumber);
    $("#email").text(getData.Email);
    $("#city").text(getData.City);

    $("#orderedName").text(getData.OrderedName);
    $("#relationship").text(getData.CertifiType);
    $("#certifiType2").text(getData.CertifiNumber);
    $("#certifiNumber2").text(getData.Sex);
    $("#birthday2").text(getData.Birthday);
    $("#sex2").text(getData.PhoneNumber);
    $("#phoneNumber2").text(getData.Email);
})