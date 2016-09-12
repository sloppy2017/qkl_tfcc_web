/**
 * Created by Administrator on 2016/9/1 0001.
 */

$(function () {
    $('.security-t').click(function () {
        $.ajax({
            type: "POST",
            url: "../../../resources/qkl_tfcc/js/tsconfig.json",
            dataType: "json",
            success: function (responseData) {
                $(".lphuiyuan").empty();
                $(".lphuiyuan").html(responseData.lphuiyuan);
            }
        })
    })
});