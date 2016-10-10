$(document).ready(function(){
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
        if(!/^[a-zA-Z]\w{5,17}$/.test(userPassword)){
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
   if(!/^[a-zA-Z0-9 ]{3,12}$/.test(postcode)){
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
	
    //通过session获取usercode
	var usercode = sessionStorage.getItem("usercode");
	var userPhone = sessionStorage.getItem("userPhone");
	var zongUse;
	
   //我的帐户交互
	$.ajax({
   	  	type:"post",
   	  	url:webURL+"/service/comacc/findMyAcc",
   	  	data:{
   	  		 "userCode": usercode
   	  	},
   	  success:function(msg1){
   	  	 
  			console.log(msg1);
			console.log(msg1.data);
			if ( msg1.success == true ) {
			zongUse = msg1.data.total_amnt ;
		    $('#total1').html( msg1.data.total_amnt );
            $('#available').html( msg1.data.avb_amnt );
            $('#frozen').html( msg1.data.froze_amnt );
            $('#totalReward1').html( msg1.data.totalReward );
            $('#totalGMJL1').html( msg1.data.totalGMJL );
            $('#telePhone').html( userPhone );
			}
           
        return false;
  		}
   	  	
   	  });
   	  
   	//判断新旧密码可一致
   	$('#newPassword').on('blur',function(){ 
   	   if( $('#oldPassword').val() ==  $('#newPassword').val() ){
   	   	  $('#rePsd').html( '新密码和原密码不能一致' );
   	   	 //判断两次密码可一致
   	   	   
	   	$('#resNewpassword').on('blur',function(){
	   	   if( $('#resNewpassword').val() !=  $('#newPassword').val() ){
	   	   	  $('#rePsd').html( '两次密码不一致' );
	   	   }else{
	   	   	   $('#rePsd').html( '' );
	   	   }
	   	});
   	   }else{
   	   	   $('#rePsd').html( '' );
   	   }
   	});
   	
  
  
	
   //修改密码交互
  $('#confirmChange').on('click',function(){
   if(  $('#rePsd').html() == '' ){
  	$.ajax({
  		type:"post",
  		url:webURL+"/service/user/modifypwd",
  		data:{
          "userCode": usercode,
          "oldpassword":$('#oldPassword').val(),
          "newpassword":$('#newPassword').val(),
          "resnewpassword":$('#resNewpassword').val(),
          "mobileflag": 1
 		},
  		success:function(msg){
  			console.log( msg );
  			alert( msg.message );
  			if( msg.success == true ){
  				window.location.href="login.html";
  			}
  		}
  		
  		
  	});
   };
  });
 
  
   
   // 提交转账
   $('#submit1').on("click",function(){
   	  if( parseInt( $('#lines').val() )  > zongUse ){
   	  	alert( '可用额度不足' );
   	  }else{
   	  	alert( '此功能未开通' );
   	  }
   });
   
   $('.transferHead').on('click',function(){
   	window.location.href="Myaccount.html";
   });



});