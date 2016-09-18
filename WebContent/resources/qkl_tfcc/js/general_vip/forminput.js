/**
 * Created by Administrator on 2016/8/29 0029.
 */
$.ajax({
        type: "post",
        url: "/service/bankaccinfo/info",
        dataType: "json",
        success: function (data) {	
        	 if(data.success){
             	
             		 $("#revorgname").html(data.data.revorgname);
             		 $("#revbankaccno").html(data.data.revbankaccno);
             		 $("#revbankdepname").html(data.data.revbankdepname);
             		 $("#checkphone").html(data.data.checkphone);
             		 alert(data.data.checkphone);
             	
             }else{
             	alert(data.message);
             }
        }
    });


var status='';
var flag =true;

$('.type-btn1').click(function(){
	alert(222);
	$.ajax({
        type: "post",
        url: "/service/bankaccinfo/tradebuy",
        dataType: "json",
        data:{
            ttext : $("#ttext").val(),
            revorgname: $("#revorgname").val(),
            revbankaccno: $("#revbankaccno").val(),
            revbankdepname: $("#revbankdepname").val()
        },
        success: function (date) {
            alert(date.Message);
            
        }
    });


});


	
function reload_table(currentPage,showCount) {
	
    var rsStr = "";
    $.ajax({
        type: 'post',
        url: '/service/bankaccinfo/tradeinfo?status='+status+'&currentPage='+currentPage+'&showCount='+showCount,
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
                + "<th>总计金额（元）</th> \n"
                +"<th>付款状态 </th>\n"
//                +"<th>购买量(股)</th>\n"
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
//            console.log(data.data.page.pageStr);
            $(".pages1").html(data.data.page.pageStr);

        }, 
        error: function (data) {

        }
    });
}







function myfilter(e) {
    var reg = /^\d{3}\.\d{2}$/;
    var obj=e.srcElement || e.target;
    var dot=obj.value.indexOf(".");//alert(e.which);
    var  key=e.keyCode|| e.which;
    if(key==8 || key==9 || key==46 || (key>=37  && key<=40))//这里为了兼容Firefox的backspace,tab,del,方向键
        return true;
    if (key<=57 && key>=48) { //数字
        if(dot==-1)//没有小数点
            return true;
        else if(obj.value.length<=dot+1)//两位小数
            return true;
    } else if((key==46) && dot==-1){//小数点
        return true;
    }
    return false;
}
