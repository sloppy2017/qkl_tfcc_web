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
