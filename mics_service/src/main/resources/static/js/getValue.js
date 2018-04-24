  $(document).ready(function() { //（无需触发，读取即响应）
      var students = localStorage.getItem("合同"); //取回students变量  

      students = JSON.parse(students); //把字符串转换成JSON对象  

      var text = JSON.stringify(students); //(将对象转化成文本对象)

      $("#h1").text(students.ageType);

      $("#h2").text(students.保障类型 + "  /" + students.保障类型 + "  /" + students.保障类型 + "  /" + students.保障类型 + " / " + students.保障类型 + "  /" + students.保障类型);

      $("#h3").text(students.保障范围 + "  /" + students.保障范围 + "  /" + students.保障范围 + "  /" + students.保障范围 + "  /" + students.保障范围 + "  /" + students.保障范围 + "  /" + students.保障范围);

      $("#h4").text(students.customQuotation + "  /" + students.customQuotation + "  /" + students.customQuotation + "  /" + students.customQuotation + "  /" + students.customQuotation + "  /" + students.customQuotation + "  /" + students.customQuotation + "  /" + students.customQuotation);


      $("#k1").text(students.kind1);
      $("#k2").text(students.kind2);
      $("#k3").text(students.kind3);
      $("#k4").text(students.kind4);
      $("#k5").text(students.kind5);
      $("#k6").text(students.kind6);
      $("#k7").text(students.kind7);
      $("#k8").text(students.kind8);

      $("#l1").text(students.range1);
      $("#l2").text(students.range2);
      $("#l3").text(students.range3);
      $("#l4").text(students.range4);
      $("#l5").text(students.range5);
      $("#l6").text(students.range6);
      $("#l7").text(students.range7);
      $("#l8").text(students.range8);

      $("#m1").text("0-" + students.customQuotation1 + "W");
      $("#m2").text("0-" + students.customQuotation2 + "W");
      $("#m3").text("0-" + students.customQuotation3 + "W");
      $("#m4").text("0-" + students.customQuotation4 + "W");
      $("#m5").text("0-" + students.customQuotation5 + "W");
      $("#m6").text("0-" + students.customQuotation6 + "W");
      $("#m7").text("0-" + students.customQuotation7 + "W");
      $("#m8").text("0-" + students.customQuotation8 + "W");

      $("#totalValue").text(students.totalVlue);

      var userData1 = localStorage.getItem("userData1");
      userData1 = JSON.parse(userData1); //把字符串转换成JSON对象  

      var text1 = JSON.stringify(userData1); //(将对象转化成文本对象)

      var userData2 = localStorage.getItem("userData");
      userData2 = JSON.parse(userData2); //把字符串转换成JSON对象  

      var text2 = JSON.stringify(userData2); //(将对象转化成文本对象)

      $("#name").text(userData2.orderName);
      $("#certifiNumber").text(userData2.certifiNumber);
      $("#phoneNumber").text(userData2.phoneNumber);
      $("#sex").text(userData2.sex);
      $("#city").text(userData2.city);
      $("#birthday").text(userData2.birthday);
      $("#certifiType").text(userData2.certifiType);
      $("#email").text(userData2.email);
      $("#certifiNumber2").text(userData2.certifiNumber2);
      $("#phoneNumber2").text(userData2.phoneNumber2);
      $("#sex2").text(userData2.sex2);
      $("#orderedName").text(userData2.orderedName);
      $("#birthday2").text(userData2.birthday2);
      $("#certifiType2").text(userData2.certifiType2);
      $("#relationship").text(userData2.relationship);
      var d0 = students.date.toString();
      var d1 = new Date(d0);
      var d2 = new Date(d1);
      d2.setFullYear(d2.getFullYear() + 1);
      d2.setDate(d2.getDate() - 1);
      var d3 = "" + d2.getFullYear() + "/";
      d3 += (d2.getMonth() + 1) + "/";
      d3 += d2.getDate();
      $("#date").text(students.date);
      $("#date2").text(d3);
      var formatedMonth = ("0" + (d2.getMonth() + 1)).slice(-2);
      var formatedMonth2 = ("0" + (d1.getMonth() + 1)).slice(-2);
      var nowDay1 = d2.getDate() < 10 ? "0" + d2.getDate() : d2.getDate();
      var nowDay2 = d1.getDate() < 10 ? "0" + d1.getDate() : d1.getDate();
      var d4 = "" + d2.getFullYear() + formatedMonth + nowDay1;
      var d5 = "" + d1.getFullYear() + formatedMonth2 + nowDay2;
      console.log(d5);
      console.log(d4);



      $("#subButton").click(function() {
          contractForm = {};
          for (var i = 1; i <= 8; i++) {
              var kind = 'kind' + i;
              contractForm[kind] = $("#k" + i).html().replace(/(^\s*)|(\s*$)/g, "");
              var range = 'range' + i;
              contractForm[range] = $("#l" + i).html().replace(/(^\s*)|(\s*$)/g, "");
              var customQuotation = 'customQuotation' + i;
              contractForm[customQuotation] = students[customQuotation];
          }
          console.log(d4);
          console.log(d5);
          contractForm.date = $("#date").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.totalValue = $("#totalValue").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.orderstartDate = d5;
          contractForm.orderendDate = d4;
          contractForm.city = $("#city").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.orderName = $("#name").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.certifitype = $("#certifiType").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.certifiNumber = $("#certifiNumber").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.sex = $("#sex").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.birthday = $("#birthday").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.phonenumber = $("#phoneNumber").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.email = $("#email").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.orderedName = $("#orderedName").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.relationship = $("#relationship").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.certifiType2 = $("#certifiType2").html();
          contractForm.certifiNumber2 = $("#certifiNumber2").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.birthday2 = $("#birthday2").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.sex2 = $("#sex2").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.phoneNumber2 = $("#phoneNumber2").html().replace(/(^\s*)|(\s*$)/g, "");
          contractForm.contractState = 1;
          console.log(contractForm);
          $.ajax({
              type: 'post',
              data: contractForm,
              url: '/saveorderInfo',
              success: function(data) {
                  if (data == "success") {
                      alert("提交成功");
                      window.location.href = "./index";
                  }
              }

          })
      });
  })