		   
		   
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
   if(!/^[\[1-9]\d{5}(?!\d) /.test(postcode)){
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
       if(!/^[a-z_\d]+$/.test(emailbox)){
           return false;
       }
       return true;
 },
//身份证正则
       Idcard:function (Idcard){
       if(!/^\d{15}|\d{18}$/.test(Idcard)){
           return false;
       }
       return true;
 },
//姓名正则
       name:function (name){
       if(!/\u4e00-\u9fa5/.test(name)){
           return false;
       }
       return true;
 }		
};

//不输入或者输入正确
function blur( event,key,p,fn,content){
	event.blur(function(){
		if( key.val() == '' ){
			p.html('');
		}else{
			 if( fn() ){}else{
			 	p.html('content');
			 	p.css("color","red"); 
			 };	
		}
	});
};

//必须输入或者输入正确
function blur1( event,p,fn,content){
	event.blur(function(){
		if( event.val() == '' ){
			p.html('这是必填项');
			p.css("color","red"); 
		}else{
			 if( fn(event.val()) ){p.html('');}else{
			 	p.html(content);
			 	p.css("color","red"); 
			 };	 
		}
	});
};



//验证格式函数 
blur1($('#pho_number1'),$('#point_phonnumber'),validate.phone,'手机格式不正确');
blur1($('#pho_password3'),$('#userPassWord1'),validate.userPassword,'密码格式不正确');
blur1($('.code'),$('#testCodeText'),validate.code,'验证码格式不正确');
//blur1($('#pho_password4'),$('#userPassWord2'),validate.userPassword,'两次密码不一致');
blur1($('#phone4'),$('#userPassWord3'),validate.phone,'手机格式不正确');

//获取验证码
   var code;
	$('#testBtn').on('click',function(){
		console.log( validate.phone($('#pho_number1').val() ) );
		if( validate.phone($('#pho_number1').val() ) ){	
			
		$.ajax({
			type:"post",
			url:webURL+"/service/user/sendsms",
			data:{"phone":$('#pho_number1').val()},
			success:function( content ){
		       	 
		        console.log(content );
		        
		        if(content.errorCode==0){
		        	tesSetIn()
		        }
	       }	
		});
	}
	
});

//判断两次密码是否一致
$('#pho_password4').blur( function(){
	if( !($('#pho_password3').val() === $('#pho_password4').val()) ){
		$('#userPassWord2').html('两次密码是否一致');
	}else{
		$('#userPassWord2').html('');
	}
} );


//倒计时
function tesSetIn(){
	var total=60;
	var timer = setInterval(function(){
		if(total == 0) {
			total="重新发送";
			$("#testBtn").removeAttr("disabled");
			
			clearInterval(timer);//如果程序在上一行出现错误，这一行代码就无法执行
		}else if(total> 0){
			$("#testBtn").attr("disabled","disabled");
		
		}
		$("#testBtn").html(total);
		total--;
	},1000);
}


//普通会员交互
$('#reg1').click(function(){
	$(this).addClass('bg-ccc');

	if( $('#pho_password3').val() ){
			$(this).disabled=false;
		$.ajax({
	 		type:"post",
	 		url:webURL+"/service/user/register",
	 		data:{
	             "phone":$('#pho_number1').val(),
	             "password":$('#pho_password3').val(),
	             "resPassword":$('#pho_password4').val(),
	             "yzm":$('#pho_code').val(),
	            "phone1":$('#phone4').val(),
	             "userType":1
	 		},
	        success:function(msg){
	         // $(this).attr("disabled","disabled");
	       	 console.log(msg);
//	       	 $(this).unbind('click');
	       	if( msg.success == true ){
	       		alert(msg.message);
	       		window.location.href="login.html";
	       		$('#reg1').disabled=false;
	       		$('#reg1').removeClass('bg-ccc');
	       	}else{
	       		alert(msg.message);
	       		$('#reg1').disabled=false;
	       		$('#reg1').removeClass('bg-ccc');
	       		
	       	}
	       }	
  		 });
 	}	
 	
});

var phoneNub,PhonePassWord;
var PhoneWebNub;
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
	Post(phoneNub,PhonePassWord)
})

function Post(phoneNub,PhonePassWord){
	$.ajax({
		type:"post",
		url:webURL+"/service/user/login",
		data:{"phone":phoneNub,"password":PhonePassWord},
		success:function(msg){
			var msg = msg;
			console.log(msg)
			if(msg.success==false){
				$("#userPassWord2").html(msg.message);
			}
			if(msg.success==true){
			PhoneWebNub=phoneNub;
			window.location.href="index.html";
			}
		}
	})
};

		   
		   
  
		   
		   
		   
		   
		   
		   
