/**
 * Created by qw on 2016/9/2.
 */
$(function(){
    //到转账界面
    $('.zhuanzhang').click(function(){
    	//alert("转账功能尚未开通");
        $('.mains>div').hide();
        $('.trans-account').show();
    })
})
//select选中那个就显示那个转账的表单
function gradeChange(){
    var objS = document.getElementById("pid");
    var grade = objS.options[objS.selectedIndex].value;
    //alert(grade);
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

//查询条件

function insearch(){
    var obj = document.getElementById('mySelect')
    alert(obj.selectedIndex);
    if(obj.options[1].selected==true){
        reg=/([\u4e00-\u9fa5]{2,4})/;
        var chaxunVal = $('input[name="chaxun"]').val()
        if(reg.test(chaxunVal)){
            $.ajax({
                type:'post',
                url:'res.php',
                data:{chaxunVal:chaxunVal},
                dataType:'json',
                success:function(){

                },errror:function(){
                }
            })
        }
    }

    if(obj.options[0].selected==true){
        reg=/^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$/;
        var chaxunVal = $('input[name="chaxun"]').val()
        if(reg.test(chaxunVal)){
            $.ajax({
                type:'post',
                url:'res.php',
                data:{chaxunVal:chaxunVal},
                dataType:'json',
                success:function(){

                },errror:function(){
                }
            })
        }
    }
}

//给不同的用户发放额度

/*
function sub_money(){
    regP =/^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$/;
    regM =/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
    var phoneVal = $('.award-num input[name="phone"]').serialize();
    var moneyVal = $('.award-num input[name="money"]').serialize();
    if(!regP.test(phoneVal) && !regM.test(moneyVal)){
        return false;
    }
    $.ajax({
        type:'post',
        url:'res.php',
        data:{phoneVal:phoneVal,moneyVal:moneyVal},
        dataType:'json',
        success:function(data){

        },errror:function(data){
        }
    });
}*/








