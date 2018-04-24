$(document).ready(function(){
	var students = localStorage.getItem("合同");//取回students变量  
   
    students = JSON.parse(students);//把字符串转换成JSON对象  
   
    var text = JSON.stringify(students);//(将对象转化成文本对象)
    console.log(students);//打印出对象(JS里面只有对象)
    console.log(text);//（打印出字符串）

	var d0 = students[0].date.toString();
   	var d1 = new Date(d0);
  	var d2 = new Date(d1);
  	d2.setFullYear(d2.getFullYear()+1);
  	d2.setDate(d2.getDate()-1);
  	var d3 = ""+d2.getFullYear() + "/";
  	d3 += (d2.getMonth()+1) + "/";
  	d3 += d2.getDate();
    $("#date").text(students[0].date);
    $("#date2").text(d3);
    $("#totalValue").text(students[9].totalVlue);

	$("#submitButton").click(function(){

		userData1={}; 

		var orderstartDate = $('#date').val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.orderstartDate = orderstartDate;

		var orderendDate = $('#date2').val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.orderendDate = orderendDate;

		var orderName = $("input[name=orderName]").val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.orderName = orderName;

		var certifiType = $("select[name=certifitype]").val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.certifiType = certifiType;

		var certifiNumber = $('input[name=certifiNumber]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.certifiNumber = certifiNumber;

		var birthday = $('input[name=birthday]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.birthday = birthday;

		var phoneNumber = $('input[name=phonenumber]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.phoneNumber = phoneNumber;

		var sex = $('select[name=sex]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.sex = sex;

		var eMail = $('input[name=email]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.email = eMail;

		var city = $('input[name=city]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.city = city;

		var userData1 = JSON.stringify(userData1);
		localStorage.setItem("userData1",userData1);

		userData2={}; 

		var orderedName = $('input[name=orderedName]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData2.orderedName = orderedName;

		var relationship = $('select[name=relationship]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData2.relationship = relationship;

		var certifiType2 = $('select[name=certifiType2]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData2.certifiType2 = certifiType2;

		var birthday2 = $('input[name=birthday2]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData1.birthday2 = birthday2;

		var sex2 = $('select[name=sex2]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData2.sex2 = sex2;

		var certifiNumber2 = $('input[name=certifiNumber2]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData2.certifiNumber2 = certifiNumber2;

		var phoneNumber2 = $('input[name=phoneNumber2]').val().replace(/(^\s*)|(\s*$)/g, "");
		userData2.phoneNumber2 = phoneNumber2;

		var userData2 = JSON.stringify(userData2);
		localStorage.setItem("userData2",userData2);

		window.location.href = "/contract.html";
	})

})