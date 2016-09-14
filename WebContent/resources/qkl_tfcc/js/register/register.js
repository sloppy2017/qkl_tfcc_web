	/**
	 * 导航部分
	 */
	var userType = "1";
	$(document).ready(function(){
		var $tab_li = $('.reg-nav ul li');
		// console.log($tab_li)
		$tab_li.click(function(){
			$(this).addClass('active').siblings().removeClass('active');
			var index = $tab_li.index(this);
			$(' .form >form').eq(index).show().siblings().hide();
			userType = index + 1;
			$(".form").find("input").each(function(){
				if(!$(this).hasClass("yzm")){
					$(this).val('');
				}
			});
			$(".form").find(".error1").each(function(){
				$(this).html('');
			});
		});
	});
	$('.mark1-box .real-name').bind('mousedown',function(){
		$('.mark1-box .real-name1').show();
	});
	$('.mark1-box .later-on').bind('mousedown',function(){
		$('.mark1-box .later-on1').show();
	});

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
	}
    
	/**
	 * 手机号验证
	 */
	$('.form input[name="phone"]').blur(function () {
		$(this).val($(this).val().trim());
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
		$(this).val($(this).val().trim());
		valid_password($(this));
	});
	function valid_password($this){
		//密码正则6-16字母数字或特殊字符
		var result = password_valid($this.val().trim());
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
		var result = password_valid($this.val().trim());
		var pVal = 	$this.parent().prev().children("input[name='password']").val().trim();
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
		//密码正则6-16字母数字或特殊字符
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
	/**
	 * 推介人手机号验证
	 */
	$('.form input[name="phone1"]').blur(function () {
		$(this).val($(this).val().trim());
		valid_phone1($(this));
	});
	function valid_phone1($this){
		//手机号正则
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
		if($this.val()==''){
			$this.next().html('请输入推介人手机号！');
			return false;
		}else if ($this.val().length != 11) {
			$this.next().html('手机号的长度有误！');
			return false;
		} else if (!myreg.test($this.val())) {
			$this.next().html('请输入正确的手机号');
			return false;
		} else {
			$this.next().empty();
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
	    settime(obj);//请用定时器
	    //向后台发送处理数据
	    
	    $.ajax({
	        type:'POST',
	        url:'/service/user/sendsms?phone='+phoneNote.val().trim(),
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
	}
	
	/**
	 * 身份认证
	 */
	//姓名
	$('.form input[name="realName"]').blur(function(){
	    // 4、2~5个汉字
	   valid_realName($(this));
	});
	function valid_realName($this){
		 var reg=/^[\u4E00-\u9FA5]{2,5}$/;
	        if ($this.val() == '') {
	        	$this.next().html('姓名不能为空');
				return false;
	        }if(!reg.test($this.val())){
	        	$this.next().html('请输入2-5个汉字');
	            return false;
	        }else {
	        	$this.next().empty();
	            return true;
	        }
	}
	//身份证号的验证
	$('.form input[name="idno"]').blur(function(){
		valid_idno($(this));
	});
	function valid_idno($this){
		var reg = /^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)?$/;
	    if ($this.val() == '') {
	    	$this.next().html('身份证号不能为空');
	        return false;
	    }if(!reg.test($this.val())){
	    	$this.next().html('请输入正确的身份证号');
	        return false;
	    }else {
	    	$this.next().empty();
	        return true;
	    }
	}
	
	$('.form input[name="cropPerson"]').blur(function(){
		valid_realName($(this));
	});
	$('.form input[name="cropName"]').blur(function(){
		$(this).val($(this).val().trim());
		valid_cropName($(this));
	});
	function valid_cropName($this){
		if($this.val()==''){
			$this.next().html('请输入公司名称');
	        return false;
		}else {
	    	$this.next().empty();
	        return true;
	    }
	}
	$('.form1 .submit').click(function(){
		/*var validPhone = valid_phone($(this).parent().find("input[name='phone']"));
		var validPassword = valid_password($(this).parent().find("input[name='password']"));
		var validResPassword = valid_resPassword($(this).parent().find("input[name='resPassword']"));
		var validYzm = valid_yzm($(this).parent().find("input[name='yzm']"));
		var validPhone1 = valid_phone1($(this).parent().find("input[name='phone1']"));
		if(!(validPhone&&validPassword&&validResPassword&&validYzm&&validPhone1)){
			return;
		}*/
		$.ajax({
			type:'POST',
			url:'/service/user/register?'+$("form:not(.hide)").serialize()+"&userType="+userType,
			success:function(data){
				if(data.success){
//					$(".mark1").show();
					location.href="/view/general_vip/general_account.html";
				}else{
					alert(data.message);
				}
			}
		});
	});
	$('.form2 .submit').click(function(){	
		var validPhone = valid_phone($(this).parent().find("input[name='phone']"));
		var validPassword = valid_password($(this).parent().find("input[name='password']"));
		var validResPassword = valid_resPassword($(this).parent().find("input[name='resPassword']"));
		var validYzm = valid_yzm($(this).parent().find("input[name='yzm']"));
		var validPhone1 = valid_phone1($(this).parent().find("input[name='phone1']"));
		var validIdno = valid_idno($(this).parent().find("input[name='idno']"));
		var validName = valid_realName($(this).parent().find("input[name='realName']"));
		
		
		if(!(validPhone&&validPassword&&validResPassword&&validYzm&&validPhone1&&validIdno&&validName)){
			return;
		}
		var url= '/service/user/register?'+$(".form2").serialize()+"&userType="+userType;
		url = encodeURI(url);
		$.ajax({
			type:'POST',
			url:url,
			success:function(data){
				if(data.success){
//					$(".mark2").show();
					location.href="/view/lp_vip/lp_account.html";
				}else{
					alert(data.message);
				}
			}
		});
	});
	$('.form3 .submit').click(function(){	
		var validPhone = valid_phone($(this).parent().find("input[name='phone']"));
		var validPassword = valid_password($(this).parent().find("input[name='password']"));
		var validResPassword = valid_resPassword($(this).parent().find("input[name='resPassword']"));
		var validYzm = valid_yzm($(this).parent().find("input[name='yzm']"));
		var validPhone1 = valid_phone1($(this).parent().find("input[name='phone1']"));
		var validIdno = valid_idno($(this).parent().find("input[name='idno']"));
		var validCropPerson = valid_realName($(this).parent().find("input[name='cropPerson']"));
		var validCropName = valid_cropName($(this).parent().find("input[name='cropName']"));
		
		if(!(validPhone&&validPassword&&validResPassword&&validYzm&&validPhone1&&validIdno&&validCropPerson&&validCropName)){
			return;
		}
		var url = '/service/user/register?'+$(".form3").serialize()+"&userType="+userType;
		url = encodeURI(url);
		$.ajax({
			type:'POST',
			url:url,
			success:function(data){
				if(data.success){
//					$(".mark3").show();
					location.href="/view/invest_vip/invest_account.html";
				}else{
					alert(data.message);
				}
			}
		});
	});