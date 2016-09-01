/**
 * Created by Administrator on 2016/8/15.
 */
//ѡ�
$(document).ready(function(){
	var $tab_li = $('.reg-nav ul li');
	console.log($tab_li);

	$tab_li.click(function(){
		$(this).addClass('active').siblings().removeClass('active');
		var index = $tab_li.index(this);
		$(' .form>form').eq(index).show().siblings().hide();
	})
})