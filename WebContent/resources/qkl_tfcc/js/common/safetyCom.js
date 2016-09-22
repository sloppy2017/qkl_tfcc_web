$(function(){
      $('.pwd').click(function(){
          $(this).hide();
          $('.main-top ,.amend').hide();
          $('.pwd').show();
      });
        $('.phone').click(function(){
            $(this).hide();
            $('.main-top ,.amend').hide();
            $('.rephone').show();
        });
        $('.renzhen').click(function(){
            $(this).hide();
            $('.main-top ,.amend').hide();
            $('.rerenzhen').show();
        });
        
        getUserInfo();
    });

/**
 * 定时器
 */
var phone = "";
var InterValObj=null; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount='';//当前剩余秒数
/**
 * 安全中兴表单验证
 */
// 手机验证
function valid_phone(){
    var oldphone = $(".oldphone").text().trim();
	var $this = $('.phone-safety');
	$this.val($this.val().trim());
    var phoneVal= $this.val().trim();
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
    if(phoneVal==''){
    	$this.val("请输入手机号");
    	$this.addClass("errorTip");
        return false;
    }else if(phoneVal.length!=11||!myreg.test(phoneVal)){
    	$this.val("请输入正确的手机号");
    	$this.addClass("errorTip");
        return false;
    }else if(oldphone === phoneVal){
    	$this.val("新手机号不能与旧手机号一致");
    	$this.addClass("errorTip");
   
    }else{
    	$this.removeClass("errorTip");
    	return true;
    }
}
//获取验证码
function getVCode(phone) {
	console.log(phone);
	var validPhone = valid_phone();//验证手机号是否正确
    if(!validPhone){
        return;
    }
	curCount = count;
    //删除input的属性值是在60内不能重新点击
    $(".yzm-safety").attr("disabled", "true");
    $(".yzm-safety").val("请在" + curCount + "秒内输入验证码");
    clearInterval(InterValObj);
    InterValObj=setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    //向后台发送处理数据
    
    $.ajax({
        type:'POST',
        url:'/service/user/sendsms?phone='+phone,
//         data:{phone:newPhone},
        dataType:'JSON',
        success: function (json) {
            // 验证码发送成功，把验证码先存到页面一个变量中       
             if(json.success){
                // settime(this);
             }else{
                 alert(json.message);
             }
            //console.log(data);
        },
        error: function (a,b,c) {
     	   //console.log(a.readyState+"\n"+b+"\n"+c);
            console.log("获取短信验证码失败");
        }
    });
}
//timer处理函数
function SetRemainTime() {
    if (curCount == 0) {
        clearInterval(InterValObj);       //停止计时器
        $(".yzm-safety").removeAttr("disabled"); //启用按钮
        $(".yzm-safety").val("重新发送验证码");
    } else {
        curCount--;
        $(".yzm-safety").val("请在" + curCount + "秒内输入验证码");
    }
}


$('input[name="yzm"]').blur(function(){
	return valid_yzm("yzm");
});
//验证验证码
function valid_yzm(id){
	var $this = $("#"+id);
	$this.val($this.val().trim());
	var code = $this.val().trim();
	var reg = /^\d[0-9]{5}$/;
    if(code==''){
    	$this.val('请输入验证码');
    	$this.addClass('errorTip');
        return false;
    }else if(!reg.test(code)){
    	$this.val('验证码格式不正确');
    	$this.addClass('errorTip');
        return false;
    }else{
    	$this.removeClass('errorTip');
        return true;
    }
}

