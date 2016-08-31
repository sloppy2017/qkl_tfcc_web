<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="./_g/_headbase.jsp" />
<title>tfcc</title>
<jsp:include page="./_g/head.jsp" />
<link rel="stylesheet" href="${basePath}/resources/qkl_tfcc/css/style.css">


<!-- "url": "/login/islogin"  -->
<script>
    var $$data = {
    		"url": "${basePath}/service/user/sendsms"	
         //"url": "${basePath}/service/user/sendsms"
    };
</script>
</head>
<body class="login">

<!--html start-->
<div class="login_c">
	<form id="j_sigin" class="easyui-form">
		<div class="m-from">
			<div class="row">
				<div class="fieldset">
					<label>用户名</label>
					<div class="val"><input id="phone" name="phone" type="text" /></div>
				</div>
			</div>
			<div class="row">
				<div class="fieldset">
					<label>密&nbsp;&nbsp;码</label>
					<div class="val"><input id="pwd" name="pwd" type="password"/></div>
				</div>
			</div>
			<div class="row alg_c">
				<p class="nullmag">请输入用户名和密码！</p>
				<p class="falsemag">用户名或密码不正确！</p>
				<input type="button" id="j_submit" value="登 录" />
			</div>
		</div>
	</form>
</div>

<div class="login_f">
	Copyright@2015-2016    All Rights Reserved    区块国际
</div>
<!--html end-->

<jsp:include page="./_g/script.jsp"/>
<script type="text/javascript" src="${basePath}/resources/qkl_tfcc/js/login.js"></script>
<jsp:include page="./_g/foot.jsp" />