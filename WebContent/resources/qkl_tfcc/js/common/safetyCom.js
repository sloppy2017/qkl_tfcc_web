/**
 * Created by qw on 2016/8/31.
 * 安全中兴表单验证
 */

var InterValObj=null; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount='';//当前剩余秒数
// 手机验证
function valid_phone()
{
    var oldphone = $(".oldphone").text().trim();
	var $this = $('.phone-safety');
	$this.val($this.val().trim());
    var phoneVal= $this.val().trim();
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
    if(phoneVal==''){
    	$this.val("请输入手机号");
    	$this.addClass("errorTip");
        return false;
    }else if(phoneVal.length!=11||!myreg.test(phoneVal)){
    	$this.val("请输入正确的手机号");
    	$this.addClass("errorTip");
        return false;
    }else if(oldphone === phoneVal){
    	$this.val("新手机号不能与旧手机号一致");
    	$this.addClass("errorTip");
        return false;
    }else{
    	$this.removeClass("errorTip");
    	return true;
    }
    
    
}
//获取验证码
function getVCode(phone) {
	console.log(phone);
	var validPhone = valid_phone();//验证手机号是否正确
    if(!validPhone){
        return;
    }
	curCount = count;
    //删除input的属性值是在60内不能重新点击
    $(".yzm-safety").attr("disabled", "true");
    $(".yzm-safety").val("请在" + curCount + "秒内输入验证码");
    clearInterval(InterValObj);
    InterValObj=setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    //向后台发送处理数据
    
    $.ajax({
        type:'POST',
        url:'/service/user/sendsms?phone='+phone,
//         data:{phone:newPhone},
        dataType:'JSON',
        success: function (json) {
            // 验证码发送成功，把验证码先存到页面一个变量中       
             if(json.success){
                // settime(this);
             }else{
                 alert(json.message);
             }
            //console.log(data);
        },
        error: function (a,b,c) {
     	   //console.log(a.readyState+"\n"+b+"\n"+c);
            console.log("获取短信验证码失败");
        }
    });
}
//timer处理函数
function SetRemainTime() {
    if (curCount == 0) {
        clearInterval(InterValObj);       //停止计时器
        $(".yzm-safety").removeAttr("disabled"); //启用按钮
        $(".yzm-safety").val("重新发送验证码");
    } else {
        curCount--;
        $(".yzm-safety").val("请在" + curCount + "秒内输入验证码");
    }
}


$('input[name="yzm"]').blur(function(){
	return valid_yzm("yzm");
});
//验证验证码
function valid_yzm(id){
	var $this = $("#"+id);
	$this.val($this.val().trim());
	var code = $this.val().trim();
	var reg = /^[0-9]\d{5}$/;
    if(code==''){
    	$this.val('请输入验证码');
    	$this.addClass('errorTip');
        return false;
    }else if(!reg.test(code)){
    	$this.val('验证码格式不正确');
    	$this.addClass('errorTip');
        return false;
    }else{
    	$this.removeClass('errorTip');
        return true;
    }
}
/**
 * 密码认证
 */

//密码

$('.pwd input[name="pwd"]').blur(function () {
    //密码正则6-16字母数字或特殊字符
    var reg = /^[a-zA-Z][a-zA-Z0-9|*|&|%|.|@|!]{5,15}$/;
    if ($(this).val() == '') {
        $(this).val('请输入密码');
        $(this).addClass("errorTip");
        return false;
    }else if (!reg.test($(this).val())) {
        $(this).val("密码是以字母开头的6-16数字字母或特殊字符");
        $(this).addClass("errorTip");
        return false;
    }else {
        $(this).removeClass("errorTip");
        return true;
    }
});

//确认密码
$('.pwd input[name="pwd2"]').blur(function(){
    var pVal = $('.pwd input[name="pwd1"]').val();
    if ($(this).val() == '') {
    	$(this).val("请输入确认密码");
        $(this).addClass("errorTip");
        return false;
    }else if($(this).val()!=pVal){
        $(this).val('两次密码不一致');
        $(this).addClass("errorTip");
        return false;
    }else{
        $(this).removeClass("errorTip");
        return true; 
    }
});


/**
 * 身份认证
 */
//姓名
$('.rerenzhen input[name="username"]').blur(function(){
    // 4、2~4个汉字
    var reg=/^[\u4E00-\u9FA5]{2,4}$/;
    if ($(this).val() == '') {
        $(this).val('请输入真实姓名');
        $(this).addClass("errorTip");
        return false;
    }if(!reg.test($(this).val())){
        $(this).val('请输入真实姓名');
        $(this).addClass("errorTip");
        return false;
    }else {
        $(this).removeClass('errorTip');
        return true;
    }
});
//身份证的验证
$('.rerenzhen input[name="card"]').blur(function(){
    var reg = /^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)?$/;
    if ($(this).val() == '') {
        $(this).val('请输入身份证号');
        $(this).addClass("errorTip");
        return false;
    }if(!reg.test($(this).val())){
        $(this).val('请输入正确的身份证号');
        $(this).addClass("errorTip");
        return false;
    }else {
        $(this).removeClass('errorTip');
        return true;
    }
});
/**
 * 初始化页面
 */
$(function(){
    $('.pwd').click(function(){
        $(this).hide();
        $('.main-top ,.amend').hide();
        $('.pwd').show();
    });
    $('.phone').click(function(){
        $(this).hide();
        $('.main-top ,.amend').hide();
        $('.rephone').show();
    });
    $('.renzhen').click(function(){
        $(this).hide();
        $('.main-top ,.amend').hide();
        $('.rerenzhen').show();
    });
});














