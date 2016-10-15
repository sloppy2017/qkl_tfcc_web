$(document).ready(function(){
	$(".uesr_phone").html(userPhone);
	 var num=1;
	 var orderStatus="";
	 var totalPage=0;
	$.ajax({
		type:"post",
		url:webURL+"/service/comacc/findout",
		async:false,
		data:{"status":orderStatus,"currentPage":num,"showCount":10,"userCode":usercode},
		success:function(msg){
			console.log(msg);
			totalPage=msg.data.page.totalPage;
		}
	});
//订单页码插件调用
	$.jqPaginator('#paginationNews', {
		        totalPages: totalPage,
		        visiblePages:5,
		        currentPage: 1, 
		         prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
		         next: '<li class="next"><a href="javascript:;">下一页</a></li>',
		         page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
		         onPageChange: function (num, type){
		         	$("tr").remove(".table_tr"); 
		         	$(".table_order1").find("tr").remove(".table_tr");
		         	 serverTitle=$("#server_title").val();
					 orderStatus=$("#orderStatus option:selected").val();
//					 console.log(orderStatus);
//					 console.log(num)
		         orderInquiry(orderStatus,num)
		    }
		   });
//转账查询
//所有
$("#orderAll").click(function(){
	orderStatus="";
	orderInquiry(orderStatus,1);
})
//成功
$("#orderFinsh").click(function(){
	orderStatus="1";
	orderInquiry(orderStatus,1);
})
//转账中
$("#orderWait").click(function(){
	orderStatus="2";
	orderInquiry(orderStatus,1);
})
//失败
$("#orderCancel").click(function(){
	orderStatus="0";
	orderInquiry(orderStatus,1);
})
var sprSchoolName=1;
//==================
//订单查询
function orderInquiry(orderStatus,num){
	 $("tr").remove("tbody");
//	console.log(usercode);
	$.ajax({
		type:"post",
		url:webURL+"/service/comacc/findout",
		async:true,
		data:{"status":orderStatus,"currentPage":num,"showCount":10,"userCode":usercode},
		success:function(msg){
			var msgdata=msg.data.outList;
			console.log(msg);
		$.each(msgdata,function(i,n) {
			var msgStatus=msgdata[i].out_status;
			var msgTxamnt=parseInt(msgdata[i].txamnt);
		    var msgtxnum=msgdata[i].outamnt;
		    var revbankaccno=msgdata[i].recipient;
		    var mstxdate=msgdata[i].outdate;
		    var msgtime = mstxdate.replace(/年/, "-").replace(/月/, "-").replace(/日/,"");
			var ajaxwrap="<tr class='table_tr'><td class='order_time'>"+msgtime+"</td><td>"+msgtxnum+"</td><td class='zhifub'>"+revbankaccno+"</td><td>"+msgStatus+"</td></tr>"
		$(".transfer_table").find("tbody").append(ajaxwrap);
		});
		}
	});
   }
	
})