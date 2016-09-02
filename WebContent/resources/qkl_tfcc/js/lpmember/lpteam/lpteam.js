/**
 * Created by Administrator on 2016/9/1 0001.
 */

$(function () {
    $.ajax({
        type: "POST",
        url: "../js/ajax/tsconfig.json",
        dataType: "json",
        success: function (responseData) {
            $(".lphy").empty();  //先清空原来的表格内容
            $(".lphy").html(responseData.lphy);  //动态接受TFCC数额
        }
    })
});
