var phoneNub,PhonePassWord;
var PhoneWebNub,usercode,userPhone;
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
		url:"/service/user/login",
		data:{"phone":phoneNub,"password":PhonePassWord,"userCode":usercode},
		success:function(msg){
			var msg = msg;
			console.log(msg)
			var msgCode=msg.data.userCode;
			var msgPhone=msg.data.phone;
			console.log(msgCode);
			if(msg.success==false){
				$("#userPassWord2").html(msg.message);
			}
			if(msg.success==true){
				$("#userPassWord2").html("");
			PhoneWebNub=phoneNub;
			sessionStorage.setItem("usercode",msgCode); 
			sessionStorage.setItem("userPhone",msgPhone); 
			window.location.href="myheart.html";
			}
		}
	});
};