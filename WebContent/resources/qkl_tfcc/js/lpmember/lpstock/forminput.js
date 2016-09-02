/**
 * Created by Administrator on 2016/8/29 0029.
 */
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
$(function () {
    $.ajax({
        type: "POST",
        url: "package.json",
        dataType: "json",
        data:{
            ttext : $("#ttext").val()
        },
        success: function (responseData) {

        }
    })
});