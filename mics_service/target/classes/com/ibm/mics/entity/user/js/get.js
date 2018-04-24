
		$(document).ready(function(){
			$('select[name=customQuotation11],select[name=customQuotation12],select[name=customQuotation21],select[name=customQuotation22],select[name=customQuotation31],select[name=customQuotation32],select[name=customQuotation33],select[name=customQuotation34]').change(function(){
				var value11 = $('select[name=customQuotation11]').val();
				var value12 = $('select[name=customQuotation12]').val();
				var value21 = $('select[name=customQuotation21]').val();
				var value22 = $('select[name=customQuotation22]').val();
				var value31 = $('select[name=customQuotation31]').val();
				var value32 = $('select[name=customQuotation32]').val();
				var value33 = $('select[name=customQuotation33]').val();
				var value34 = $('select[name=customQuotation34]').val();
				var value111 = value11/50000*43.8;
				var value121 = value12/2000*4;
				var value211 = value21/20*4;
				var value221 = value22/2000*1;
				var value311 = value31/500000*8;
				var value321 = value32/100000*1.5;
				var value331 = value33/100000*2.4;
				var value341 = value34/50000*2;
				var totalValue = value111+value121+value211+value221+value311+value321+value331+value341;
				$('#total,#chooseActualPremium').text(totalValue);
				var dayPay = totalValue/365;
				dayPay = dayPay.toFixed(2);
				$('.amountByDay').text(dayPay);
				console.log(totalValue);
				// $("#nextBtn").click(function(){
				// 	alert(value11+' '+value12+' '+value21+' '+value22+' '+value31+' '+value32+' '+value33+' '+value34);
				// })
			});
				$("#nextBtn").click(function(){
				objJSON={}; 	
				
				var orderDate = $('input[name=date]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON.date = orderDate;
				var ageType = $('select[name=ageType]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON.ageType = ageType;
				var personCount = $('select[name=personCount]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON.personCount = personCount;

				objJSON2={};

				var kind1 = "一般意外";
				objJSON2.保障类型 = kind1;
				var range11 = "意外身故、伤残";
				objJSON2.保障范围 = range11;
				var customQuotation11 = $('select[name=customQuotation11]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON2.customQuotation = customQuotation11;

				objJSON3={};

				objJSON3.保障类型 = kind1;
				var range12 = "意外医疗";
				objJSON3.保障范围 = range12;
				var customQuotation12 = $('select[name=customQuotation12]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON3.customQuotation = customQuotation12;

				objJSON4={};

				var kind2 = "津贴及车费";
				objJSON4.保障类型 = kind2;
				var range21 = "住院误工津贴";
				objJSON4.保障范围 = range21;
				var customQuotation21 = $('select[name=customQuotation21]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON4.customQuotation = customQuotation21;

				objJSON5={};

				objJSON5.保障类型 = kind2;
				var range22 = "救护车费用报销";
				objJSON5.保障范围 = range22;
				var customQuotation22 = $('select[name=customQuotation22]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON5.customQuotation = customQuotation22;

				objJSON6={};

				var kind3 = "交通意外";
				objJSON6.保障类型 = kind3;
				var range31 = "飞机意外身故、伤残";
				objJSON6.保障范围 = range31;
				var customQuotation31 = $('select[name=customQuotation31]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON6.customQuotation = customQuotation31;

				objJSON7={};

				objJSON7.保障类型 = kind3;
				var range32 = "火车意外身故、伤残";
				objJSON7.保障范围 = range32;
				var customQuotation32 = $('select[name=customQuotation32]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON7.customQuotation = customQuotation32;

				objJSON8={};

				objJSON8.保障类型 = kind3;
				var range33 = "船舶意外身故、伤残";
				objJSON8.保障范围 = range33;
				var customQuotation33 = $('select[name=customQuotation33]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON8.customQuotation = customQuotation33;

				objJSON9={};

				objJSON9.保障类型 = kind3;
				var range34 = "汽车意外身故、伤残";
				objJSON9.保障范围 = range34;
				var customQuotation34 = $('select[name=customQuotation34]').val().replace(/(^\s*)|(\s*$)/g, "");
				objJSON9.customQuotation = customQuotation34;

				objJSON10={};

				var totalVlue = $('#total').html();
				objJSON10.totalVlue = totalVlue;

				var obj = JSON.stringify(objJSON);
				var obj2 = JSON.stringify(objJSON2);
				var obj3 = JSON.stringify(objJSON3);
				var obj4 = JSON.stringify(objJSON4);
				var obj5 = JSON.stringify(objJSON5);
				var obj6 = JSON.stringify(objJSON6);
				var obj7 = JSON.stringify(objJSON7);
				var obj8 = JSON.stringify(objJSON8);
				var obj9 = JSON.stringify(objJSON9);
				var obj10 = JSON.stringify(objJSON10);

				// alert(obj);
				// console.log(objJSON9);
				// $("#clibut").value()=1;
				$("#clibut").text("obj") ;


				var students =  [ objJSON,objJSON2,objJSON3,objJSON4,objJSON5,objJSON6,objJSON7,objJSON8,objJSON9,objJSON10 ];
				students = JSON.stringify(students);//将JSON对象转化成字符串  
				window.location.href = "./orderContract.html";			  
				localStorage.setItem("合同",students);//用localStorage保存转化好的的字符串 
			});
		})
