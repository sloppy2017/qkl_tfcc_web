/**
 * Created by qw on 2016/8/31.
 */
//LP会员注册
var InterValObj=null; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount='';//当前剩余秒数
var flag=true;
// 手机验证
function sub_phone()
{
    var phone = $('.phone-safety');
    var phoneVal= $('.phone-safety').val();
    var myreg = /^(((13[0-9]{1})|159|153)+\d{8})$/;
    if(phoneVal.length==''|| phoneVal.length!=11)
    {
        phone.next().html('请输入正确手机号');
        return false;
    }else if(!myreg.test(phoneVal))
    {
        phone.next().html('请输入正确的手机号');
        phone.focus();
        return false;
    }else{
        phone.next().empty();
        //判断所有的都正确时执行这个发送的函数
        sendMessage(phoneVal);
        return true;
    }
}
function sendMessage(phoneVal,InterValObj,phone) {
    curCount = count;
    //删除input的属性值是在60内不能重新点击
    $(".yzm-safety").attr("disabled", "true");
    $(".yzm-safety").val("请在" + curCount + "秒内输入验证码");
    clearInterval(InterValObj);
    InterValObj=setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    //向后台发送处理数据
    console.log(phoneVal);
    $.ajax({
        type: "POST", //用POST方式传输     　　
        url: 'res.php', //目标地址.f
        dataType:'text',//数据格式:JSON
        //data: "dealType=" + dealType +"&uid=" + uid + "&code=" + code,
        data:{phone:phoneVal },
        success: function(data){
            alert('请求成功');
            if(data=="1"){//成功的处理
                alert('用户存在');
                $('.phone-safety').next().html('对不起，用户名已存在！');
            }else{               //失败的处理
                $('.phone-safety').next().html('恭喜你可以注册').css('color','green');
            }
        },error:function(){
            alert('请求失败');
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


$('.rephone input[name="yzm"]').blur(function(){
    reg = /^[a-zA-Z0-9]{6,}$/;
    if($(this).val()==''){
        $(this).next().next().html('请输入验证码');
        flag=false;
    }else if(!reg.test($(this).val())){
        $(this).next().next().html('验证码不正确或者不是本人手机号');
        flag=false;
    }else{
        $(this).next().next().empty();
        flag=true;
    }
})
function  ajaxsub(){
    var yamVal=$('.rephone input[name="yzm"]').val();
    if(flag){
        console.log(yamVal);
        $.ajax({
            type:'post',
            url:'res.php',
            datatype:"json",
            data:{yzmVal:yamVal},
            success:function(data){
                alert('成功')
                $('.mark1').show();
            },error:function(){
                alert('失败')
            }
        })
    }
}





//密码认证

//密码

$('.pwd input[name="pwd"]').blur(function () {
    //密码正则6-16字母数字或特殊字符
    reg = /^[a-zA-Z][a-zA-Z0-9|*|&|%|.|@|!]{5,15}$/;
    if ($(this).val() == '') {
        $(this).next().html('请输入密码');
        flag=false;
    }else if (!reg.test($(this).val())) {
        $(this).next().html('密码是以字母开头的6-16数字字母或特殊字符');
        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
        //ajax();
    }
});

$('.pwd input[name="pwd1"]').blur(function () {
    //密码正则6-16字母数字或特殊字符
    reg = /^[a-zA-Z][a-zA-Z0-9|*|&|%|.|@|!]{5,15}$/;
    if ($(this).val() == '') {
        $(this).next().html('请输入密码');
        flag=false;
    }else if (!reg.test($(this).val())) {
        $(this).next().html('密码是以字母开头的6-16数字字母或特殊字符');
        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
        //ajax();
    }
});
//确认密码
$('.pwd input[name="pwd2"]').blur(function(){
    var pVal = $('.pwd input[name="pwd1"]').val();
    if ($(this).val() == '') {
        $(this).next().html('请输入确认密码');
        flag=false;
    }else if($(this).val()!=pVal){
        $(this).focus();
        $(this).next().html('确认密码错误');
        flag=false;
    }else{
        $(this).next().empty();
        flag=true;
    }
})

function subpwd(){
    reg = /^[a-zA-Z][a-zA-Z0-9|*|&|%|.|@|!]{5,15}$/;
    var pwdVal = $('.pwd input[name="pwd"]').val();
    var pwd1Val = $('.pwd input[name="pwd1"]').val();
    var pwd2Val = $('.pwd input[name="pwd2"]').val();
    console.log(pwd1Val)
    if(flag){
        $.ajax({
            type:'post',
            url:'1.php',
            datatype:"json",
            data:{pwd1:pwd1Val,pwd:pwdVal},
            success:function(data){
                alert('成功')
            },error:function(){
                alert('失败')
            }
        })
    }

}





//身份认证
//姓名
$('.rerenzhen input[name="username"]').blur(function(){
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
        //ajax();
    }
})
//身份证的验证
$('.rerenzhen input[name="card"]').blur(function(){
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
        //ajax();
    }
})

function save(){
    var user = $('.rerenzhen input[name="username"]').val();
    var card =$('.rerenzhen input[name="card"]').val();
    if(flag){
        $.ajax({
            type:'post',
            url:'1.php',
            datatype:"json",
            data:{user:user,card:card},
            success:function(data){
                alert('成功')
            },error:function(){
                alert('失败')
            }
        })
    }
}















