/**
 * Created by Administrator on 2016/9/1 0001.
 */
$(function () {
    $.ajax({
        type: "POST",
        url: "../../../resources/qkl_tfcc/js/tsconfig.json",
        dataType: "json",
        success: function (responseData) {
            $(".tfcc").empty();
            $(".tfcc").html(responseData.tfcc);
            $(".jfcc").empty();
            $(".jfcc").html(responseData.jfcc);
            $(".myjiangli").empty();
            $(".myjiangli").html(responseData.myjiangli);
            $(".tuijianjiang").empty();
            $(".tuijianjiang").html(responseData.tuijianjiang);
            $("#mygoumaijiang").empty();
            $("#mygoumaijiang").html(responseData.mygoumaijiang);
        }
    })
});