function getUserInfo(){
  var url = "/service/user/getUserInfo";
  $.getJSON(url,function(json){
      if(json.success){
    	 /* if(json.data.userType==1){
        	  $(".myImg").children("p").html("普通会员");
          }else if(json.data.userType==2){
        	  $(".myImg").children("p").html("网点会员");
          }else if(json.data.userType==3){
        	  $(".myImg").children("p").html("LP会员");
          }else if(json.data.userType==4){
              $(".myImg").children("p").html("投资公司");
          }else if(json.data.userType==5){
              $(".myImg").children("p").html("众筹会员");
          }*/
    	  if(json.data.imgAddrss){
    		  $("#left_headPicId").attr("src",json.data.imgAddrss);
    	  }else{
              $("#left_headPicId").attr("src","/resources/qkl_tfcc/imgs/LPtouxiang.jpg");
          }
    	  if(json.data.userName){
              $("#user").html(json.data.userName);
          }else if(json.data.realName){
              $("#user").html(json.data.realName);
          }else{
        	  $("#user").html(json.data.phone);
          }
    	  phone = json.data.phone;
    	  if(json.data.realName!=null&&json.data.realName.trim()!=''&&json.data.realName.trim()!=undefined){
    		  $('.renzhen').unbind("click");
    		  $("#renzheng").text("已验证");
    		  $("#renzheng").next("a").text("已认证");
    		  $("#renzheng").next("a").css("background","url('/resources/qkl_tfcc/imgs/grey.png')no-repeat 0px");
    	  }
      }else{
    	  alert(json.message);
      }
      
  });
}
//获取原手机号
function setPhone(){
   $("#oldphone").text(phone);
}
//修改手机号
function modifyPhone(){
   var validPhone = valid_phone();//验证手机号是否正确
   var validYzm = valid_yzm('yzm');//验证验证码
   if(!validPhone||!validYzm){
       return;
   }
   $.ajax({
       type:'POST',
       url:'/service/user/modifyphone?'+$("#form2").serialize()+"&oldphone="+$("#oldphone").text().trim(),
       dataType:'JSON',
       success: function (json) {
            if(json.success){
            	$(".mark1").css("display","block");
            }else{
                if(json.errorCode==-13){
                	 $("#yzm").addClass("errorTip");
                	 $("#yzm").val(json.message);
                }else{
                	$("#phone").addClass("errorTip");
                    $("#phone").val(json.message);
                }
            }
           //console.log(data);
       },
       error: function (a,b,c) {
           //console.log(a.readyState+"\n"+b+"\n"+c);
           console.log("修改手机号失败");
       }
   });
}
//修改密码
function modifypwd(){
   var valid1 = valid_oldpassword($("#oldpassword"));
   var valid2 = valid_newpassword($("#newpassword"));
   var valid3 = valid_resnewpassword($("#resnewpassword"));
   if(!(valid1&&valid2&&valid3)){
	   return;
   }
   $.ajax({
       type:'POST',
       url:'/service/user/modifypwd?'+$("#form3").serialize(),
//        data:$("#form2").serialize(),
       dataType:'JSON',
       success: function (json) {
            if(json.success){
                $(".mark2").css("display","block");
            }else{
                alert(json.message);
            }
           //console.log(data);
       },
       error: function (a,b,c) {
           //console.log(a.readyState+"\n"+b+"\n"+c);
           console.log("修改登陆密码失败");
       }
   });
}
//实名认证
function realname(){
   var valid1 = valid_realName($("#realName"));	  
   var valid2 = valid_idno($("#idno"));	 
   if(!(valid1&&valid2)){
	   return;
   }
   var url = '/service/user/realname?'+$("#form1").serialize();
   url = encodeURI(url);
   $.ajax({
       type:'POST',
       url:url,
//        data:$("#form2").serialize(),
       dataType:'JSON',
       success: function (json) {
            if(json.success){
            	$(".mark").css("display","block");
            }else{
                alert(json.message);
            }
           //console.log(data);
       },
       error: function (a,b,c) {
           //console.log(a.readyState+"\n"+b+"\n"+c);
           console.log("修改登陆密码失败");
       }
   });
}
function toPart1(obj){
   location.reload();
   /* var markDiv = $(obj).parent().parent();
   markDiv.css("display","none");
   $(".rerenzhen,.rephone,.pwd").hide();
   $(".main-top,.amend,.btn").show(); */
}

/**
 * 密码认证
 */
//原密码
$('#oldpassword').blur(function () {
   return valid_oldpassword($(this));
});
function valid_oldpassword($this){
 //密码正则6-20字母数字或特殊字符
   $this.val($this.val().trim());
   /* var reg = /^([^a-zA-Z]|[^\d]|[^a-zA-Z0-9]){6,20}$/;
   if ($this.val() == '') {
	   $this.next().text('请输入原密码');
	   $this.addClass("errorTip");
       return false;
   }else if (!reg.test($this.val())) {
	   $this.next().text('请输入6~20位字母、数字或字符组合');
	   $this.addClass("errorTip");
       return false;
   }else {
       if($this.hasClass('errorTip')){
    	   $this.removeClass("errorTip");
    	   $this.next().html('');
       }
       return true;
   } */
   var result = valid_password($this.val());
   if ($this.val() == '') {
       $this.next().text('密码不能为空');
       $this.addClass("errorTip");
       return false;
   }else if(!result){
       $this.next().text('请输入6~20位字母、数字或字符组合');
       $this.addClass("errorTip");
       return false;
   }else{
	   if($this.hasClass('errorTip')){
           $this.removeClass("errorTip");
           $this.next().html('');
       }
       return true;
   }
   
}

