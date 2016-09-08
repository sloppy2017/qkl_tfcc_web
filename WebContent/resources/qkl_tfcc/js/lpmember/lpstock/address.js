/**
 * Created by Administrator on 2016/9/5 0005.
 */
$(function () {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        success: function (responseData) {
            $(".shou_k").empty();  //先清空原来的表格内容
            $(".shou_k").html(responseData);  //动态接受TFCC数额
            $(".dizhi").empty();  //先清空原来的表格内容
            $(".dizhi").html(responseData); //动态接受JFCC数额
            $(".zhanghao").empty();  //先清空原来的表格内容
            $(".zhanghao").html(responseData); //动态接受JFCC数额
            $(".yinhang").empty();  //先清空原来的表格内容
            $(".yinhang").html(responseData); //动态接受JFCC数额
            $(".dianhua").empty();  //先清空原来的表格内容
            $(".dianhua").html(responseData); //动态接受JFCC数额
        }
    })
});

//数量传值
$(function () {
    //失去焦点时
    $("#ttext").blur(function () {
        $.ajax({
            type: "POST",
            url: "",
            dataType: "json",
            data:{
                ttext:$("#ttext").val()
            },
            success: function (responseData) {
                $(".goumaishuliang").empty();  //先清空原来的表格内容
                $(".goumaishuliang").val(responseData);  //动态接受TFCC数额
            }
        })
    })

    //点击确认购买
    $("#button").click(function () {
        $.ajax({
            type:"POST",
            url:"",
            data:{
                goumaishuliang:$(".goumaishuliang").val()
            },
            ////success: function (responseData) {
            ////    if(){
            ////
            ////    }else{
            ////
            ////    }
            //}
        })
    })
});