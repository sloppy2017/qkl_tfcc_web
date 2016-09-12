/**
 * Created by Administrator on 2016/8/15.
 */
//ѡ�
var userType = "1";
$(document).ready(function(){
	var $tab_li = $('.reg-nav ul li');
	// console.log($tab_li)
	$tab_li.click(function(){
		$(this).addClass('active').siblings().removeClass('active');
		var index = $tab_li.index(this);
		$(' .form >form').eq(index).show().siblings().hide();
		userType = index + 1;
	});
});
$('.mark1-box .real-name').bind('mousedown',function(){
	$('.mark1-box .real-name1').show();
});
$('.mark1-box .later-on').bind('mousedown',function(){
	$('.mark1-box .later-on1').show();
});