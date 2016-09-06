/**
 * Created by qw on 2016/9/1.
 */
var  flag = true;
//实名认证
$('.form input[name="autonym"]').blur(function(){
    // 4、2~4个汉字
    var reg=/^[\u4E00-\u9FA5]{2,4}$/;
    if ($(this).val() == '') {
        $(this).next().html('请输入真实姓名');
        flag=false;
    }if(!reg.test($(this).val())){
        $(this).next().html('请输入真实姓名');
        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
    }
})
//身份证的验证
$('.form input[name="card"]').blur(function(){
    var reg = /^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)?$/;
    if ($(this).val() == '') {
        $(this).next().html('请输入身份证号');
        flag=false;
    }if(!reg.test($(this).val())){
        $(this).next().html('请输入正确的身份证号');
        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
    }
})
//手机号
$('.form input[name="phone"]').blur(function(){
    var reg = /^(((13[0-9]{1})|159|153)+\d{8})$/;
    if ($(this).val() == '') {
        $(this).next().html('请输入正确的手机号');
        flag=false;
    }if(!reg.test($(this).val())){
        $(this).next().html('请输入正确的手机号');
        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
    }
})
//微信号码
$('.form input[name="weixin"]').blur(function(){
    var reg=/^[a-zA-Z\d_]{5,}$/;
    if ($(this).val() == '') {
        $(this).next().html('请输入正确的微信号');
        flag=false;
    }if(!reg.test($(this).val())){
        $(this).next().html('请输入正确的微信号');
        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
    }
})

//支付宝
$('.form input[name="alipay"]').blur(function(){
    var reg = /^(((13[0-9]{1})|159|153)+\d{8})$/;
    if ($(this).val() == '') {
        $(this).next().html('请输入正确的支付宝号');
        flag=false;
    }if(!reg.test($(this).val())){
        $(this).next().html('请输入正确的支付宝号');
        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
    }
})

$('.form input[name="address"]').blur(function(){
    var reg=/ [\u4e00-\u9fa5]/;
    if ($(this).val() == '') {
        $(this).next().html('通讯地址不能为英文');
        flag=false;
    }if(!reg.test($(this).val())){
        $(this).next().html('通讯地址不能为英文');
        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
    }
})
//邮编
$('.form input[name="postcode"]').blur(function(){
   var reg=/[1-9]\d{5}(?!\d)/;
    if ($(this).val() == '') {
        $(this).next().html('请输入正确的邮编');
        flag=false;
    }if(!reg.test($(this).val())){
        $(this).next().html('请输入正确的邮编');
        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
    }
})
function sub_save(){
        var autonymVal = $('.form input[name="autonym"]').val();
        var cardVal = $('.form input[name="card"]').val();
        var phoneVal = $('.form input[name="phone"]').val();
        var weixinVal = $('.form input[name="weixin"]').val();
        var alipayVal = $('.form input[name="alipay"]').val();
        var addressVal = $('.form input[name="address"]').val();
        var postcodeVal =$('.form input[name="postcode"]').val();
      console.log( phoneVal);
    if(flag){
        $.ajax({
            type:'post',
            url:'...',
            dataType:'json',
            data:{
                autonymVal:autonymVal,
                cardVal:cardVal,
                weixinVal: weixinVal,
                alipayVal:alipayVal,
                addressVal:addressVal,
                postcodeVal: postcodeVal,
            },
            success:function(data){
                alert('成功')
            },
            error:function(data){
                alert('失败')
            }
        })
    }
}
