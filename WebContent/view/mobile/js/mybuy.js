$(document).ready(function(){
//	console.log(userPhone)
	$(".uesr_phone").html(userPhone);
	 gathering();	 
				 $("#myAlipay").blur(function(){
					var buyNub,paymentAmount,myAlipaynub;
					if(!validate.alipay($('#myAlipay').val())){
					$("#myAlipayP").html("请输入正确的支付宝账号");
					}else{
						$("#myAlipayP").html("");
					}
				})
				$("#buyNumber").blur(function(){
					
					buyNub= $("#buyNumber").val();
					if(buyNub==""){
						alert("请输入购买数量");
					}else if(!validate.nuber($("#buyNumber").val())){
						alert("请输入正确的数量");
						$("#paymentAmount").val("");
					}else{
						console.log(buyNub);
						purchase();
					}
					
				})
				
				$("#ConfirmBuy").click(function(){
					 paymentAmount=$("#paymentAmount").val();
					 myAlipaynub=$('#myAlipay').val();
					 buyNub= $("#buyNumber").val();
					if(paymentAmount==""){
						alert("请填写数量，获取支付金额");
					}else if(!validate.alipay($('#myAlipay').val())){
						alert("填写正确的支付宝账户");
					}else{
						var a=confirm("您确定购买吗？")
		            		if(a==true){
		            			purchaseOrder();
		            		}
					}
				})

				$("#cancel").click(function(){
					var a=confirm("您确定取消购买吗？")
		            		if(a==true){
		            			window.location.href="index.html"
		            		}
				})

//收款账号
function gathering(){
	$.ajax({
	type:"post",
	url:webURL+"/service/bankaccinfo/info",
	async:true,
	data:{"userCode":usercode},
	success:function(msg){
		console.log(msg);
		var orgName=msg.data.orgName;
		var bankaccno=msg.data.bankaccno;
		$("#gatherUser").html(orgName);
		$("#taoboaNub").html(bankaccno);
		$("#taoboaNub1").html(msg.data.depositBankname);

	}
});
}
//				var wConfirm = window.confirm;  
//				window.confirm = function (message) {  
//				    try {  
//				        var iframe = document.createElement("IFRAME");  
//				        iframe.style.display = "none";  
//				        iframe.setAttribute("src", 'data:text/plain,');  
//				        document.documentElement.appendChild(iframe);  
//				        var alertFrame = window.frames[0];  
//				        var iwindow = alertFrame.window;  
//				        if (iwindow == undefined) {  
//				            iwindow = alertFrame.contentWindow;  
//				        }  
//				        iwindow.confirm(message);  
//				        iframe.parentNode.removeChild(iframe);  
//				    }  
//				    catch (exc) {  
//				        return wConfirm(message);  
//				    }  
//				}  
//				 window.alert = function(name){  
//		            var iframe = document.createElement("IFRAME");  
//		            iframe.style.display="none";  
//		            iframe.setAttribute("src", 'data:text/plain,');  
//		            document.documentElement.appendChild(iframe);  
//		            window.frames[0].window.alert(name);  
//		            iframe.parentNode.removeChild(iframe);  
//		        }  

				function purchase(){
		    
					$.ajax({
						type:"post",
						url:webURL+"/service/bankaccinfo/PayMoney",
						data:{"txnum":buyNub,"userCode":usercode},
						success:function(msg){
							console.log(msg)
							if(msg.data<1000){
								alert("单次交易金额不得小于1000.00");
								$("#paymentAmount").val("");
							}else if(msg.data>50000){
								alert("单次交易金额不得大于50000.00");
								$("#paymentAmount").val("");
							}else{
								$("#paymentAmount").val(msg.data);
							}
							
						}
					});
				   }
				function purchaseOrder(){
					$.ajax({
						type:"post",
						url:webURL+"/service/bankaccinfo/tradebuy",
						data:{"txamnt":paymentAmount,"payno":myAlipaynub,"userCode":usercode,"txnum":buyNub},
						success:function(msg){
							alert(msg.message);
							console.log(msg)
							if(msg.success==true){
								window.location.href="myorder.html";
							}
						
						}
					});
				   }	




})