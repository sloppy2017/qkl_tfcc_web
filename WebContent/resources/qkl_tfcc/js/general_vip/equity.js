/**
 * Created by qw on 2016/9/18.
 */

var str='';
var flag =true;
$(".sel a").click(function(){
    var showcnt =10; //每页页数初始值
    var  myselect=document.getElementById("showcnt");
    if(myselect==null||myselect=="null"){
    }else{
        showcnt=myselect.options[myselect.selectedIndex].value;
    }

    var searchSel = document.getElementById('searchSel')
    if(searchSel==null||searchSel==''){
    }else{
        str=searchSel.options[searchSel.selectedIndex].value;
    }

    // alert("str is "+str);
    if(flag){
        reload_table(1,showcnt);
    }
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
            var tablecols = "<tr> \n"
                + " <th>购买时间</th> \n"
                + "<th>购买份数</th> \n"
                + "<th>支付方式</th> \n"
                +"<th>总计金额（元）</th>\n"
                +"<th>付款状态</th>\n"
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
