$(function(){
    (function creat(){
    	//手机失去焦点
        $('.form-input1 .phone').blur(function(){
            //手机号正则
          	 reg=/^1[3|4|5|7|8][0-9]\d{4,8}$/i;
            if($(this).val()==''||$(this).val()=='请输入你的手机号'){
                $(this).addClass('errorC');
                $(this).next().html('请输入你的手机号');
            }else if($(this).val().length<11){
            	$(this).addClass('errorC');
            	$(this).next().html('你输入的手机号的长度有误！');
            }else if(!reg.test($(this).val())){
            	$(this).addClass('errorC');
            	$(this).next().html('请输入正确的手机号'); 
            }else{
            	$(this).addClass('checkedC');
            	$(this).removeClass('errorC');
            	$(this).next().empty();
            }
        });
        //密码失去焦点
  		$('.form-input1 .pass').blur(function(){
        	//密码正则6-16字母数字或特殊字符
        	 reg = /^[a-zA-Z][a-zA-Z0-9|*|&|%|.|@|!]{5,15}$/;
        	  if($(this).val()==''||$(this).val()=='请输入密码'){
                $(this).addClass('errorC');
                $(this).next().html('请输入密码');
 
            }else if(!reg.test($(this).val())){
            	$(this).addClass('errorC');
            	$(this).next().html('密码是以字母开头的6-16数字字母或特殊字符');
            }else{
            	$(this).addClass('checkedC');
            	$(this).removeClass('errorC');
            	$(this).next().empty();
            }
            
        });
        //验证码失去焦点
		$('.form-input1 .xiaoyan').blur(function(){
        	//密码正则6-16字母数字或特殊字符
        	 reg = /^[a-zA-Z0-9]{6}$/;
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
            }
	    });
     })();
     // 生成验证码
     (function create_code(){
     	function auth_code(){
     		var arr=['1','2','3','4','5','6','7','8','9','0','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

     		return arr.sort(function(){
     			return (Math.random()-.5);
     		});
     	}
     	auth_code();
     	function show_code(){
     		var arr1='';
     		var code=auth_code();
     		for(var i=0;i<6;i++){
     			arr1+=code[i];
     		}
     		$("#check-code").text(arr1);
     	}
     	show_code();
     	$("#check-code").click(function(){
     		show_code();
     	});
     })()
	$('.submit').click(function(){
		
		$.ajax({
			type:'POST',
			url: '1.php',
			dataType:JSON,
			data:{
				phone:$('.form-input1 .phone').val(),
				password:$('.form-input1 .pass').val(),
				yzm:$('.form-input1 .xiaoyan').val()
			},
			success:function(response,status,xhr) {
				$().html(response);
			},
			error:function(xhr,errorText,errorStatus){
				console.log(errorText+'...'+errorStatus);
			}
		})

	})
})
