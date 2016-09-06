/**
 * Created by qw on 2016/9/3.
 */

//选择盒子
$(function(){
    $('.type-btn').on('click',function(){
       var type =$('input[name="type"]:checked').serialize();
        console.log(type);
        $.ajax({
            type:'post',
            url:'sx',
            data:type,
            success:function(){
            }
        })
    })
})

//查询函数
function querytable(){
    var obj = document.getElementById('selectedObj')
    alert(obj.selectedIndex);
    if(obj.options[obj.selectedIndex].selected==true){
        console.log(obj.value);
        $.ajax({
            type:'post',
            url:'res.php',
            data:{member:obj.value},
            dataType:'json',
            success:function(){

            },errror:function(){
            }
        })
    }
    reg=/([\u4e00-\u9fa5]{2,4})/;
    var chaxunVal = $('input[name="chaxun"]').val()
    if(reg.test(chaxunVal)){
        $.ajax({
            type:'post',
            url:'res.php',
            data:{chaxunVal:chaxunVal},
            dataType:'json',
            success:function(){

            },errror:function(){
            }
        })
    }
}


//A,B,C选择表格函数




