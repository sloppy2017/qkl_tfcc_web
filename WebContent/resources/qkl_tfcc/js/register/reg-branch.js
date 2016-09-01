/**
 * Created by qw on 2016/8/23.
 */
//网点会员注册
var InterValObj=null; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount='';//当前剩余秒数
// 手机验证
function sub_phoneB()
{
    var phone = $('.phoneB');
    var phoneVal= $('.phoneB').val();
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
    $(".yzmB").attr("disabled", "true");
    $(".yzmB").val("请在" + curCount + "秒内输入验证码");
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
                $('.phone').next().html('对不起，用户名已存在！');
            }else{               //失败的处理
                $('.phone').next().html('恭喜你可以注册').css('color','green');
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
        $(".yzmB").removeAttr("disabled"); //启用按钮
        $(".yzmB").val("重新发送验证码");
    } else {
        curCount--;
        $(".yzmB").val("请在" + curCount + "秒内输入验证码");
    }
}
//-------------------------------------------------------------//

var flag =true; //默认情况下是成功的；
//判断验证码发送手机收到的验证码
$('.input input[name="yzmB"] ').blur(function(){
    //密码正则6-16字母数字或特殊字符
    reg = /^[a-zA-Z0-9]{6,}$/;
    if($(this).val()==''){
        //$(this).focus();
        $(this).next().next().html('请输入验证码');
        flag=false;
    }else if(!reg.test($(this).val())){
        //$(this).focus();
        $(this).next().next().html('验证码不正确');
        flag=false;
    }else{
        $(this).next().next().empty();
        flag=true;
        //ajax();
    }
});
//网点名称失去焦点
$('.branchB').blur(function(){
   var reg=/^[\u4e00-\u9fa5_a-zA-Z0-9]+$;/;
    if ($(this).val() == '') {
        $(this).next().html('请输入网点名称');
        flag=false;
    }if(!reg.test($(this).val())){

        flag=false;
    }else {
        $(this).next().empty();
        flag=true;
        //ajax();
    }
})


//姓名
$('.usernameB').blur(function(){
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
$('.idcardB').blur(function(){
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
//密码失去焦点
$('.input input[name="passwordB"]').blur(function () {
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
$('.input .resPasswordB').blur(function(){
    var pVal = 	$('.input .passwordB').val();
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
});

//推荐人失去焦点
$('.input input[name="phone2"]').blur(function () {
    //手机号正则
    reg = /^1[3|4|5|7|8][0-9]\d{4,8}$/i;
    if ($(this).val() == ''||$(this).val().length < 11) {
        $(this).next().html('手机号有误');
        flag=false;

    }else if (!reg.test($(this).val())) {
        $(this).next().html('请输入正确的手机号');
        $(this).focus();
        flag=false;
    } else {
        $(this).next().empty();
        flag=true;
        //ajax();  //调用函数
    }
});


//创建ajax函数
function ajaxB(){
    var yzmVal=	$('.input input[name="yzmB"] ').val();
    var psswordVal=	$('.input input[name="passwordB"] ').val();
    var branchBVal =$('.input input[name="branchB"] ').val();
    var usernameeBVal = $('.usernameeB').val();
    var phoneVal=	$('.input input[name="phone2"] ').val();
    var idcardBVal =$('.idcardB').val();
    console.log();
    if(flag){
        $.ajax({
            type:'post',
            url:'res.php',
            data:{
                yzm:yzmVal,
                password:psswordVal,
                phone2:phoneVal,
                branchB: branchBVal,
                usernameeB:usernameeBVal,
                idcardB:idcardBVal,
            },
            success:function(reqData){
                alert('恭喜你注册成工')
            },
            error:function(reqData){
                alert('请求失败');
            }
        })
    }else{
        alert('请输入相关信息在提交注册');
    }
}






