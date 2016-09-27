var webURL="";
var usercode = sessionStorage.getItem("usercode");
//正则
var validate={
//手机号正则
		phone:function (phone){
        if(!/^0?(13|15|17|18|14|19)[0-9]{9}$/.test(phone)){
            return false;
        }
        return true;
  },
 //验证码正则
		code:function (code){
        if(!/\d{6}/.test(code)){
            return false;
        }
        return true;
  },
//邮箱正则
		emailbox:function (emailbox){
        if(!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(emailbox)){
            return false;
        }
        return true;
  },
//密码正则
 		userPassword:function (userPassword){
        if(!/[a-zA-Z\d+]{6,16}/.test(userPassword)){
            return false;
        }
        return true;
   },
//住址正则
 	address:function (address){
   if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(address)){
       return false;
   }
   return true;
  },	
//邮编正则
 	postcode:function (postcode){
   if(!/^[0-9][0-9]{5}$/.test(postcode)){
       return false;
   }
   return true;
  },
//支付宝正则
  		alipay:function (alipay){
       if(!/[a-zA-Z\d+]{6,16}/.test(alipay)){
           return false;
       }
      return true;
  },
//微信正则
 		weixin:function (emailbox){
       if(!/^[a-zA-Z\d_]{5,}$/.test(emailbox)){
           return false;
       }
       return true;
 },
//身份证正则
       Idcard:function (Idcard){
       if(!/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/.test(Idcard)){
           return false;
       }
       return true;
 },
//姓名正则
       name:function (name){
       if(!/^[\u4e00-\u9fa5]{0,}$/.test(name)){
           return false;
       }
       return true;
 }		
};
//登录
var phoneNub,PhonePassWord;
var PhoneWebNub,usercode;
$("#loginPhoneNumber").blur(function(){
	if (!validate.phone($("#loginPhoneNumber").val())){
       	    	$("#userphoneNub").html("请输入正确手机号"); 
       	    	$("#userphoneNub").css("color","red"); 
       	   		}
		 else{
		 	$("#userphoneNub").html("");   
		 	phonnub=$("#userphoneNub").val();
		 }
})
$("#loginCilck").click(function(){
	phoneNub=$("#loginPhoneNumber").val();
	PhonePassWord=$("#userpassWord").val();
	Post(phoneNub,PhonePassWord);
})
//登录
function Post(phoneNub,PhonePassWord){
	$.ajax({
		type:"post",
		url:webURL+"/service/user/login",
		data:{"phone":phoneNub,"password":PhonePassWord,"userCode":usercode},
		success:function(msg){
			var msg = msg;
			console.log(msg)
			var msgCode=msg.data.userCode;
			console.log(msgCode);
			if(msg.success==false){
				$("#userPassWord2").html(msg.message);
			}
			if(msg.success==true){
				$("#userPassWord2").html("");
			PhoneWebNub=phoneNub;
			sessionStorage.setItem("usercode",msgCode); 
			window.location.href="index.html";
			}
		}
	});
};

var usercode = sessionStorage.getItem("usercode");
personal()
purse()
//个人中心(获取数据)；
function personal(){
	console.log(usercode)
	$.ajax({
		type:"get",
		url:webURL+"/service/user/toMyself",
		data:{"userCode":usercode},
		success:function(msg){
			console.log(msg)
			$("#userName").val(msg.data.realName);
			$("#idno").val(msg.data.idno);
			$("#phone").val(msg.data.phone);
			$("#wxnum").val(msg.data.wxnum);
			$("#bankaccno").val(msg.data.bankaccno);
			$("#mailAddrss").val(msg.data.mailAddrss);
			$("#zipCode").val(msg.data.zipCode);
		}
	});
   }	
// //个人中心(上传数据)
// function blur( eventtt,p,fn,content){
// 	
//	eventtt.blur(function(){
////		alert("")
//		if( eventtt.val() == '' ){
//			alert(1)
//			p.html('');
//		}else{
//			 if( fn() ){
//			 	p.html("");
//			 }else{
//			 	p.html(content);
//			 	p.css("color","red"); 
//			 };	
//		}
//	});
//};
// //验证格式函数 
//blur($('#phone'),$('#phone_content'),validate.phone,'手机格式不正确');
//blur($('#wxnum'),$('#wxnum_content'),validate.weixin,'微信格式不正确');
//blur($('#bankaccno'),$('#bankaccno_content'),validate.alipay,'支付宝格式不正确');
//blur($('#mailAddrss'),$('#mailAddrss_content'),validate.address,'地址格式不正确');
//blur($('#zipCode'),$('#zipCode_content'),validate.postcode,'邮编格式不正确');



