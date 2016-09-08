
var str='';
var flag =true;
$(".type-btn a").click(function () {
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
            str = str+'A,';
        }
        if(document.getElementById("lvb").checked==true){
            str = str+'B,';
        }
        if(document.getElementById("lvc").checked==true){
            str = str+'C,';
        }
    }
    alert("str is "+str);
    if(flag){
        reload_table(1,showcnt);
    }
})

function reload_table(currentPage,showCount) {
    console.log(str)
    var rsStr = "";
    $.ajax({
        type: 'post',
//		url: '../../..rvice/user/login/',
        url: '../../../service/team/findVipPage/',
        dataType: 'json',
        data: {
            str:str,
            currentPage: currentPage,
            showCount: showCount
        },
        success: function (data) {
            /*  alert('请求成功');/*  */
            var message = data.message;
            /*    console.log("success is " + data.success);
             console.log("data is " + data.map);
             console.log("data.userList is " + data.data.userList); */
            var usList = data.data.userList;
            console.log("usList.length " + usList.length);
            /*   console.log("data.data.page.pageStr " + data.data.page.pageStr); */
            var tablecols = "<tr> \n"
                + " <th>会员级别</th> \n"
                + "<th>注册时间</th> \n"
                + "<th>会员账号</th> \n"
                +"<th>推荐人</th>\n"
                +"<th>用户名 </th>\n"
                +"<th>购买量(股)</th>\n"
                + "</tr> \n";
            rsStr= tablecols;
            for (var i = 0; i < usList.length; i++) {
                rsStr = rsStr + "<tr class='ss'>";
                console.log("usList(" + i + ") name is " + usList[i].name);
                rsStr = rsStr + "<th>" + usList[i].name + "</th>";
                rsStr = rsStr + "<th>" + usList[i].test_user_id + "</th>";
                rsStr = rsStr + "<th>" + usList[i].name + "</th>";
                rsStr = rsStr + "<th>" + usList[i].test_user_id + "</th>";
                rsStr = rsStr + "<th>" + usList[i].name + "</th>";
                rsStr = rsStr + "<th>" + usList[i].test_user_id + "</th>";
                rsStr = rsStr + "</tr>";
            }

            console.log("rsStr " + rsStr);


//			 $(".result-tab").append(rsStr);
            var x =$(".result-tab").html( rsStr)

            console.log(x)

//			 $(".pages1").append(data.data.page.pageStr);
            $(".pages1").html(data.data.page.pageStr);

//			$('.phone').val(data.data.phone);
            /*  alert("message is " + message); */


//			 if(data.success){
//				 window.location.href ="../index.html";
//			 }
        }, error: function (data) {

        }
    })
}




