$(document).ready(function(){

	function sss(){
		
	}
	
	sss();
	
})	


/**
	 * 倒数60秒
	 */
	var countdown=60; 
	var timeoutId=0;
	function settime(obj) { 
		if(countdown == 0){
			clearTimeout(timeoutId);		
			countdown = 60;
			$(obj).removeAttr("disabled"); //启用按钮
			$(obj).val("重新发送验证码");
		}else{
			$(obj).attr("disabled","disabled"); //禁用按钮
			$(obj).val("请在" + countdown + "秒内输入验证码");
			countdown--;
			timeoutId = setTimeout(function(){settime(obj);},1000);	
			
		}
	};
	

	/**
	 * 手机号验证
	 */
	$('.form input[name="phone"]').blur(function () {
		$(this).val($.trim($(this).val()));
		valid_phone($(this));
	});
	function valid_phone($this){
		//手机号正则
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
		if ($this.val() == ''||$this.val() == undefined) {
			$this.next().html('请输入你的手机号');
			return false;
		} else if ($this.val().length !=11) {
			$this.next().html('你输入的手机号的长度有误！');
			return false;
		} else if (!myreg.test($this.val())) {
			$this.next().html('请输入正确的手机号');
			return false;
		} else {
			$this.next().empty();
			return true;
		}
	}
	/**
	 * 密码验证
	 */
	$('.form input[name="password"]').blur(function () {
		$(this).val($.trim($(this).val()));
		valid_password($(this));
	});
	function valid_password($this){
		//密码正则6-16字母数字或特殊字符
		var result = password_valid($.trim($this.val()));
		if ($this.val() == '') {
			$this.next().html('请输入密码');
			return false;
		} else if (!result) {
			$this.next().html('请输入6~20位字母、数字或字符组合');
			return false;
		} else {
			$this.next().empty();
			return true;
		}
	}
	/**
	 * 确认密码验证
	 */
	$('.form input[name="resPassword"]').blur(function(){
		valid_resPassword($(this));
	});
	function valid_resPassword($this){
		var result = password_valid($.trim($this.val()));
		var pVal = 	$this.parent().prev().children("input[name='password']").val();
		if ($this.val() == '') {
			$this.next().html('请输入确认密码');
			return false;
		}else if(!result){
			$this.next().html('请输入6~20位字母、数字或字符组合');
			return false;
		}else if($this.val()!=pVal){
			$this.next().html('两次密码不一致');
			return false;
		}else{
			$this.next().empty();
			return true;
		}
	}	
	/**
	 * 验证码验证
	 */
	$('.form input[name="yzm"] ').blur(function(){
		valid_yzm($(this));
	});
	function valid_yzm($this){
		//验证码6位数字开头不能为零
		reg = /^[1-9]\d{5}$/;
		if($this.val()==''){
			$this.next().next().html('请输入验证码');
			return false;
		}else if(!reg.test($this.val())){
			$this.next().next().html('验证码格式有误');
			return false;
		}else if(countdown == 60){
			$this.next().next().html('验证码已过期，请重新获取');
			return false;
		}else{
			$this.next().next().empty();
			return true;
		}
	}
	//密码格式验证
	function password_valid(str){
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
	$(".yzm").click(function(){
		getVCode(this);
	});
	//获取验证码
	function getVCode(obj) {
		var phoneNote = $(obj).parent().prev().children("input[name='phone']");
		var validPhone = valid_phone(phoneNote);//验证手机号是否正确
	    if(!validPhone){
	        return;
	    }
	    $.ajax({
	        type:'POST',
	        url:'/service/user/isExistPhone?phone='+$.trim(phoneNote.val()),
	        dataType:'JSON',
	        success: function (json) {
	             if(json.success){
	            	 settime(obj);//调用定时器
	         	    //向后台发送处理数据
	         	    $.ajax({
	         	        type:'POST',
	         	        url:'/service/user/sendsms?phone='+$.trim(phoneNote.val()),
	         	        dataType:'JSON',
	         	        success: function (json) {
	         	             if(json.success){
	         	             }else{
	         	                 alert(json.message);
	         	             }
	         	        },
	         	        error: function (a,b,c) {
	         	            console.log("获取短信验证码失败");
	         	        }
	         	    });
	             }else{
	                 alert(json.message);
	             }
	        }
	    });
	}
	function subBtn(){
		var validPhone = valid_phone($("input[name='phone']"));
		var validYzm = valid_yzm($("input[name='yzm']"));
		var validPassword = valid_password($("input[name='password']"));
		var validResPassword = valid_resPassword($("input[name='resPassword']"));
//		alert(validPhone+"--"+validYzm+"--"+validResPassword+"--"+validPassword);
		if(!(validPhone&&validPassword&&validResPassword&&validYzm)){
			return;
		}
		var url = '../service/user/forgetpwd?'+$("form").serialize();
		$.ajax({
			type:'POST',
			url:url,
			dataType:'JSON',
			success:function(data){
				if(data.success){
					$(".mark1").show();
				}else{
					alert(data.message);
				}
			}
		});
		/*var url = '../service/user/forgetpwd?'+$("form").serialize();
		$.post(url,function(data){
			if(data.success){
				$(".mark1").show();
			}else{
				alert(data.message);
			}
		});*/
	};
//	$(".empty-clear").val("");
//	function empty(){
//		$('.empty-clear').html('')
//		alert(111)
//	}	
//	empty();
	
	
//	
//	clearCookie(JSESSIONID);
//	function clearCookie(name) {  
//	    setCookie(name, "", -1);  
//	}  
//	function checkCookie() {
//	    var user = getCookie("username");
//	    if (user != "") {
//	        alert("Welcome again " + user);
//	    } else {
//	        user = prompt("Please enter your name:", "");
//	        if (user != "" && user != null) {
//	            setCookie("username", user, 365);
//	        }
//	    }
//	}
	

	

function submit(){
		console.log("forgetpwd start1!");
		var validPhone = valid_phone($("input[name='phone']"));
		var validYzm = valid_yzm($("input[name='yzm']"));
		var validPassword = valid_password($("input[name='password']"));
		var validResPassword = valid_resPassword($("input[name='resPassword']"));
//		alert(validPhone+"--"+validYzm+"--"+validResPassword+"--"+validPassword);
		if(!(validPhone&&validPassword&&validResPassword&&validYzm)){
			return;
		}
		 console.log("forgetpwd start2!");
		var url = '../service/user/forgetpwd?'+$("form").serialize();
		$.ajax({
			type:'POST',
			url:url,
			dataType:'JSON',
			success:function(data){
				if(data.success){
					$(".mark1").show();
				}else{
					alert(data.message);
				}
			}
		});
		/*var url = '../service/user/forgetpwd?'+$("form").serialize();
		$.post(url,function(data){
			if(data.success){
				$(".mark1").show();
			}else{
				alert(data.message);
			}
		});*/
	};





