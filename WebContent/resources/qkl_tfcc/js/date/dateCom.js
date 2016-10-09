$(document).ready(function(){
    var $tab1_li = $('.main-middle .huiyuan li');
    // console.log($tab_li)
    $tab1_li.click(function(){
        $(this).addClass('act').siblings().removeClass('act');
        var index = $tab1_li.index(this);
        $(' .mains .btm').eq(index).show().siblings().hide();
        $(' .main-top .top-tab').eq(index).show().siblings().hide();
    });
});
function time(){
    var now = new Date();
    var years =now.getFullYear();
    var month = now.getMonth()+1;
    var date = now.getDate();
    var times = now.getHours();
    if(times.toLocaleString().length==1){
    	times = "0"+times;
    }
    var minutes = now.getMinutes();
    if(minutes.toLocaleString().length==1){
    	minutes = "0"+minutes;
    }
    var seconds=now.getSeconds();
    if(seconds.toLocaleString().length==1){
    	seconds = "0"+seconds;
    }
    var week = now.getDay();
    $('.yearTime').html(years+'年'+month+'月'+date+'日');
    $('.dateTime').html(times+':'+ minutes+':'+seconds);
    var weekArr = ['日','一','二','三','四','五','六'];
    $('.weekTime').html('星期'+weekArr[week]);
}
time();
setInterval(time,1000);
/**
 * Created by qw on 2016/8/26.
 */
