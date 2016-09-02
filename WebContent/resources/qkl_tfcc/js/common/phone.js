/**
 * Created by Administrator on 2016/8/30 0030.
 */
jQuery(document).ready(function($) {
// Stuff to do as soon as the DOM is ready;
    var phone=$('#phone');
    $(phone).on('click',function(){
        phone.val('');
    })
    $(phone).on('keyup',function(evt){
        var phoneVal=phone.val();
        phoneVal=phoneVal.replace('/[^\d]+/g', ''); //替换非数字字符为空格
        phoneVal=parseInt(phoneVal,10);
        if(isNaN(phoneVal)){
            phoneVal = '';
        }
        this.value=phoneVal;
    })
});