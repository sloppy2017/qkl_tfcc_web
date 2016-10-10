$(document).ready(function(){
	var usercode = sessionStorage.getItem("usercode");
	 var num=1;
	 var num1=1;
	 console.log(usercode)
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
		         	 serverTitle=$("#server_title").val();
					 orderStatus=$("#orderStatus option:selected").val();
//					 console.log(orderStatus);
//					 console.log(num)
		         orderInquiry(orderStatus,num)
		    }
		   });
//订单查询
$("#orderQuery").click(function(){
	orderStatus=$("#orderStatus option:selected").val();
	orderInquiry(orderStatus,1);
})
var sprSchoolName=1;
console.log(orderStatus);
//会员页码插件调用
//==================
//收款账号
function gathering(){
	$.ajax({
	type:"post",
	url:webURL+"service/bankaccinfo/info",
	async:true,
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
	console.log(usercode)
	$.ajax({
		type:"post",
		url:webURL+"service/bankaccinfo/searchSel",
		async:true,
		data:{"str":orderStatus,"currentPage":num,"showCount":10,"userCode":usercode},
		success:function(msg){
			 $("tr").remove(".table_tr");
			var msgdata=msg.data.tradeInfo;
			console.log(msg)
		$.each(msgdata,function(i,n) {
			var msgStatus=msgdata[i].status;
			var msgTxamnt=parseInt(msgdata[i].txamnt);
		    var msgtxnum=msgdata[i].txnum;
		    var mstxdate=msgdata[i].txdate.substr(0,11);
		    var msgtime = mstxdate.replace(/年/, "-").replace(/月/, "-").replace(/日/,"")
//		    console.log(msgtime);
//			console.log(msgStatus)
			var ajaxwrap="<tr class='table_tr'><td>"+msgtime+"</td><td>"+msgtxnum+"</td><td class='timecolor'>"+msgTxamnt+"</td><td>"+msgStatus+"</td></tr>"
		$(".table_order").find("tbody").append(ajaxwrap);
		});
		}
	});
   }






























});

