/**
 * Created by qw on 2016/9/2.
 */
$(function(){
    //到转账界面
    $('.zhuanzhang').click(function(){
        $('.mains>div').hide();
        $('.trans-account').show();
    })
})
//select选中那个就显示那个转账的表单
function gradeChange(){
    var objS = document.getElementById("pid");
    var grade = objS.options[objS.selectedIndex].value;
    alert(grade);
    if(objS.options[0].selected==false){
        $('.trans-jfcc').show();
        $('.trans-tfcc').hide()
    }else{

        $('.trans-tfcc').show();
        $('.trans-jfcc').hide()
    }
}



//tfcc提交函数
function  save(){
    var money =  $('.trans-tfcc input[name="money"]').val();
    //var reg =/^([1-9][0-9]*)?[0-9]\.[0-9]{2}$/;
    var reg =/^([1-9][0-9]*)?[0-9]$/;
    if(money==''){
        //$('.errorc').val('输入金额不能为空')
        $('errorc').html('输入金额不能为空');
        return false
    }else if(!reg.test(money)){
        $('.errorc').html('输入金额方式不对')
        return false
    }else{
        $('.errorc').empty();
        $(".dividend").fadeIn(500);
        return true;
    }
}
function tfcc_sub(){
    var money =  $('.trans-tfcc input[name="money"]').val();
    console.log(money);
    $.ajax({
        type:'post',
        url:'res.php',
        data:{money:money},
        success:function(data){
            alert('成功')
            $('.mark2').fadeIn(500);
        },
        error:function(data){
            alert('失败')
        }
    })
    $(".dividend").fadeOut(500);
}
function exit(){
    $(".dividend").fadeOut(500);
}






//jfcc提交函数
function jfcc_sub(){
    var money =  $('.trans-jfcc input[name="money"]').val();
    //var reg =/^([1-9][0-9]*)?[0-9]\.[0-9]{2}$/;
    var reg =/^([1-9][0-9]*)?[0-9]$/;
    if(money==''){
        $('.errorc').html('输入金额不能为空')
        return false;
    }else if(!reg.test(money)){
        $('.errorc').html('输入金额方式不对')
        return false;
    }else{
        $('.errorc').empty();
        //console.log(money);
        $.ajax({
            type:'post',
            url:'.../',
            data:{money:money},
            success:function(data){

                $(".mark2").fadeOut(500);
            },
            error:function(){
            }
        })
        return true;
    }
}






