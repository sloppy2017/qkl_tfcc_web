
var str='';
var flag =true;

function query(){
    var showcnt =10; //每页页数初始值
    var  myselect=document.getElementById("showcnt");
    if(myselect==null||myselect=="null"){
    }else{
        showcnt=myselect.options[myselect.selectedIndex].value;
    }
    str='';
    if(document.getElementById("quan").checked==true){
        document.getElementById("lva").checked =true;
        document.getElementById("lvb").checked =true;
        document.getElementById("lvc").checked =true;
    }
    if(document.getElementById("quan").checked==true){
        str = str+'ALL,';
    }
    else{
        if(document.getElementById("lva").checked==true){
            str = str+"'A',";
        }
        if(document.getElementById("lvb").checked==true){
            str = str+"'B',";
        }
        if(document.getElementById("lvc").checked==true){
            str = str+"'C',";
        }
    }
    alert("str is "+str);
    if(flag){
        reload_table(1,showcnt);
    }
    
    $('.page1 ul li').click(function(){
        $(this).addClass('bg-color').siblings().removeClass('bg-color');
    });
}
function reload_table(currentPage,showCount) {
    var rsStr = "";
    alert("currentPage:"+currentPage+"---showCount:"+showCount+"---str:"+str);
    $.ajax({
        type: 'post',
        url: '/service/team/findVipPage?str='+str+"&currentPage="+currentPage+"&showCount="+showCount,
        dataType: 'json',
        /*data: {
            str:str,
            currentPage: currentPage,
            showCount: showCount
        },*/
        success: function (data) {
            var tviplist = data.data.tviplist;
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
                rsStr = rsStr + "<th>" + tviplist[i].reg_time + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].phone + "</th>";
                rsStr = rsStr + "<th>" + tviplist[i].real_name + "</th>";
                rsStr = rsStr + "</tr>";
            }
            $(".result-tab").html( rsStr);
            $(".pages1").html(data.data.page.pageStr);

        }, 
        error: function (data) {

        }
    });
}

	
	




