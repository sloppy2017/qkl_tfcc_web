/**
 * Created by Administrator on 2016/9/1 0001.
 */
$(function () {
    $("#button").click(function () {
        $.ajax({
            type : "POST",
            url : "",
            dataType :"json",
            data :{
                "us":$("#us").val(),
                "name":$("#name").val(),
                "sfzh":$("#sfzh").val(),
                "shouji":$("#shouji").val(),
                "password1":$("#password1").val(),
                "password2":$("#password2").val(),
                "ewjy":$("#ewjy").val(),
                "Zip-code":$("#Zip-code").val()
            }
        })
    })
})
