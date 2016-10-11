//$(document).ready(function(){   
// var doc=document,
//  inputs=doc.getElementsByTagName('input'),
//  supportPlaceholder='placeholder'in doc.createElement('input'), 
//  placeholder=function(input){
//   var text=input.getAttribute('placeholder'),
//   defaultValue=input.defaultValue;
//   if(defaultValue==''){
//      input.value=text
//   }
//   input.onfocus=function(){
//      if(input.value===text)
//      {
//          this.value=''
//      }
//    };
//   input.onblur=function(){
//      if(input.value===''){
//          this.value=text
//      }
//    }
//};
//
//if(!supportPlaceholder){
//   for(var i=0,len=inputs.length;i<len;i++){
//        var input=inputs[i],
//        text=input.getAttribute('placeholder');
//        if(input.type==='text'&&text){
//           placeholder(input)
//        }
//    }
//}
// });


//兼容placeholder
function isPlaceholder(){  
    var input = document.createElement('input');  
    return 'placeholder' in input;  
}  

if (!isPlaceholder()) {//不支持placeholder 用jquery来完成  
    $(document).ready(function() {  
        if(!isPlaceholder()){  
            $("input").not("input[type='password']").each(//把input绑定事件 排除password框  
                function(){  
                    if($(this).val()=="" && $(this).attr("placeholder")!=""){  
                        $(this).val($(this).attr("placeholder"));  
                        $(this).focus(function(){  
                            if($(this).val()==$(this).attr("placeholder")) $(this).val("");  
                        });  
                        $(this).blur(function(){  
                            if($(this).val()=="") $(this).val($(this).attr("placeholder"));  
                        });  
                    }  
            });  
            //对password框的特殊处理1.创建一个text框 2获取焦点和失去焦点的时候切换  
            var pwdField    = $("input[type=password]");  
            var pwdVal      = pwdField.attr('placeholder');  
            pwdField.after('<input id="pwdPlaceholder" type="text" value='+pwdVal+' autocomplete="off" />');  
            var pwdPlaceholder = $('#pwdPlaceholder');  
            pwdPlaceholder.show();  
            pwdField.hide();  
              
            pwdPlaceholder.focus(function(){  
                pwdPlaceholder.hide();  
                pwdField.show();  
                pwdField.focus();  
            });  
              
            pwdField.blur(function(){  
                if(pwdField.val() == '') {  
                    pwdPlaceholder.show();  
                    pwdField.hide();  
                }  
            });  
              
        }  
    });       
} 
