var $login = {
    _formId: '',
    _init: function(){
       //this.isLogin();
       this._build();
    },
    isLogin:function(){
    	$.ajax({
            url: $$data.url+'?status=0',
            type:'post',
            success:function(data){
                var data = $.parseJSON(data).data;
               	if(data.status==1){
               		window.location.href = "/index";
               	}
            }
        });
    },
    _build:function(){
    	var that = this;
    	$('#j_submit').click(function(){   		
    		that.siginUp();
    	});
    	$(document).keydown(function(e){
		    if(e.keyCode == 13) {
		        that.siginUp();
		    }
		});
    },
    siginUp:function(){
    	var phone=$("#phone").val();
    	var pwd=$("#pwd").val();
    	
    	
    	/*get方式
		promiser = $.ajax({
		    type: "get",
		    url: "http://127.0.0.1:8080/yc_udrs_web/test/showe/1/",
		    dataType: "json",    		    
		    error: function(XMLHttpRequest, b, c) {
		      console.log(XMLHttpRequest.readyState + "\n" + b + "\n" + c);
		    }
		  });
		  promiser.done(function(data) {
		    var listDom = "";
		    alert("done ,data is "+data);
		   
		  });*/
    	//post方式
//		promiser = $.ajax({
//		    type: "post",
////		    url: "http://127.0.0.1:8080/yc_udrs_web/service/user/sendsms/",
//		    dataType: "json",    
//		    data: {
//		    	   phone: phone,
//		    	   pwd: pwd
//		        },
//		    error: function(XMLHttpRequest, b, c) {
//		      console.log(XMLHttpRequest.readyState + "\n" + b + "\n" + c);
//		    }
//		  });
//		  promiser.done(function(data) {
//		    var listDom = "";
////		    alert("done ,data is "+data);
//		   
//		  });
		promiser = $.ajax({
		    type: "post",
//		    url: "http://127.0.0.1:8080/yc_udrs_web/service/user/sendsms/",
		    url: "http://127.0.0.1:8080/yc_udrs_web/test/show/",
		    dataType: "json",    
		    data: {
		    	email: phone
		        },
		    error: function(XMLHttpRequest, b, c) {
		      console.log(XMLHttpRequest.readyState + "\n" + b + "\n" + c);
		    }
		  });
		  promiser.done(function(data) {
		    var listDom = "";
//		    alert("done ,data is "+data);
		   
		  });  	
    	
    	if(phone=="" || pwd==""){
    		$(".nullmag").show();
    	}else{
    		//$.ajax({
    			
    		//	url: $$data.url+'?status=1',
    			//url: 'http://127.0.0.1:8080/yc_udrs_web/test/showe/1',
    			//data: $('#j_sigin').serialize(),
	       //     type:'get',
	       //     success:function(data){ 
	      //      	 var data = $.parseJSON(data).data;
	       //     	 alert("data is "+data);
            		// if(data.name==email && data.password==pwd){
            		//	 window.location.href = "/index";
            		// }else{
            		// 	$(".falsemag").show();
            		// }
	       //     }
    	//	})
    			
    		
    	}
    }
};

$login._init();
function clearCookie(name) {  
    setCookie(phone, "", -1); 
    setCookie(JSESSIONID, "", -1);
}  
function checkCookie() {
    var user = getCookie("username");
    if (user != "") {
        alert("Welcome again " + user);
    } else {
        user = prompt("Please enter your name:", "");
        if (user != "" && user != null) {
            setCookie("username", user, 365);
        }
    }
}