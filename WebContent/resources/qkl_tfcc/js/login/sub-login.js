$(function() {
	var phone = $.cookie('phone');
	$("#password").val('');
	if(phone != 'undefined'){
		$("#phone").val(phone);
		$("#saveid").attr("checked",true);
	}
	$("#saveid").click(function(){
		if(!$("#saveid").attr("checked")){
			$.cookie('phone', '', { expires: -1 });
			$("#phone").val('');
			$("#password").val('');
		}
	});
	
	//手机失去焦点
	$("input[name='phone']").blur(function () {
		valid_phone($(this));
	});
	// 手机验证
	function valid_phone($this){
		$this.val($this.val().trim());
		//手机号正则
		var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
		if ($this.val() == '') {
			$this.next().html('请输入你的手机号');
			return false;

		}else if ($this.val().length != 11) {
			$this.next().html('你输入的手机号的长度有误！');
			return false;
		} else if (!reg.test($this.val())) {
			$this.next().html('请输入正确的手机号');
			return false;
		} else {
			$this.next().empty();
			return true;
		}
	}
	$("input[name='password']").blur(function () {
	   return valid_password($(this));
	});
	function valid_password($this){
		$this.val($this.val().trim());
	 //密码正则6-20字母数字或特殊字符
	   var result = password_valid($this.val());
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
	
	$('input[name="yzm"]').blur(function(){
		return valid_yzm($(this));
	});
	//验证验证码
	function valid_yzm($this){
		$this.val($this.val().trim());
		var code = $this.val().trim();
//		var reg = /^[0-9a-zA-Z]{6}$/;
	    if(code==''){
	    	$this.next().next().html('请输入验证码');
	        return false;
	    }else if(code.toLowerCase() != verCode){
	    	$this.next().next().html('验证码输入错误');
	        return false;
	    }else{
	    	$this.next().next().empty();
	        return true;
	    }
	}

	// 生成验证码 start
	function auth_code() {
		var arr = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
		return arr.sort(function () {
			return (Math.random() - .5);
		});
	}
	function show_code() {
		var arr1 = '';
		var code = auth_code();
		for (var i = 0; i < 6; i++) {
			arr1 += code[i];
		}
		verCode = arr1.toLowerCase();
		$("#check-code").text(arr1);
	}
	
	show_code();
	$("#check-code").click(function () {
		show_code();
	});
	// 生成验证码 end
	$('.submit').click(function(){
		var validPhone = valid_phone($("input[name='phone']"));
		var validPassword = valid_password($("input[name='password']"));
		var validYzm = valid_yzm($("input[name='yzm']"));
		if(!(validPhone&&validPassword&&validYzm)){
			return;
		}
		//记住
		if($("#saveid").attr("checked")){
			$.cookie('phone', $("#phone").val(), { expires: 7 });
		}
		var url = "/service/user/login?"+$("form").serialize();
		url = encodeURI(url);
		$.ajax({
			type:"post",
			url:url,
			success:function(data){
				if(data.success){
					if(data.data.userType==1){
						location.href='../view/general_vip/general_account.html';
					}else if(data.data.userType==4){
						location.href = '../view/invest_vip/invest_account.html';
					}
				}else{
					alert(data.message);
				}
			},
			error:function(data){

			}
		});
	});
	
	 //绑定输入框，这里只能 是ID
   $("#yzm").keydown(function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	$(".submit").click();
	    }
    });
  
   
   
   
   
});
