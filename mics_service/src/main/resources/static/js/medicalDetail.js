$(document).ready(function() {
    var getData = localStorage.getItem("medicalcareDetail");
    getData = JSON.parse(getData);
    var s = "";
    var i = "";
    $("#name").text(getData.PatientName);
    $("#age").text(getData.Age);
    $("#sex").text(getData.Sex3);
    $("#certifiType").text(getData.CertifyType3);
    $("#certifiNumber").text(getData.CertifiNumber3);
    $("#type").text(getData.Type_value);
    $("#range").text(getData.Range_value);
    $("#kind").text(getData.Kind);
    $("#payment").text(getData.Payment);
    $("#zhuyuan").text(getData.In_hospital);
    $("#visitTime").text(getData.VisitTime.substring(0,4)+"/"+getData.VisitTime.substring(4,6)+"/"+ getData.VisitTime.substring(6,8));
    $("#describe").text(getData.Describe);
    $("#history").text(getData.History);
})