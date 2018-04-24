$(document).ready(function() {

    $.ajax({
        type: 'post',
        data: {},
        url: '/configuration',
        success: function(data) {
            var obj = eval("(" + data[0] + ")");
            var obj2 = eval("(" + data[1] + ")");
            for (var i = 1; i <= 8; i++) {
                var kind = 'Kind' + i;
                var range = 'Range' + i;
                var customQuotation = 'CustomQuotation' + i;
                $("#m" + i).text(obj[customQuotation]);
                $("#k" + i).text(obj[kind]);
                $("#l" + i).text(obj[range]);
            }
            $("#k1").text(obj.kind1);
            $("#date").text(obj.OrderstartDate);
            $("#date2").text(obj.OrderendDate);
            $("#value").text(obj.TotalValue);
            $("#name").text(obj.OrderName);
            $("#certifiType").text(obj.CertifiType);
            $("#certifiNumber").text(obj.CertifiNumber);
            $("#sex").text(obj.Sex);
            $("#birthday").text(obj.Birthday);
            $("#phoneNumber").text(obj.PhoneNumber);
            $("#email").text(obj.Email);
            $("#city").text(obj.City);

            $("#orderedName").text(obj.OrderedName);
            $("#relationship").text(obj.Relationship);
            $("#certifiType2").text(obj.CertifiType2);
            $("#certifiNumber2").text(obj.CertifiNumber2);
            $("#birthday2").text(obj.Birthday2);
            $("#sex2").text(obj.Sex2);
            $("#phoneNumber2").text(obj.PhoneNumber2);

            $("#Kind").text(obj2.Kind);
            $("#Range_value").text(obj2.Range_value);
            $("#Payment").text(obj2.Payment);
            $("#PatientName").text(obj2.PatientName);
            $("#CertifyType3").text(obj2.CertifyType3);
            $("#CertifiNumber3").text(obj2.CertifiNumber3);
            $("#Age").text(obj2.Age);
            $("#Sex3").text(obj2.Sex3);
            $("#In_hospital").text(obj2.In_hospital);
            $("#Type_value").text(obj2.Type_value);
            $("#VisitTime").text(obj2.VisitTime);
            $("#Describe").text(obj2.Describe);
            $("#History").text(obj2.History);

            $("#sub").click(function() {
                information = {};
                information.ContractId = obj.ContractId;
                information.medicalcareID = obj2.medicalcareID;
                information.result = $("#checkResult").val();
                information.state = $('select[name=result]').val();
                $.ajax({
                    type: 'post',
                    data: information,
                    url: '/updataClaim',
                    success: function(data) {
                        if (data == "ok") {
                            window.location.href = "./underwriter";
                        } else {
                            alert("失败");
                        }
                    }
                })
            })
        }
    })
});