//正则
var validate={
		phone:function (phone){
        if(!/^0?(13|15|17|18|14|19)[0-9]{9}$/.test(phone)){
            return false;
        }
        return true;
  },
		emailbox:function (emailbox){
        if(!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(emailbox)){
            return false;
        }
        return true;
  },
 		userPassword:function (userPassword){
        if(!/[a-zA-Z\d+]{6,16}/.test(userPassword)){
            return false;
        }
        return true;
   },
   
}
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
		data:{"phone":phoneNub,"password":PhonePassWord},
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
			window.location.href="index1.html";
			}
		}
	});
};

var usercode = sessionStorage.getItem("usercode");
personal()
purse()
//个人中心
function personal(){
	console.log(usercode)
	$.ajax({
		type:"get",
		url:webURL+"/service/user/toMyself",
		data:{"userCode":usercode},
		success:function(msg){
			console.log(msg)
			$("#userName").val(msg.data.userName);
			$("#idno").val(msg.data.userName);
			$("#phone").val(msg.data.phone);
			$("#idno").val(msg.data.userName);
			$("#wxnum").val(msg.data.wxnum);
			$("#bankaccno").val(msg.data.bankaccno);
			$("#mailAddrss").val(msg.data.mailAddrss);
			$("#zipCode").val(msg.data.zipCode);
		}
	});
   }		   
//钱包 
function purse(){
	console.log(usercode)
	$.ajax({
		type:"post",
		url:webURL+"service/comacc/findNums",
		data:{"userCode":usercode},
		success:function(msg){
			console.log(msg)
			$("#findTB").text(msg.data.findTB);
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
		alert("保存成功")
	}
	
})

