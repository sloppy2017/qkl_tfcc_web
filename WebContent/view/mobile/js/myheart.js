$(document).ready(function(){
// 个人中心的主页面

var phoneVal;
//返回的手机号值
$.ajax({
    type:"get",
    url:"/service/user/toMyself",
    data:{"userCode":usercode},
    success:function(massge){
      // console.log(massge)
      var realVal=massge.data.realName;
      // console.log(realVal);
       phoneVal=massge.data.phone;
      $('.mun-info').text(phoneVal);

      // console.log(phoneVal);
    }
  });
  $.ajax({
    type:"post",
    url:"/service/comacc/findMyAcc",
    data:{"userCode":usercode},
    success:function(massge){
      $('.a-one div').text(massge.data.total_amnt);
      $('.a-two div').text(massge.data.avb_amnt);
      $('.a-three div').text(massge.data.froze_amnt);
    }
  });


//获取验证码
   var code;
  $('.get-pwd').on('click',function(){
    console.log( validate.phone($('.put-phone').val() ) );
    if( validate.phone($('.put-phone').val() ) ){ 
      console.log($('.put-phone').val() )
       tesSetIn()
    $.ajax({
      type:"post",
      url:"/service/user/sendsms",
      data:{"phone":$('.put-phone').val()},
      success:function( content ){            
            if(content.errorCode==0){           
            }
         }  
    });
  }
});
//倒计时
function tesSetIn(){
  var total=60;
  var timer = setInterval(function(){
    if(total == 0) {
      total="重新发送";
      $(".get-pwd").removeAttr("disabled");
      
      clearInterval(timer);//如果程序在上一行出现错误，这一行代码就无法执行
    }else if(total> 0){
      $(".get-pwd").attr("disabled","disabled");
    }
    $(".get-pwd").html(total);
    total--;
  },1000);
}


//确认修改
$('.real-name1 button').click(function(){
   if( validate.code($('.write-pwd').val() ) ){ 
      console.log($('.write-pwd').val() )
       tesSetIn()
    $.ajax({
      type:"post",
      url:"/service/user/modifyphone",
      data:{
        "userCode":usercode,
        "yzm":$('.write-pwd').val(),
        "oldphone":phoneVal,
        "phone":$(".put-phone").val(),
        "mobileflag":1,
      },
      success:function(massges){                    
            console.log( massges );
            phoneVal = $(".put-phone").val();
            $('.a-m-name .oldphone').html( phoneVal);
            window.location.href="myheart.html";

        }  
    });
  }
})




//自动刷新获取值；
$.ajax({
    type:"get",
    url:"/service/user/toMyself",
    data:{"userCode":usercode},
    success:function(massge){
      console.log(massge);
      var realVal=massge.data.realName;     
      console.log(realVal);
      var phoneVal=massge.data.phone;
       $('.oldphone').val(phoneVal)
      if( realVal == ''){
           $('.a-m-name .oldphone').val(phoneVal);
      }else{
         $('#name-info').html(realVal);
         $('.undiend').html('已认证');
      }
       // console.log(phoneVal);
    }
  });

$('.exit-btn').click(function(){
  	window.location.href='login.html';
})






// 实名认证
var vnameVal;
  $('.real-name2 button').click(function(){
    // var xx =validate.name($('.r-name').val() );
    // var yy =validate.Idcard($('.r-idcard').val());
      vnameVal=$('.r-name').val();
    // alert(vnameVal);         
        // alert(xx+'.....'+yy);
        $.ajax({
          type:"post",
          url:"/service/user/realname",
          data:{
            "realName":$('.r-name').val(),
            "userCode":usercode,
            "phone":phoneVal,
            "idno":$('.r-idcard').val(),
            "mobileflag":1
          },
          success:function(massges){
            alert(massges.message);
              console.log(massges);                         
              // $('.mark1').show();
              if(massges.message=='用户实名成功！'){
                  window.location.href='myheart.html'; 
              } 
            
              return false; 
            }  
      });


  })

 $("#name-info").html( vnameVal);







});

    














