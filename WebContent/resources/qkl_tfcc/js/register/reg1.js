

//注册界面
$(function(){
	(function createV() {
		var flag=false;
		$('.input input[name="phone"]').blur(function () {
			//手机号正则
			reg = /^1[3|4|5|7|8][0-9]\d{4,8}$/i;
			if ($(this).val() == '') {
				$(this).addClass('errorC');
				$(this).next().html('请输入你的手机号');
			} else if ($(this).val().length < 11) {
				$(this).addClass('errorC');
				$(this).next().html('请输入正确的手机格式');
			} else if (!reg.test($(this).val())) {
				$(this).addClass('errorC');
				$(this).next().html('请输入正确的手机格式');
			} else {
				$(this).addClass('checkedC');
				$(this).removeClass('errorC');
				$(this).next().empty();
				flag=true;
			}
		});
		//密码失去焦点
		$('.input input[name="password"]').blur(function () {
			//密码正则6-16字母数字或特殊字符
			reg = /^[a-zA-Z][a-zA-Z0-9|*|&|%|.|@|!]{5,15}$/
			if ($(this).val() == '') {
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
		$('.input input[name="resPassword"]').blur(function(){
			var pVal = 	$('.input input[name="password1"]').val();
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
		$('.input input[name="yzm"] ').blur(function(){
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


		$('.input input[name="phone1"]').blur(function () {
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
	})()


	$('.submit').click(function(){
				
		if(flag){
			$.ajax({
				url:'1.php',
				data:{
					phone:$('.phone').val(),
					yzm:$('.zym').val(),
					password:$('password').val(),
					phone1:$('phone1').val(),
				},
				success:function(data){
					alert(data)
				}
			})


		}

	})

})

	var countdown=60; 
		function settime(obj) { 
			console.log('aaaa')
			var val=$('.input input[name="username1"]');
		    if (countdown == 0) { 
		        obj.removeAttribute("disabled");    
		        obj.value="免费获取验证码"; 
		        countdown = 60; 
		        return;

				$.ajax({
					type:'POST',
					url:'1.php',
					data:{phone:val.val()},
					dataType:'JSON',
					success:function(response) {
						$(this).next().html(response);
					},
					error:function(xhr,errorText,errorStatus){
						console.log(errorText+'...'+errorStatus);
					}
				})
		    } else { 

		        obj.setAttribute("disabled", true); 
		        obj.value="重新发送(" + countdown + ")"; 
		        countdown--; 
		    } 
		setTimeout(function() { 
		    settime(obj) }
		    ,1000) 
		}

