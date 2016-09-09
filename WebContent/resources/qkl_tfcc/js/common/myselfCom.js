/**
 * Created by qw on 2016/9/1.
 */
//微信号码
$('#wxnum').blur(function(){
	$this.val($(this).val().trim());
	if($(this).val()==''){
		return;
	}
	valid_wxnum($(this));
});
function valid_wxnum($this){
	var reg=/^[a-zA-Z\d_]{5,}$/;
    if(!reg.test($this.val())){
        $this.val('请输入正确的微信号');
        $this.addClass('errorTip');
        return false;
    }else {
    	if($this.hasClass('errorTip')){
        	$this.removeClass('errorTip');
        }
    	return true;
    }
}
//支付宝
$('#bankaccno').blur(function(){
	$this.val($(this).val().trim());
	if($(this).val()==''){
		return;
	}
	valid_bankaccno($(this));
});
function valid_bankaccno($this){
	var reg = /^(((13[0-9]{1})|159|153)+\d{8})$/;
    if(!reg.test($this.val())){
        $this.val('请输入正确的支付宝号');
        $this.addClass('errorTip');
        return false;
    }else {
    	if($this.hasClass('errorTip')){
        	$this.removeClass('errorTip');
        }
    	return true;
    }
}
//通讯地址
$('#mailAddrss').blur(function(){
	$this.val($(this).val().trim());
	if($(this).val()==''){
		return;
	}
	valid_mailAddrss($(this));
});
function valid_mailAddrss($this){
	 var reg=/ [\u4e00-\u9fa5]/;
	    if ($this.val() == '') {
	        $this.val('通讯地址不能为空');
	        $this.addClass('errorTip');
	        return false;
	    }if(!reg.test($this.val())){
	        $this.val('通讯地址格式有误');
	        $this.addClass('errorTip');
	        return false;
	    }else {
	    	if($this.hasClass('errorTip')){
	        	$this.removeClass('errorTip');
	        }
	    	return true;
	    }
}
//邮编
$('#zipCode').blur(function(){
	$this.val($(this).val().trim());
	if($(this).val()==''){
		return;
	}
	valid_zipCode($(this));
});
function valid_zipCode($this){
	var reg=/[1-9]\d{5}(?!\d)/;
    if ($this.val() == '') {
    	$this.val('邮箱不可以为空');
    	$this.addClass('errorTip');
        return false;
    }if(!reg.test($this.val())){
    	$this.val('请输入正确的邮编');
        $this.addClass('errorTip');
        return false;
    }else {
        if($this.hasClass('errorTip')){
        	$this.removeClass('errorTip');
        }
       return true;
    }
}
