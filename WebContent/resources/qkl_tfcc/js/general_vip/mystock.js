/**
 * Created by qw on 2016/9/18.
 */
$(function(){
	//获取个人信息
	getUserInfo();
	//获取收款账户信息
	getBankAccInfo();
});
function getBankAccInfo(){
	$.ajax({
        type: "post",
        url: "/service/bankaccinfo/info",
        dataType: "json",
        success: function (data) {	
        	 if(data.success){
             		 $("#revorgname").text(data.data.orgName);
             		 $("#revbankaccno").text(data.data.bankaccno);
             		$("#deposit_bankname").text(data.data.depositBankname);
             		/* $("#revbankdepname").text(data.data.depositBankname);
             		 $("#checkphone").text(data.data.checkphone);*/
             			
             }else{
             	alert(data.message);
             }
        }
    });
}

/*$('#buy').mousedown(function(){
	$(this).css("background","gray");
	
});*/


$("input[name='txnum']").blur(function(){//计算支付金额
	var url = "/service/bankaccinfo/PayMoney?"+$("form").serialize();
	url = encodeURI(url);
	$.ajax({
        type: "post",
        url:url,
        dataType: "json",
        success: function (data) {
        	if(data.success){
        		$("input[name='txamnt']").attr("value",data.data);
        	}else{
        		alert(data.message);
        	}  
        }
    });
});

/*$(function(){ 
	$("input[name='payno']").blur(function(){
		var $payno= $("input[name='payno']").val();
		var reg = /^[\w\u0391-\uFFE5\u40]+$/;
		if($payno.length==0||$payno==null){
			alert('支付宝账号不能为空');
			return false;
		};
		if(!reg.test($payno)){
			
			alert('请输入正确的支付宝账号');
		};
	});
	});*/




var lock=0;
function userRequest(){	
//提交购买申请
	var $txnum= $("input[name='txnum']").val();
	var reg = /[a-zA-Z\d+]{6,16}/;
	var $txamnt= $("input[name='txamnt']").val();
	var $payno= $("input[name='payno']").val();
	
	
	if($txnum.length==0||$txnum==null){
		alert('请输入购买数量');
		return false;
	}
	if($payno.length==0||$payno==null){
		alert('支付宝账号不能为空');
		return false;
	}else{
		if(reg.test($payno)){
			
			}else{
				alert('请输入正确的支付宝账号:手机号/邮箱');
				return false;
			};
	}
	
	if($txamnt<1000.00){
		alert('单次购买金额不得少于1000.00元');
		return false;
	};
	if($txamnt>50000.00){
		alert('单次购买金额不得大于50000.00元');
		return false;
	};
	$("#buy").unbind("click");
	$("#buy").css("background-image","url(../../resources/qkl_tfcc/imgs/grey.png)");
	$("#buy").css("background-size","220px 42px");
	++lock;
	if(lock>1){
		return;
	}
	var url = "/service/bankaccinfo/tradebuy?txamnt="+$txamnt+"&payno="+$payno+"&txnum="+$txnum;
	url = encodeURI(url);
	$.ajax({
        type: "post",
        url:url,
        dataType: "json",
        /*data:{
            ttext:$("#ttext").val(),
            money:$("#money").val()
            'revorgname':$("#revorgname").text().trim(),
            'revbankaccno': $("#revbankaccno").text().trim(),
            'revbankdepname':$("#revbankdepname").text().trim()
        },*/ 
        success: function (data) {
        	alert(data.message);
        	if(data.success){
                var tempwindow=window.open();
                tempwindow.location='https://auth.alipay.com/login/index.htm';
            }
        	window.location.reload();
            /*$("input[name='txamnt']").val("");
            $("input[name='txnum']").val("");
            $("input[name='payno']").val("");*/
        }
	
    });
}





/*$(function(){ 
	 $('#pay').trigger('click') ;
	});*/

/*$(function(){ //隐藏链接支付宝的菜单
	 $('#pay').css("display","none") ;
	});*/

/*$(function(){ 

	  var url = $('tijiao').attr("src"); 

	  alert(url); 

	});
*/
/*onload = function (){ 
    var a = document.querySelector ("a"); 
    a.onclick = function (){ 
        if (this.disabled){ 
            return false; 
        } 
        this.style.color = 'grey'; 
        this.disabed = true; 
    }; 
} 
*/




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



var str='';
var flag =true;
$(".sel .search-btn-a").click(function(){//查询列表
    var showcnt =10; //每页页数初始值
    var  myselect=document.getElementById("showcnt");
    if(myselect==null||myselect=="null"){
    }else{
        showcnt=myselect.options[myselect.selectedIndex].value;
    }

    var searchSel = document.getElementById('searchSel');
    if(searchSel==null||searchSel==''){
    }else{
        str=searchSel.options[searchSel.selectedIndex].value;
       // alert(str);
    }

    /* alert("str is "+str);*/
    if(flag){
        reload_table(1,showcnt);
    }
});
function reload_table(currentPage,showCount) {
    // alert("currentPage:"+currentPage+"---showCount:"+showCount+"---str:"+str);
    $.ajax({
        type: 'post',
        url: '/service/bankaccinfo/searchSel?str='+str+'&currentPage='+currentPage+'&showCount='+showCount,
        dataType: 'json',
        data: {
            str:str,
            currentPage: currentPage,
            showCount: showCount
        },
        success: function (data) {
        	 console.log(data.data);
            var tviplist = data.data.tradeInfo;
            if(tviplist.length==0||tviplist==null){
                $("#tfoot").show();
                $("#showData").hide();
                $(".pages1").hide();
                return;
            }
            var rsStr = "";
            for (var i = 0; i < tviplist.length; i++) {
                rsStr = rsStr + "<tr class='ss'>";
                rsStr = rsStr + "<th>" + (i+1) + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].orderno + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].txnum + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].txamnt + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].txdate + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].paytime + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].revbankaccno + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].revbankdepname + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].status + "</th>";
                rsStr = rsStr + "</tr>";
            }
            $("#tfoot").hide();
            $("#showData").html(rsStr);
            $("#showData").show();
           $(".pages1").html(data.data.page.pageStr);
           $(".pages1").show();

        },
        error: function (data) {
        	alert("错误")
        }
    });
}
