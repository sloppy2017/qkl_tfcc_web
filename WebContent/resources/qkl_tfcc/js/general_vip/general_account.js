/**
 * Created by qw on 2016/9/18.
 */
$(function(){
	$.ajax({
	    type:'post',
	    url:'/service/comacc/findNums',
	    dataType:'json',
	    success:function(data){
	        if(data.success){
	            if(data.data.findTB==null||data.data.findTB==""){
	                $("#spt").html("0.0000");
	            }else{
	                $("#spt").html(data.data.findTB);
	                $("#zid").html(data.data.findTB);
	                $("#in_sp").html(data.data.findTB);
	            }
	            if(data.data.findTTReward==null||data.data.findTTReward==""){
	                $("#sptt").html("0.0000");
	            }else{
	                $("#sptt").html(data.data.findTTReward);
	            }
	            if(data.data.findReward==null||data.data.findReward==""){
	                $("#spv").html("0.0000");
	            }else{
	                $("#spv").html(data.data.findReward);
	            }
	            
	        }else{
	            alert(data.message);
	        }
	    }
	});
});


$(function(){
      getUserInfo();
  });

  function getUserInfo(){
      var url = "/service/user/toMyself";
      $.getJSON(url,function(data){
          if(data.success){
              if(data.data.imgAddrss){
                  $("#left_headPicId").attr("src",data.data.imgAddrss);
              }else{
                  $("#left_headPicId").attr("src","../../resources/qkl_tfcc/imgs/LPtouxiang.jpg");
              }
              if(data.data.userName){
                  $("#user").html(data.data.userName);
              }else if(data.data.realName){
                  $("#user").html(data.data.realName);
              }else{
                  $("#user").html(data.data.phone);
              }
          }
      });
  }

  $(function(){
	  $('#tijiao').click(function(){//输入转账的金额和我的账户余额比较
		 var money= $('#zhuanzhang').val();
		 //alert(money);
		 $.ajax({
			    type:'post',
			    url:'/service/comacc/acccompare?money='+money,
		 		//data:{'money':money},
			    dataType:'json',
			    success:function(data){
			        if(data.success){    
			        	alert(data.message);
			        	return false;
			        }
			       
			    }
			});
		  
	  });
	  
	  
  });
  
  $(function(){
	  $('#tijiao').click(function(){//点击提交按钮的提示信息
	  var zhanghao= $('#zhanghao').val();//对方账号
	  var money= $('#zhuanzhang').val();//转帐额度
	  if(zhanghao.length==0||zhanghao==null){
		  alert('账号不能为空');
		  return false;
	  }
	  if(money.length==0||money==null){
		  alert('转账金额不能为空');
		  return false;
	  }
	 // alert('转账功能还未正式上线');
	  $.ajax({
		    type:'post',
		    url:'/service/../..?zhanghao='+zhanghao,
	 		//data:{'money':money},
		    dataType:'json',
		    success:function(data){
		        if(data.success){     
		        	alert(data.message);
		        }
		        
				  
		    }
		});
	  
  });
  });
  /**
 * Created by qw on 2016/9/18.
 */
/*$(function(){
	$.ajax({
	    type:'post',
	    url:'/service/comacc/findNums',
	    dataType:'json',
	    success:function(data){
	        if(data.success){
	            if(data.data.findTB==null||data.data.findTB==""){
	                $("#spt").html("0.0000");
	            }else{
	                $("#spt").html(data.data.findTB);
	                $("#zid").html(data.data.findTB);
	            }
	            if(data.data.findTTReward==null||data.data.findTTReward==""){
	                $("#sptt").html("0.0000");
	            }else{
	                $("#sptt").html(data.data.findTTReward);
	            }
	            if(data.data.findReward==null||data.data.findReward==""){
	                $("#spv").html("0.0000");
	            }else{
	                $("#spv").html(data.data.findReward);
	            }
	            
	        }else{
	            alert(data.message);
	        }
	    }
	});
});


$(function(){
      getUserInfo();
  });

  function getUserInfo(){
      var url = "/service/user/toMyself";
      $.getJSON(url,function(data){
          if(data.success){
              if(data.data.imgAddrss){
                  $("#left_headPicId").attr("src",data.data.imgAddrss);
              }else{
                  $("#left_headPicId").attr("src","../../resources/qkl_tfcc/imgs/LPtouxiang.jpg");
              }
              if(data.data.userName){
                  $("#user").html(data.data.userName);
              }else if(data.data.realName){
                  $("#user").html(data.data.realName);
              }else{
                  $("#user").html(data.data.phone);
              }
          }
      });
  }

  $(function(){
	  $('#zhuanzhang').blur(function(){//输入转账的金额和我的账户余额比较
		 var money= $(this).val();
		 if(money==null||money==''||money==0){
			 alert('转账金额不能为空');
		 }
		 $.ajax({
			    type:'post',
			    url:'/service/comacc/acccompare?money='+money,
		 		//data:{'money':money},
			    dataType:'json',
			    success:function(data){
			        if(data.success){    
			        	alert(data.message);
			        }
			    }
			});
		  
	  });
	  
	  
  });
  
  $(function(){
	  $('#tijiao').click(function(){//点击提交按钮的提示信息
		  alert('转账功能还未正式上线');  
		  return false;
	  });
	  var zhanghao= $('#zhanghao').val();
	  $.ajax({
		    type:'post',
		    url:'/service/../..?zhanghao='+zhanghao,
	 		//data:{'money':money},
		    dataType:'json',
		    success:function(data){
		        if(data.success){    
		        	alert(data.message);
		        }
		    }
		});
	  
  });
  */