$('#phone').blur(function(){
	if($('#phone')==""){
		$('#phone_content').html('');
	}else{
		if(!validate.phone($("#phone").val())){
		$('#phone_content').html("手机格式不正确（非必填）");
		$('#phone_content').css("color","#2b71b1");
		}else{
		$('#phone_content').html("");
		}
	} 
});
$('#wxnum').blur(function(){
	console.log($('#wxnum').val())
	if(validate.weixin($("#wxnum").val())){
		$('#wxnum_content').html("微信格式不正确（非必填）");
		$('#wxnum_content').css("color","#2b71b1");
	}else if($('#wxnum').val()==""){
		$('#wxnum_content').html("");
	}else{
		$('#wxnum_content').html("");
	}
	
})
$('#bankaccno').blur(function(){
	console.log($('#bankaccno').val())
	if(!validate.alipay($("#bankaccno").val())){
		$('#bankaccno_content').html("支付宝格式不正确（非必填）");
		$('#bankaccno_content').css("color","#2b71b1");
	}else if($('#bankaccno').val()==""){
		$('#bankaccno_content').html("");
	}else{
		$('#bankaccno_content').html("");
	}
	
})
$('#mailAddrss').blur(function(){
	console.log($('#mailAddrss').val())
	if(!validate.address($("#mailAddrss").val())){
		$('#mailAddrss_content').html("地址有误（非必填）");
		$('#mailAddrss_content').css("color","#2b71b1");
	}else if($('#mailAddrss').val()==""){
		$('#mailAddrss_content').html("");
	}else{
		$('#mailAddrss_content').html("");
	}
	
})
$('#zipCode').blur(function(){
	console.log($('#zipCode').val())
	if(!validate.postcode($("#zipCode").val())){
		$('#zipCode_content').html("邮编格式有误（非必填）");
		$('#zipCode_content').css("color","#2b71b1");
	}else if($('#zipCode').val()==""){
		$('#zipCode_content').html("");
	}else{
		$('#zipCode_content').html("");
	}
	
})



   
// $("#sava").click(function(){
// 	if(validate.phone($("#phone").val())&&$("#userName").val()!=""){
// 		saveData();
// 	}else{
// 		alert("请完成必填项");
// 	}
// })
   
   
   
   
   
   
   
   
  //钱包 
function saveData(){
	console.log(usercode)
	$.ajax({
		type:"post",
		url:webURL+"/service/user/modifyuser",
		data:{
			"realName":$("#userName").val(),
			"idno":$("#idno").val(),
			"userCode":usercode,
			"wxnum":$("#wxnum").val(),
			"bankaccno":$("#bankaccno").val(),
			"mailAddrss":$("#mailAddrss").val(),
			"zipCode":$("#zipCode").val(),
			"mobileflag":"1",
		
		},
		success:function(msg){
			if(msg.success==true){
			alert(msg.message)
			window.location.href="purse.html"
			}else{
				alert(msg.message);
			}
			
//			$("#findTB").text(msg.data.total_amnt);
		}
	});
   }		 
   
   
   
   
   
   
//钱包 
function purse(){
	console.log(usercode)
	$.ajax({
		type:"post",
		url:webURL+"/service/comacc/findMyAcc",
		data:{"userCode":usercode},
		success:function(msg){
			console.log(msg)
			$("#findTB").text(msg.data.total_amnt);
		}
	});
   }		   
// 资料
$("#userName").blur(function(){
	if($("#userName").val()==""){
		$("#point_userName").html("此为必填项");
		$("#point_userName").css("color","red");
	}else{
		$("#point_userName").html("");
	}
})
$("#idno").blur(function(){
	if($("#idno").val()==""){
		$("#testCodeidno").html("此为必填项");
		$("#testCodeidno").css("color","red");
	}else{
		$("#testCodeidno").html("");
	}
})
$("#sava").click(function(){
	if($("#userName").val()==""){
		$("#point_userName").html("此为必填项");
		$("#point_userName").css("color","red");
	}
	if($("#idno").val()==""){
		$("#testCodeidno").html("此为必填项");
		$("#testCodeidno").css("color","red");
	}else{
		saveData();
	}
	
})
$("#dele").click(function(){
	window.location.href="login.html";
})
