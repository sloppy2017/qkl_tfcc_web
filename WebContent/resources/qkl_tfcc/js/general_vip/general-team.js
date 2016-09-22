

$(function(){
	$.ajax({
	    type:'post',
	    url:'/service/team/findVipNum',
	    dataType:'json',
	    success:function(data){
	        var message = data.message;
	        if(data.success){
	            if(data.data==null){
	                 $(".member1").html("0");
	                 $(".member2").html("0");
	                 $(".member3").html("0");
	                 $(".member4").html("0");
	            }else{
	                $(".member1").html(data.data.rNum);
	                $(".member2").html(data.data.aNum);
	                $(".member3").html(data.data.bNum);
	                $(".member4").html(data.data.cNum);
	            }
	            
	        }else{
	            alert(message);
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
                  $("#left_headPicId").attr("src","/resources/qkl_tfcc/imgs/LPtouxiang.jpg");
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
	  $("#quan").click(function(){
		  var c=$(this).attr("checked");
		  if(c=="checked"){ //选中
			 $("[name=checkbox]").attr("checked",true);
		  }else{
			 $("[name=checkbox]").attr("checked",false);
		  }
	  });
	});
	
	
	$(function(){
		var totalCount=$("[name=checkbox]").length;//总数
		
		$("[name=checkbox]").click(function(){
			var count=$("[name=checkbox]:checked").length; //被选中的个数
			if(totalCount==count){
				$("#quan").attr("checked",true);
		    }else{
			   $("#quan").attr("checked",false);
			}
	    });
    });
 
  
  

var str='';
var flag =true;

$(".type-btn a").click(function(){
    var showcnt =10; //每页页数初始值
    var  myselect=document.getElementById("showcnt");
    if(myselect==null||myselect=="null"){
    }else{
        showcnt=myselect.options[myselect.selectedIndex].value;
    }
    
    str='';
   /* if(document.getElementById("quan").checked==true){
        document.getElementById("lva").checked =true;
        document.getElementById("lvb").checked =true;
        document.getElementById("lvc").checked =true;
    }*/
    
    if(document.getElementById("quan").checked==true){
        str = str+'ALL,';
    }
    else{
        if(document.getElementById("lva").checked==true){
            str = str+"A,";
        }
        if(document.getElementById("lvb").checked==true){
            str = str+"B,";
        }
        if(document.getElementById("lvc").checked==true){
            str = str+"C,";
        }
    }
  // alert("str is "+str);
    
   
    if(flag){
        reload_table(1,showcnt);
    }
    
    //$('.page1 ul li').click(function(){
    //    $(this).addClass('bg-color').siblings().removeClass('bg-color');
    //});


});
	
function reload_table(currentPage,showCount) {
    var rsStr = "";
   // alert("currentPage:"+currentPage+"---showCount:"+showCount+"---str:"+str);
    $.ajax({
        type: 'post',
        url: '/service/team/findVipPage?str='+str+'&currentPage='+currentPage+'&showCount='+showCount,
        dataType: 'json',
        data: {
            str:str,
            currentPage: currentPage,
            showCount: showCount
        },
        success: function (data) {
            var tviplist = data.data.tviplist;
            if(tviplist.length==0){
        		alert("没有符合条件的会员信息");
        	}
            var tablecols = "<tr> \n"
                + " <th>会员级别</th> \n"
                + "<th>注册时间</th> \n"
                + "<th>会员账号</th> \n"
                +"<th>会员姓名 </th>\n"
//                +"<th>购买量(股)</th>\n"
                + "</tr> \n";
            rsStr= tablecols;
            for (var i = 0; i < tviplist.length; i++) {
                rsStr = rsStr + "<tr class='ss'>";
                rsStr = rsStr + "<th>" + tviplist[i].rela_level + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].rg_time + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].phone + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].real_name + "</th>";
                rsStr = rsStr + "</tr>";
            }
            $(".result-tab").html( rsStr);
//            console.log(data.data.page.pageStr);
            $(".pages1").html(data.data.page.pageStr);

        }, 
        error: function (data) {

        }
    });
}




