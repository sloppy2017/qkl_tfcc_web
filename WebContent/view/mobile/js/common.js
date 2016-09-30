
var usercode = sessionStorage.getItem("usercode");
var userPhone = sessionStorage.getItem("userPhone");
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

//下拉框
		$(".orclick").click(function(){
			$(".menu").slideToggle("slow");
		})
		
//手机号

