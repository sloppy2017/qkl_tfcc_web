$(document).ready(function(){
	$(".uesr_phone").html(userPhone);
	 var num=1;
	 var num1=1;
	 console.log(usercode);
	//收款账号
	gathering();
	
//订单页码插件调用
	$.jqPaginator('#paginationNews', {
		        totalPages: 100,
		        visiblePages:5,
		        currentPage: 1, 
		         prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
		         next: '<li class="next"><a href="javascript:;">下一页</a></li>',
		         page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
		         onPageChange: function (num, type){
		         	$(".table_order1").find("tr").remove(".table_tr");
		         	 serverTitle=$("#server_title").val();
					 orderStatus=$("#orderStatus option:selected").val();
//					 console.log(orderStatus);
//					 console.log(num)
		         orderInquiry(orderStatus,num)
		    }
		   });
//订单查询
//完成
$("#orderFinsh").click(function(){
	orderStatus="1";
	orderInquiry(orderStatus,1);
})
//代付款
$("#orderWait").click(function(){
	orderStatus="0";
	orderInquiry(orderStatus,1);
})
//取消
$("#orderCancel").click(function(){
	orderStatus="9";
	orderInquiry(orderStatus,1);
})
var sprSchoolName=1;
console.log(usercode);

//==================
//收款账号
function gathering(){
	$.ajax({
	type:"post",
	url:webURL+"/service/bankaccinfo/info",
	async:true,
	data:{"userCode":usercode},
	success:function(msg){
		console.log(msg);
		var orgName=msg.data.orgName;
		var bankaccno=msg.data.bankaccno;
		$("#gatherUser").html(orgName);
		$("#taoboaNub").html(bankaccno);
	}
});
}
//订单查询
function orderInquiry(orderStatus,num){
	 $("tr").remove(".table_tr");
	console.log(usercode);
	$.ajax({
		type:"post",
		url:webURL+"/service/bankaccinfo/searchSel",
		async:true,
		data:{"str":orderStatus,"currentPage":num,"showCount":10,"userCode":usercode},
		success:function(msg){
			console.log(msg);
			var msgdata=msg.data.tradeInfo;
			console.log(msg)
		$.each(msgdata,function(i,n) {
			var msgStatus=msgdata[i].status;
			var msgTxamnt=parseInt(msgdata[i].txamnt);
		    var msgtxnum=msgdata[i].txnum;
		    var revbankaccno=msgdata[i].revbankaccno;
		    var mstxdate=msgdata[i].txdate;
		    var msgtime = mstxdate.replace(/年/, "-").replace(/月/, "-").replace(/日/,"")
//		    console.log(msgtime);
//			console.log(msgStatus)
			var ajaxwrap="<tr class='table_tr'><td class='order_time'>"+msgtime+"</td><td>"+msgtxnum+"</td><td class='zhifub'>"+revbankaccno+"</td><td class='timecolor zonge'>"+msgTxamnt+"</td><td>"+msgStatus+"</td></tr>"
		$(".table_order").find("tbody").append(ajaxwrap);
		});
		}
	});
   }
	
})