$(document).ready(function(){
	var $tab_li = $('.nav ul li');
	// console.log($tab_li)
	$tab_li.click(function(){
		$(this).addClass('active').siblings().removeClass('active');
		var index = $tab_li.index(this);
		$(' .main>.list').eq(index).show().siblings().hide();
	})
})
//


$(document).ready(function(){
	var $tab1_li = $('.huiyuan li');
	// console.log($tab_li)
	$tab1_li.click(function(){
		$(this).addClass('act').siblings().removeClass('act');
		var index = $tab1_li.index(this);
		$(' .mains .btm').eq(index).show().siblings().hide();
	})
})

$(function(){
	function time(){
		var now = new Date();
		var years =now.getFullYear();
		var month = now.getMonth()+1;
		var date = now.getDate();
		var times = now.getHours()
		var minutes = now.getMinutes();
		var seconds=now.getSeconds();
		var week = now.getDay()-1;
		$('.yearTime').html(years+'年'+month+'月'+date+'日');
		$('.dateTime').html(times+':'+ minutes+':'+seconds);
		var weekArr = ['一','二','三','四','五','六','日']
		$('.weekTime').html('星期'+weekArr[week]);
	}
	setInterval(time,1000);
})
