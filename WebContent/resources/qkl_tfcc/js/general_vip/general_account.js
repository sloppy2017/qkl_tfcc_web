/**
 * Created by qw on 2016/9/18.
 */
$(function(){
	$.ajax({
	    type:'post',
	    url:'/service/comacc/findMyAcc',
	    dataType:'json',
	    success:function(data){
	        if(data.success){
	        	//可用SAN
	        	if(data.data.avb_amnt!=null&&data.data.avb_amnt!=""&&data.data.avb_amnt!='undefined'){
	        		$("#avb_amnt").text(data.data.avb_amnt);
	        		$("#zid").text(data.data.avb_amnt);
	        	}else{
	        		$("#avb_amnt").text("0.0000");
	        		$("#zid").text("0.0000");
	        	}
	        	//冻结SAN
	        	if(data.data.froze_amnt!=null&&data.data.froze_amnt!=""&&data.data.froze_amnt!='undefined'){
	        		$("#froze_amnt").text(data.data.froze_amnt);
	        	}else{
	        		$("#froze_amnt").text("0.0000");
	        	}
	        	//总SAN
	        	if(data.data.total_amnt!=null&&data.data.total_amnt!=""&&data.data.total_amnt!='undefined'){
	        		$("#total_amnt").text(data.data.total_amnt);
	        	}else{
	        		$("#total_amnt").text("0.0000");
	        	}
	        	//累计总购买奖励
	        	if(data.data.totalGMJL!=null&&data.data.totalGMJL!=""&&data.data.totalGMJL!='undefined'){
	        		$("#totalGMJL").text(data.data.totalGMJL);
	        	}else{
	        		$("#totalGMJL").text("0.0000");
	        	}
	        	//累计总奖励
	        	if(data.data.totalReward!=null&&data.data.totalReward!=""&&data.data.totalReward!='undefined'){
	        		$("#totalReward").text(data.data.totalReward);
	        	}else{
	        		$("#totalReward").text("0.0000");
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

  
  $('#tijiao').bind("click",tijiao);
  var lock=0;
  function tijiao(){//输入转账的金额和我的账户余额比较
		 var zhanghao= $('#zhanghao').val();//钱包账号
		  var money= ""+$('#zhuanzhang').val();//转帐额度
		  var myreg = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){0,4})?$/;

		  
		  if(zhanghao.length==0||zhanghao==''){
			  alert('钱包账户不能为空');
			  return false;
		  }
		  if(money.length==0||money==''){
			  alert('转账额度不能为空');
			  return false;
		  }
		  if(money=="0"){
			  alert('转账额度不能为0');
			  return false;
		  }
		  if(!myreg.test(money)){
			  alert('转账额度格式有误，请输入数字且小数点后最多保留四位');
	          return false;
	      }
		  ++lock;
		  if(lock>1){
			return;
		  }
		  
	  	$("#tijiao").unbind("click");
		$("#tijiao").css("background-image","url(../../resources/qkl_tfcc/imgs/grey.png)");
		$("#tijiao").css("background-size","220px 42px");
		 //alert(money);
		 $.ajax({
			    type:'post',
			    url:'/service/comacc/turnOut?money='+money+'&zhanghao='+zhanghao,
		 		//data:{'money':money},
			    dataType:'json',
			    success:function(data){   
			        	alert(data.message);
			        	/*$('#zhanghao').val("");
			        	$('#zhuanzhang').val("");
			        	$("#tijiao").css("background-image","url(../../resources/qkl_tfcc/imgs/safty1.jpg)");
				        $("#tijiao").bind("click",tijiao);*/
			        window.location.reload();
			        
			    }
			});
	  }
  /*$(function(){
	  $('#tijiao').click(function(){//点击提交按钮的提示信息
	 //var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  
	  if (filter.test(zhanghao)){ 
		     return true; 
		  }else{
			  alert('您的钱包账号格式不正确'); 
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
  });*/
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
  
  
  /* 列表查询*/
  $(".trans-serach-btn").click(function(){
      var showcnt =10; //每页页数初始值
      var currentPage = 1;
      reload_table(currentPage,showcnt);
      //$('.page1 ul li').click(function(){
      //    $(this).addClass('bg-color').siblings().removeClass('bg-color');
      //});
  });
  	
  function reload_table(currentPage,showCount) {
     // alert("currentPage:"+currentPage+"---showCount:"+showCount+"---str:"+str);
//	  alert("currentPage="+currentPage+"--showCount="+showCount);
	  var url ='/service/comacc/findout?'+$("#form").serialize()+'&currentPage='+currentPage+'&showCount='+showCount;
      $.ajax({
          type: 'post',
          url: url,
          dataType: 'json',
          /*data: {
              str:str,
              currentPage: currentPage,
              showCount: showCount
          },*/
          success: function (data) {
        	  var outList = data.data.outList;
              if(outList.length==0||outList==null){
                  $("#tfoot").show();
                  $("#showData").hide();
                  $(".pages1").hide();
                  return;
              }
              var rsStr = "";
              for (var i = 0; i < outList.length; i++) {
                  rsStr = rsStr + "<tr class='ss'>";
                  rsStr = rsStr + "<th>" + (i+1) + "</th>";
                  rsStr = rsStr + "<th>" + outList[i].outdate + "</th>";
                  rsStr = rsStr + "<th>" + outList[i].outamnt + "</th>";
                  rsStr = rsStr + "<th>" + outList[i].recipient + "</th>";
                  rsStr = rsStr + "<th>" + outList[i].out_status + "</th>";
                  rsStr = rsStr + "<th>" + outList[i].remark1 + "</th>";
                  rsStr = rsStr + "</tr>";
              }
              $("#showData").html( rsStr);
              $("#showData").show();
              $("#tfoot").hide();
              $(".pages1").html(data.data.page.pageStr);
              $(".pages1").show();

          }, 
          error: function (data) {

          }
      });
  }
  
  
  
  