
//function textFocus(e){
//    if(e.defaultValue== e.value){
//        e.value='';
//        e.style.color='#333';
//    }
//}
//function textBlur(el) {
//    if (el.value == '') { el.value = el.defaultValue; el.style.color = '#999'; }
//}



//注册界面
$(function(){
	(function createV() {
//		alert("222");
		$('.form input[name="phone"]').blur(function () {
			//手机号正则
			reg = /^1[3|4|5|7|8][0-9]\d{4,8}$/i;
			if ($(this).val() == '' || $(this).val() == '请输入你的手机号') {
				$(this).addClass('errorC');
				$(this).next().html('请输入你的手机号');
			} else if ($(this).val().length < 11) {
				$(this).addClass('errorC');
				$(this).next().html('你输入的手机号的长度有误！');
			} else if (!reg.test($(this).val())) {
				$(this).addClass('errorC');
				$(this).next().html('请输入正确的手机号');
			} else {
				$(this).addClass('checkedC');
				$(this).removeClass('errorC');
				$(this).next().empty();
				flag=true;
			}
		});
		//密码失去焦点
		$('.form input[name="password"]').blur(function () {
			//密码正则6-16字母数字或特殊字符
			reg = /^[a-zA-Z][a-zA-Z0-9|*|&|%|.|@|!]{5,15}$/
			if ($(this).val() == '' || $(this).val() == '请输入密码') {
				$(this).addClass('errorC');
				$(this).next().html('请输入密码');

			} else if (!reg.test($(this).val())) {
				$(this).addClass('errorC');
				$(this).next().html('密码是以字母开头的6-16数字字母或特殊字符');
			} else {
				$(this).addClass('checkedC');
				$(this).removeClass('errorC');
				$(this).next().empty();
				flag=true;
			}
		});
		$('.form input[name="resPassword"]').blur(function(){
			var pVal = 	$('.form input[name="password1"]').val();
			if ($(this).val() == '' || $(this).val() == '请输入密码') {
				$(this).addClass('errorC');
				$(this).next().html('请输入确认密码');
			}else if($(this).val()!==pVal){
				$(this).addClass('errorC');
				$(this).next().html('确认密码错误')
			}else{
				$(this).addClass('checkedC');
				$(this).removeClass('errorC');
				$(this).next().empty();
				flag=true;
			}
		})
		//验证码失去焦点
		$('.form input[name="yzm"] ').blur(function(){
			//密码正则6-16字母数字或特殊字符
			reg = /^[a-zA-Z0-9]{6}$/
			if($(this).val()==''||$(this).val()=='请输入验证码'){
				$(this).addClass('errorC');
				$(this).next().next().html('请输入验证码');

			}else if(!reg.test($(this).val())){
				$(this).addClass('errorC');
				$(this).next().next().html('请重新输入验证码');
			}else{
				$(this).addClass('checkedC');
				$(this).removeClass('errorC');
				$(this).next().empty();
				flag=true;
			}
		});

		$('.form input[name="phone1"]').blur(function () {
			//手机号正则
			reg = /^1[3|4|5|7|8][0-9]\d{4,8}$/i;
			if ($(this).val() == '' || $(this).val() == '输入推荐人手机号') {
				$(this).addClass('errorC');
				$(this).next().html('输入推荐人手机号');
			} else if ($(this).val().length < 11) {
				$(this).addClass('errorC');
				$(this).next().html('你输入的手机号的长度有误！');
			} else if (!reg.test($(this).val())) {
				$(this).addClass('errorC');
				$(this).next().html('请输入正确的手机号');
			} else {
				$(this).addClass('checkedC');
				$(this).removeClass('errorC');
				$(this).next().empty();
			}
		});


	})

	$('.submit').click(function(){	
	
		
		
		var phone = $('.form input[name="phone"]').val();
		var yzm = $('.form input[name="yzm"]').val();
		var password = $('.form input[name="password"]').val();
		var resPassword = $('.form input[name="resPassword"]').val();
		var phone1 = $('.form input[name="phone1"]').val();
	
			$.ajax({
				type:'POST',
				url:'../service/user/register/',
				data:{
					phone:phone,
					yzm:yzm,
					password:password,
					respassword:resPassword,
					phone1:phone1,
					usertype:"1"
				},
				success:function(data){
					alert(data);
				}
			});


		

	})

})

function sendsms(){
	var phone = $('.form input[name="phone"]');
//	  alert("phone is "+phone.val());
	$.ajax({
		type:'POST',
		url:'../service/user/sendsms/',
		data:{
			phone:phone.val()
			},
		dataType:'JSON',
		success: function (data) {
            // 验证码发送成功，把验证码先存到页面一个变量中		
             if(data.success){
            	// settime(this);
             }else
            	 {
            	 alert(data.message);
            	 }
            //console.log(data);
        },
        error: function (a,b,c) {
   //console.log(a.readyState+"\n"+b+"\n"+c);
            console.log("获取短信验证码失败");
        }
	});
	
	
}


var countdown=60; 
var timeoutId=0;
function settime() { 
	if(countdown == 0){
		clearTimeout(timeoutId);		
		countdown = 60;
	}else{
		timeoutId = window.setTimeout(refreshMsgTime,1000);		
		countdown--;
	}
}

