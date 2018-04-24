  $(document).ready(function(){//（无需触发，读取即响应）
        var students = localStorage.getItem("合同");//取回students变量  
   
        students = JSON.parse(students);//把字符串转换成JSON对象  
       
        var text = JSON.stringify(students);//(将对象转化成文本对象)
         console.log(students);//打印出对象(JS里面只有对象)
         console.log(text);//（打印出字符串）


        $("#h1").text(students[0].ageType);

        $("#h2").text(students[1].保障类型+"  /" +students[2].保障类型+"  /" +students[3].保障类型+"  /" +students[4].保障类型+" / " +students[5].保障类型+"  /" +students[6].保障类型);

        $("#h3").text(students[1].保障范围+"  /" +students[2].保障范围+"  /" +students[3].保障范围+"  /" +students[4].保障范围+"  /" +students[5].保障范围+"  /" +students[6].保障范围+"  /" +students[7].保障范围);

        $("#h4").text(students[1].customQuotation+"  /" +students[2].customQuotation+"  /" +students[3].customQuotation+"  /" +students[4].customQuotation+"  /" +students[5].customQuotation+"  /" +students[6].customQuotation+"  /" +students[7].customQuotation+"  /" +students[8].customQuotation);    
       

         $("#k1" ).text(students[1].保障范围);
         $("#k2" ).text(students[2].保障范围);
         $("#k3" ).text(students[3].保障范围);
         $("#k4" ).text(students[4].保障范围);
         $("#k5" ).text(students[5].保障范围);
         $("#k6" ).text(students[6].保障范围);
         $("#k7" ).text(students[7].保障范围);
         $("#k8" ).text(students[8].保障范围);

         $("#l1" ).text(students[1].保障类型);
         $("#l2" ).text(students[2].保障类型);
         $("#l3" ).text(students[3].保障类型);
         $("#l4" ).text(students[4].保障类型);
         $("#l5" ).text(students[5].保障类型);
         $("#l6" ).text(students[6].保障类型);
         $("#l7" ).text(students[7].保障类型);
         $("#l8" ).text(students[8].保障类型);

         $("#m1" ).text(students[1].customQuotation);
         $("#m2" ).text(students[2].customQuotation);
         $("#m3" ).text(students[3].customQuotation);
         $("#m4" ).text(students[4].customQuotation);
         $("#m5" ).text(students[5].customQuotation);
         $("#m6" ).text(students[6].customQuotation);
         $("#m7" ).text(students[7].customQuotation);
         $("#m8" ).text(students[8].customQuotation);

         $("#totalValue" ).text(students[9].totalVlue);

         var userData1 = localStorage.getItem("userData1");
         userData1 = JSON.parse(userData1);//把字符串转换成JSON对象  
       
         var text1 = JSON.stringify(userData1);//(将对象转化成文本对象)
         console.log(userData1);//打印出对象(JS里面只有对象)
         console.log(text1);//（打印出字符串）

         var userData2 = localStorage.getItem("userData2");
         userData2 = JSON.parse(userData2);//把字符串转换成JSON对象  
       
         var text2 = JSON.stringify(userData2);//(将对象转化成文本对象)

         $("#name").text(userData1.orderName);
         $("#certifiNumber").text(userData1.certifiNumber);
         $("#phoneNumber").text(userData1.phoneNumber);
         $("#sex").text(userData1.sex);
         $("#city").text(userData1.city);
         $("#birthday").text(userData1.birthday);
         $("#certifiType").text(userData1.certifiType);
         $("#email").text(userData1.email);
         $("#certifiNumber2").text(userData2.certifiNumber2);
         $("#phoneNumber2").text(userData2.phoneNumber2);
         $("#sex2").text(userData2.sex2);
         $("#orderedName").text(userData2.orderedName);
         $("#birthday2").text(userData2.birthday2);
         $("#certifiType2").text(userData2.certifiType2);
         $("#relationship").text(userData2.relationship);
         $("#date").text(students[0].orderstartDate);
         $("#date2").text(students[0].orderendDate);

         console.log(students[0].date);

          

})  