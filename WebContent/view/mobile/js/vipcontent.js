$(document).ready(function(){
	var bankaccno,wxnum,mailAddrss;
	//会员信息
	vipName();
	vipmu();
	$("#mailAddrss").blur(function(){
		if(!validate.address($('#mailAddrss').val())){
			alert("请输入正确地址");
		}else{
			mailAddrss = $("#mailAddrss").val();
		}
	});
	$("#bankaccno").blur(function(){
		if(!validate.alipay($('#bankaccno').val())){
			alert("请输入正确支付宝账号");
		}else{
			bankaccno = $("#bankaccno").val();
		}
	})
	$("#wxnum").blur(function(){
		if(!validate.weixin($('#wxnum').val())){
			alert("请输入正确微信账号");
		}else{
			wxnum = $("#wxnum").val();
		}
	})

	
	$("#vipSave").click(function(){
		bankaccno = $("#bankaccno").val();
		wxnum = $("#wxnum").val();
		mailAddrss = $("#mailAddrss").val();
		if(bankaccno!=""&&wxnum!=""&&mailAddrss!=""){
			vipbaocun();
		}else{
			alert("请填写完整")
		}
		
	})
	
	
	
	
	
	
	
	
	
//	姓名
function vipName(){
	$.ajax({
		type:"get",
		url:webURL+"/service/user/toMyself",
		data:{"userCode":usercode},
		success:function(msg){
			console.log(msg)
			if(msg.data.realName==""){
				$("#realName").text("未认证");
				$("#realName").css("color","red")
			}else{
				$("#realName").text(msg.data.realName);
			}
			$("#vipphone").text(msg.data.phone)
		}
	});
};
//我的资料
function vipmu(){
	$.ajax({
		type:"get",
		url:webURL+"/service/user/toMyself",
		data:{"userCode":usercode},
		success:function(msg){
			console.log(msg);
			$("#bankaccno").val(msg.data.bankaccno);
			$("#wxnum").val(msg.data.wxnum);
			$("#mailAddrss").val(msg.data.mailAddrss)
		}
	});
};
//保存用户
function vipbaocun(){
	$.ajax({
		type:"post",
		url:webURL+"/service/user/modifyuser",
		data:{"userCode":usercode,"bankaccno":bankaccno,"wxnum":wxnum,"mailAddrss":mailAddrss,},
		success:function(msg){
			alert(msg.message)
			console.log(msg);
		}
	});
};


})