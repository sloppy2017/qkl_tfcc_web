$(document).ready(function(){

var usercode = sessionStorage.getItem("usercode");
var num1=1;



$.jqPaginator('#paginationUser', {
		        totalPages: 100,
		        visiblePages:5,
		        currentPage: 1, 
		         prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
		         next: '<li class="next"><a href="javascript:;">下一页</a></li>',
		         page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
		         onPageChange: function (num1, type){
					 console.log(num1)
		         Userquery(num1)
		    }
		   });






//推荐
recommend()
function recommend(){
	console.log(usercode)
	$.ajax({
		type:"post",
		url:"/service/team/findVipNum",
		data:{"userCode":usercode},
		success:function(msg){
			console.log(msg)
			if(msg.data==null){
				$("#rNum").text("0");
			}
			$("#rNum").text(msg.data.rNum);
		}
	});
   }	
findTTnub()
function findTTnub(){
	console.log(usercode)
	$.ajax({
		type:"post",
		url:"/service/comacc/findMyAcc",
		data:{"userCode":usercode},
		success:function(msg){
			console.log(msg)
			var findTb=parseInt(msg.data.totalReward);
			$("#findTTReward").text(findTb);
		}
	});
   }







//会员查询
function Userquery(num1){
	console.log(usercode)
	$.ajax({
		type:"post",
		url:"/service/team/findVipPage",
		data:{"str":"all","userCode":usercode,"currentPage":num1},
		success:function(msg){
			 $("tr").remove(".table_tr");
			var msgtvi=msg.data.tviplist;
			console.log(msg)
		$.each(msgtvi,function(i,n) {
			var msgPhone=msgtvi[i].phone;
			var msgRela_level=msgtvi[i].rela_level;
		    var msgReal_name=msgtvi[i].real_name;
		    if(msgReal_name==""){
		    	msgReal_name="无";
		    }
		    var msgrg_time=msgtvi[i].rg_time;
		    var msgtime = msgrg_time.replace(/年/, "-").replace(/月/, "-").replace(/日/,"")
//		    console.log(msgtime);
//			console.log(msgStatus)
			var ajaxwrap="<tr class='table_tr'><td>"+msgtime+"</td><td>"+msgPhone+"</td><td>"+msgReal_name+"</td><td>"+msgRela_level+"</td></tr>"
		$(".table_order").find("tbody").append(ajaxwrap);
		});
		}
	});
	
   }
})