//新密码
$('#newpassword').blur(function(){
   return valid_newpassword($(this));
});
function valid_newpassword($this){
   $this.val($this.val().trim());
   //密码正则6-16字母数字或特殊字符
   /* var reg = /^[a-zA-Z][a-zA-Z0-9|*|&|%|.|@|!]{5,20}$/;
   if ($this.val() == '') {
	   $this.next().text('请输入新密码');
	   $this.addClass("errorTip");
       return false;
   }else if (!reg.test($(this).val())) {
	   $this.next().text('密码是以字母开头的6-16数字字母或特殊字符');
	   $this.addClass("errorTip");
       return false;
   }else{
       if($this.hasClass('errorTip')){
    	   $this.removeClass("errorTip");
    	   $this.next().html('');
       }
       return true; 
   } */
   var result = valid_password($this.val());
   if ($this.val() == '') {
       $this.next().text('密码不能为空');
       $this.addClass("errorTip");
       return false;
   }else if(!result){
       $this.next().text('请输入6~20位字母、数字或字符组合');
       $this.addClass("errorTip");
       return false;
   }else{
       if($this.hasClass('errorTip')){
           $this.removeClass("errorTip");
           $this.next().html('');
       }
       return true;
   }
}


//确认密码
$('#resnewpassword').blur(function(){
    return valid_resnewpassword($(this));
});
function valid_resnewpassword($this){
    $this.val($this.val().trim());
    var newpassword = $("#newpassword").val().trim();
    //密码正则6-16字母数字或特殊字符
    /* var reg = /^[a-zA-Z][a-zA-Z0-9|*|&|%|.|@|!]{5,15}$/;
    if ($this.val() == '') {
    	$this.next().next().text('请输入确认密码');
        $this.addClass("errorTip");
        return false;
    }else if (!reg.test($this.val())) {
    	$this.next().next().text('密码是以字母开头的6-16数字字母或特殊字符');
        $this.addClass("errorTip");
        return false;
    }else if($this.val()!=newpassword){
    	$this.next().next().text('两次密码不一致');
        $this.addClass("errorTip");
        return false;
    }else{
        if($this.hasClass('errorTip')){
            $this.removeClass("errorTip");
            $this.next().next().html('');
        }
        return true; 
    } */
    var result = valid_password($this.val());
    if ($this.val() == '') {
        $this.next().next().text('密码不能为空');
        $this.addClass("errorTip");
        return false;
    }else if(!result){
        $this.next().next().text('请输入6~20位字母、数字或字符组合');
        $this.addClass("errorTip");
        return false;
    }else if($this.val()!=newpassword){
        $this.next().next().text('两次密码不一致');
        $this.addClass("errorTip");
        return false;
    }else{
        if($this.hasClass('errorTip')){
            $this.removeClass("errorTip");
            $this.next().next().html('');
        }
        return true;
    }
}
function valid_password(str){
    var is=true;
    //去除前后空格
    str=str.replace(/(^\s*)|(\s*$)/g,'');
    //位数不对，设置为false
    if(str.length<6 || str.length>20){
        is=false;   
    };
    //全是数字
    var sz=/^[0-9]{1,}$/;
    //全是字母
    var zm=/^[a-zA-Z]{1,}$/;
    //全是特殊字符;
    var ts=/^[`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\]]{1,}$/; 
    if(sz.test(str)){
        is=false;       
    };
    if(zm.test(str)){
        is=false;
        
    };
    if(ts.test(str)){
        is=false;
    };
    return is;  
};

/**
 * 身份认证
 */
//姓名
$('#realName').blur(function(){
    // 4、2~5个汉字
   valid_realName($(this));
});
function valid_realName($this){
//	 var reg=/^[\u4E00-\u9FA5]{2,5}$/;
	var reg = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？0123456789]");
        if ($this.val() == '') {
        	$this.val('姓名不能为空');
        	$this.addClass("errorTip");
            return false;
        }if(reg.test($this.val())){
        	$this.val('姓名格式有误');
        	$this.addClass("errorTip");
            return false;
        }else {
            if($this.hasClass("errorTip")){
            	$this.removeClass('errorTip');
            }
            return true;
        }
}
//身份证号的验证
$('#idno').blur(function(){
	valid_idno($(this));
});
function valid_idno($this){
	var reg = /^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)?$/;
    if ($this.val() == '') {
    	$this.val('身份证号不能为空');
    	$this.addClass("errorTip");
        return false;
    }if(!reg.test($this.val())){
    	$this.val('请输入正确的身份证号');
    	$this.addClass("errorTip");
        return false;
    }else {
         if($this.hasClass("errorTip")){
        	 $this.removeClass('errorTip');
         }
        return true;
    }
}

$(".valid,input[type='text']").focus(function(){
    if($(this).hasClass("errorTip")){
        $(this).removeClass('errorTip');
        $(this).val('');
    }
});

  











