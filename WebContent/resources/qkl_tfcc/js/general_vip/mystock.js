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
             		 $("#revbankdepname").text(data.data.depositBankname);
             		 $("#checkphone").text(data.data.checkphone);
             			
             }else{
             	alert(data.message);
             }
        }
    });
}


$("input[name='txnum']").change(function(){
	var url = "/service/bankaccinfo/PayMoney?"+$("form").serialize();
	url = encodeURI(url);
	$.ajax({
        type: "post",
        url:url,
        dataType: "json",
        success: function (data) {
        	if(data.success){
        		$("input[name='txamnt']").val(data.data);
        	}else{
        		alert(data.message);
        	}  
        }
    });
});

$('#buy').click(function(){
	var url = "/service/bankaccinfo/tradebuy?"+$("form").serialize();
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
            
        }
    });


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



var str='';
var flag =true;
$(".sel a").click(function(){
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
    }

    /* alert("str is "+str);*/
    if(flag){
        reload_table(1,showcnt);
    }
});
function reload_table(currentPage,showCount) {
    var rsStr = "";
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
            var tviplist = data.data.tradeInfo;
            var tablecols = "<tr> \n"
                + " <th>购买时间</th> \n"
                + "<th>购买份数</th> \n"
               /* + "<th>支付方式</th> \n"*/
                +"<th>总计金额（元）</th>\n"
                +"<th>付款状态</th>\n"
                + "</tr> \n";
            rsStr= tablecols;
            for (var i = 0; i < tviplist.length; i++) {
                rsStr = rsStr + "<tr class='ss'>";
                rsStr = rsStr + "<th>" + tviplist[i].txdate + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].txnum + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].txamnt + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].status + "</th>";
                rsStr = rsStr + "</tr>";
            }
            $(".result-tab").html( rsStr);
           // console.log(data.data.page.pageStr);
            $(".pages1").html(data.data.page.pageStr);

        },
        error: function (data) {

        }
    });
}
