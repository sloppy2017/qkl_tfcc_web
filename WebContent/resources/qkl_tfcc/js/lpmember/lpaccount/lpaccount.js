/**
 * Created by Administrator on 2016/9/1 0001.
 */
$(function () {
    $.ajax({
        type: "POST",
        url: "../js/ajax/tsconfig.json",
        dataType: "json",
        success: function (responseData) {
            $("#tfcc").empty();
            $("#tfcc").html(responseData.tfcc);
            $("#jfcc").empty();
            $("#jfcc").html(responseData.jfcc);
            $("#huiyuanjiang").empty();
            $("#huiyuanjiang").html(responseData.huiyuanjiang);
            $("#goumaijiang").empty();
            $("#goumaijiang").html(responseData.goumaijiang);
            $("#mygoumaijiang").empty();
            $("#mygoumaijiang").html(responseData.mygoumaijiang);
        }
    })
});